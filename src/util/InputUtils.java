package util;

import java.util.Scanner;

/**
 * A utility class dedicated to handling console input from the user.
 * <p>
 * This class provides static methods for reading specific types of data (like CPF, name,
 * date, etc.) and ensures that the captured input passes basic format validation
 * via methods in {@link ValidationUtils} before returning the value.
 */
public class InputUtils {

    /**
     * Prompts the user to enter a CPF and repeatedly asks for input until a valid
     * CPF string is provided according to {@link ValidationUtils#isValidCPF(String)}.
     *
     * @param scanner The {@code Scanner} object used for reading input.
     * @return The validated CPF string in the format 000.000.000-00.
     */
    public static String getValidCPF(Scanner scanner) {
        String cpf;
        do {
            System.out.print("DIGITE O CPF (000.000.000-00): ");
            cpf = scanner.nextLine();
            if (!ValidationUtils.isValidCPF(cpf)) {
                System.out.println("[ERRO] - CPF INVÁLIDO! DIGITE NOVAMENTE.");
            }
        } while (!ValidationUtils.isValidCPF(cpf));

        return cpf;
    }

    /**
     * Prompts the user for a name and repeats the prompt until the input is valid
     * according to {@link ValidationUtils#isValidName(String)}.
     *
     * @param scanner The {@code Scanner} object used for reading input.
     * @return The validated name string.
     */
    public static String getName(Scanner scanner) {
        String name;
        do {
            System.out.print("DIGITE SEU NOME: ");
            name = scanner.nextLine();
            if (!ValidationUtils.isValidName(name)) {
                System.out.println("[ERRO] - NOME INVÁLIDO! APENAS LETRAS E ESPAÇOS.");
            }
        } while (!ValidationUtils.isValidName(name));

        return name;
    }

    /**
     * Prompts the user for a date of birth and repeats the prompt until the input is
     * valid according to {@link ValidationUtils#isValidDate(String)}.
     *
     * @param scanner The {@code Scanner} object used for reading input.
     * @return The validated date string in the format DD-MM-YYYY.
     */
    public static String getDateOfBirth(Scanner scanner) {
        String dateOfBirth;
        do {
            System.out.print("DIGITE SUA DATA DE NASCIMENTO (DD-MM-YYYY): ");
            dateOfBirth = scanner.nextLine();
            if (!ValidationUtils.isValidDate(dateOfBirth)) {
                System.out.println("[ERRO] - DATA INVÁLIDA! DIGITE NOVAMENTE");
            }
        } while (!ValidationUtils.isValidDate(dateOfBirth));

        return dateOfBirth;
    }

    /**
     * Prompts the user for an address and repeats the prompt until the input is
     * valid according to {@link ValidationUtils#isValidAddress(String)}.
     *
     * @param scanner The {@code Scanner} object used for reading input.
     * @return The validated address string.
     */
    public static String getAddress(Scanner scanner) {
        String address;
        do {
            System.out.print("DIGITE SEU ENDEREÇO: ");
            address = scanner.nextLine();
            if (!ValidationUtils.isValidAddress(address)) {
                System.out.println("[ERRO] - ENDEREÇO INVÁLIDO! DIGITE NOVAMENTE.");
            }
        } while (!ValidationUtils.isValidAddress(address));

        return address;
    }

    /**
     * Prompts the user for a login username and repeats the prompt until the input
     * is valid according to {@link ValidationUtils#isValidUser(String)}.
     * <p>
     * Calls {@link #clearBuffer(Scanner)} to prevent scanner issues if the next input
     * is read by a different method.
     *
     * @param scanner The {@code Scanner} object used for reading input.
     * @return The validated username string.
     */
    public static String getUser(Scanner scanner) {
        String user;
        do {
            System.out.print("DIGITE SEU USUÁRIO (USER.DEMOSTRATIVO): ");
            user = scanner.nextLine();
            if (!ValidationUtils.isValidUser(user)) {
                System.out.println("[ERRO] - USUÁRIO INVÁLIDO! DIGITE NOVAMENTE.");
            }

            clearBuffer(scanner);
        } while (!ValidationUtils.isValidUser(user));

        return user;
    }

    /**
     * Prompts the user for a password and repeats the prompt until the password
     * contains only letters (a-z, A-Z).
     * <p>
     * Note: This validation rule (only letters) is weak for a real application.
     * Calls {@link #clearBuffer(Scanner)} to prevent scanner issues.
     *
     * @param scanner The {@code Scanner} object used for reading input.
     * @return The validated password string.
     */
    public static String getPassword(Scanner scanner) {
        String password;
        do {
            System.out.print("DIGITE SUA SENHA: ");
            password = scanner.nextLine();

            clearBuffer(scanner);
        } while (!password.matches("^[a-zA-Z]+$"));

        return password;
    }

    /**
     * Clears any remaining input from the scanner's buffer.
     * <p>
     * This is useful after reading a number with {@code nextInt()} or {@code nextDouble()}
     * to prevent leftover newline characters from interfering with a subsequent {@code nextLine()} call.
     *
     * @param scanner The {@code Scanner} object to clear.
     */
    public static void clearBuffer(Scanner scanner) {
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }
}
