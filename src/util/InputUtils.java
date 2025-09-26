package util;

import java.util.Scanner;

public class InputUtils {
    public static String getValidCPF(Scanner scanner) {
        String cpf;
        do {
            System.out.print("DIGITE O CPF (000.000.000-00): ");
            cpf = scanner.next();
            if (!ValidationUtils.isValidCPF(cpf)) {
                System.out.println("[ERRO] - CPF INVÁLIDO! DIGITE NOVAMENTE.\n");
                cpf = null;
            }
        } while (cpf == null);

        return cpf;
    }
}
