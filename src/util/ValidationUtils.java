package util;

import menus.ManagerMenu;
import models.Manager;

import java.util.Scanner;

public class ValidationUtils {
    // Removed character not numeric
    public static String cleanCPF(String cpf) {
        return cpf.replaceAll("[^\\d]", "");
    }

    // Validate CPF with official algorithm
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

    // Validates date in dd-MM-yyyy format and checks if it is a valid date
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

    // Validates name (allowing letters, spaces and accents)
    public static boolean isValidName(String name) {
        return name != null && name.matches("[a-zA-ZÀ-ÿ ]+");
    }

    // Validates address (allowing letters, numbers, spaces and some special characters)
    public static boolean isValidAddress(String address) {
        return address != null && address.matches("[a-zA-ZÀ-ÿ0-9 .,-º]+");
    }

    // Validate user (e.g. user.demonstrative)
    public static boolean isValidUser(String user) {
        return user != null && user.matches("^[a-zA-Z]+\\.[a-zA-Z]+$");
    }

    // Valid strong password: minimum 8 characters, at least 1 uppercase letter, 1 lowercase letter, 1 number and 1 special character
    public static boolean isValidPassword(String password) {
        return password != null && password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$");
    }

    // Validates if user and password are correct and calls the respective function
    public static void checkUsernamePassword(Manager manager, BankRepository bankRepository, Scanner scanner, String user, String password) {
        if (manager.getUser().equals(user) && manager.getPassword().equals(password)) {
            System.out.println("[SUCESSO] - LOGIN REALIZADO!\n");
            new ManagerMenu(bankRepository, manager, scanner).exibir();
        } else {
            System.out.println("[ERRO] - USUÁRIO OU SENHA INCORRETA!\n");
        }
    }

}
