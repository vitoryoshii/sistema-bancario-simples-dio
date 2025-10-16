package util;

import java.util.Scanner;

public class InputUtils {
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

    public static String getPassword(Scanner scanner) {
        String password;
        do {
            System.out.print("DIGITE SUA SENHA: ");
            password = scanner.nextLine();

            clearBuffer(scanner);
        } while (!password.matches("^[a-zA-Z]+$"));

        return password;
    }
    public static void clearBuffer(Scanner scanner) {
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }


}
