package menus;

import models.Client;
import models.Manager;
import util.BankRepository;
import util.InputUtils;
import util.ValidationUtils;

import java.util.Scanner;

public class MainMenu implements Menu{
    private Scanner scanner = new Scanner(System.in);
    private final BankRepository bankRepository;

    public MainMenu(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    public void exibir() {
        do {
            System.out.println("===== BANCO DIGITAL =====");
            System.out.println("1 - ACESSO CLIENTE");
            System.out.println("2 - ACESSO GERENTE");
            System.out.println("0 - SAIR");
            System.out.println("=========================");
            System.out.print("OPÇÃO: ");

            int option = scanner.nextInt();

            switch (option) {
                case 1 -> accessClient();
                case 2 -> accessManager();
                case 0 -> {
                    System.out.println("Encerrando o sistema...");
                    return;
                }
                default -> System.out.println("[ERRO] - OPÇÃO INVÁLIDA!\n");
            }
        } while (true);
    }

    public void accessClient() {
        String cpf = InputUtils.getValidCPF(scanner);

        Client client = bankRepository.searchCustomerByCPF(cpf);

        if (client == null) {
            System.out.println("[ERRO] - CPF NÃO CADASTRADO! O GERENTE DEVE CRIAR O CADASTRO.\n");
        } else {
            new ClientMenu(bankRepository, client, scanner).exibir();
        }
    }

    public void accessManager() {
        String cpf = InputUtils.getValidCPF(scanner);

        Manager manager = bankRepository.searchManagerByCPF(cpf);

        if (manager == null) {
            System.out.println("[ERRO] - CPF NÃO CADASTRADO!\n");

            Manager newManager = Manager.createManager(cpf);
            bankRepository.addManager(newManager);

            System.out.println("[SUCESSO] - CADASTRO REALIZADO!\n");
        } else {
            System.out.print("DIGITE O USUÁRIO: ");
            String user = scanner.next();
            System.out.print("DIGITE A SENHA: ");
            String password = scanner.next();

            ValidationUtils.checkUsernamePassword(manager, bankRepository, scanner, user, password);
        }
    }
}
