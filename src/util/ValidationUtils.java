package util;

import dao.ManagerDao;
import menus.ManagerMenu;
import models.Manager;

import java.util.Optional;
import java.util.Scanner;

/**
 * A utility class containing static methods for data validation and authentication checks.
 * <p>
 * This class ensures data integrity by validating various input formats (CPF, dates,
 * names, addresses) and handles the final manager authentication step.
 */
public class ValidationUtils {

    /**
     * Removes all non-digit characters from a CPF string.
     *
     * @param cpf The raw CPF string, possibly containing dots, hyphens, or spaces.
     * @return The cleaned CPF string containing only 11 digits.
     */
    public static String cleanCPF(String cpf) {
        return cpf.replaceAll("[^\\d]", "");
    }

    /**
     * Validates a CPF string using the official Brazilian algorithm (modulus 11 calculation).
     * <p>
     * Performs checks for length, sequential identical digits (e.g., "111.111.111-11"),
     * and correctness of the two verification digits.
     *
     * @param cpf The CPF string to be validated (formatting is handled internally).
     * @return {@code true} if the CPF is valid, {@code false} otherwise.
     */
    public static boolean isValidCPF(String cpf) {
        cpf = cleanCPF(cpf);

        if (cpf.length() != 11) return false;

        // Checks if all digits are the same (invalid CPF)
        if (cpf.matches("(\\d)\\1{10}")) return false;

        try {
            int sum = 0;
            int weight = 10;
            for (int i = 0; i < 9; i++) {
                int num = Integer.parseInt(cpf.substring(i, i + 1));
                sum += num * weight;
                weight--;
            }
            int mod = 11 - (sum % 11);
            int digit1 = (mod == 10 || mod == 11) ? 0 : mod;
            if (digit1 != Integer.parseInt(cpf.substring(9, 10))) return false;

            sum = 0;
            weight = 11;
            for (int i = 0; i < 10; i++) {
                int num = Integer.parseInt(cpf.substring(i, i + 1));
                sum += num * weight;
                weight--;
            }
            mod = 11 - (sum % 11);
            int digit2 = (mod == 10 || mod == 11) ? 0 : mod;
            return digit2 == Integer.parseInt(cpf.substring(10, 11));
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Validates a date string to ensure it is in the "dd-MM-yyyy" format and represents a real calendar date.
     * <p>
     * Uses a non-lenient {@code SimpleDateFormat} for strict format and date validity checking.
     *
     * @param date The date string to validate.
     * @return {@code true} if the date is valid and in the correct format, {@code false} otherwise.
     */
    public static boolean isValidDate(String date) {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(date);
            return true;
        } catch (java.text.ParseException e) {
            return false;
        }
    }

    /**
     * Validates a name string, ensuring it contains only letters, spaces, and accented characters.
     *
     * @param name The name string to validate.
     * @return {@code true} if the name is valid, {@code false} otherwise.
     */
    public static boolean isValidName(String name) {
        return name != null && name.matches("[a-zA-ZÀ-ÿ ]+");
    }

    /**
     * Validates an address string, allowing letters, numbers, spaces, and common address punctuation (e.g., commas, hyphens).
     *
     * @param address The address string to validate.
     * @return {@code true} if the address is valid, {@code false} otherwise.
     */
    public static boolean isValidAddress(String address) {
        return address != null && address.matches("[a-zA-ZÀ-ÿ0-9 .,-º]+");
    }

    /**
     * Validates a username string, ensuring it follows a basic structure (e.g., "word.word").
     *
     * @param user The username string to validate.
     * @return {@code true} if the username matches the required format, {@code false} otherwise.
     */
    public static boolean isValidUser(String user) {
        return user != null && user.matches("^[a-zA-Z]+\\.[a-zA-Z]+$");
    }

    /**
     * Validates a password against strong security requirements:
     * <ul>
     * <li>Minimum 8 characters</li>
     * <li>At least 1 uppercase letter</li>
     * <li>At least 1 lowercase letter</li>
     * <li>At least 1 digit (number)</li>
     * <li>At least 1 special character ({@code @#$%^&+=!})</li>
     * </ul>
     *
     * @param password The password string to validate.
     * @return {@code true} if the password meets all criteria, {@code false} otherwise.
     */
    public static boolean isValidPassword(String password) {
        return password != null && password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$");
    }

    /**
     * Authenticates a manager by searching the database and checking the password.
     * <p>
     * If authentication is successful, the {@link ManagerMenu} is launched. If it fails,
     * an error message is displayed. Handles {@code Exception} for database connection failures.
     *
     * @param scanner The {@code Scanner} object for input (passed to {@code ManagerMenu}).
     * @param user The username provided by the user.
     * @param password The password provided by the user (assumed to be plaintext matching a stored hash/plaintext).
     */
    public static void checkUsernamePassword(Scanner scanner, String user, String password) {

        ManagerDao managerDAO = new ManagerDao();

        try {
            Optional<Manager> managerOpt = managerDAO.findManagerByUser(user);

            if (managerOpt.isPresent()) {
                Manager manager = managerOpt.get();
                String storedHash = manager.getPassword();


                if (password.equals(storedHash)) {

                    System.out.println("[SUCESSO] - LOGIN REALIZADO!\n");
                    new ManagerMenu(manager, scanner).exibir();

                } else {
                    System.out.println("[ERRO] - SENHA INCORRETA.");
                }
            } else {
                System.out.println("[ERRO] - USUÁRIO NÃO ENCONTRADO.");
            }
        } catch (Exception e) {
            System.err.println("[ERRO FATAL] - Falha na conexão ou no login: " + e.getMessage());
        }
    }
}
