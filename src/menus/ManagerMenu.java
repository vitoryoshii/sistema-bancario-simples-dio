package menus;

import models.Client;
import models.Manager;
import util.BankRepository;
import util.InputUtils;

import java.util.Scanner;

public class ManagerMenu implements Menu{
    private Manager manager;
    private BankRepository bankRepository;
    private Scanner scanner;

    public ManagerMenu(BankRepository bankRepository, Manager manager, Scanner scanner) {
        this.bankRepository = bankRepository;
        this.manager = manager;
        this.scanner = scanner;
    }

    @Override
    public void exibir() {
        int option;
        do {
            System.out.println("=== ACESSO GERENTE - " + manager.getUser() + " ===");
            System.out.println("1 - CRIAR CLIENTE");
            System.out.println("2 - ATIVAR CONTA CLIENTE");
            System.out.println("3 - LISTAR CLIENTES");
            System.out.println("4 - LISTAR CONTAS CLIENTES");
            System.out.println("====================================");
            System.out.println("5 - ATUALIZAÇÃO CADASTRAL");
            System.out.println("====================================");
            System.out.println("0 - VOLTAR");
            System.out.println("====================================");
            System.out.print("OPÇÃO: ");

            option = scanner.nextInt();

            switch (option){
                case 1 -> createClient();
                case 2 -> activateClient();
                case 3 -> System.out.println(manager.listUsers(bankRepository.getClients()));
                case 4 -> System.out.println(manager.listAccounts(bankRepository.getClients()));
                case 5 -> updateClient();
                case 0 -> System.out.println("VOLTANDO...\n");
                default -> System.out.println("[ERRO] - OPÇÃO INVÁLIDA\n");
            }
        } while (option != 0);
    }

    public void createClient() {
        Client newClient = manager.createClient();
        bankRepository.addClient(newClient);
        System.out.println("[SUCESSO] - CLIENTE CRIADO: " + newClient.getName() + " - CPF: " + newClient.getCpf() + "\n");
    }

    public void activateClient() {
        String cpf = InputUtils.getValidCPF(scanner);

        Client client = bankRepository.searchCustomerByCPF(cpf);

        if (client != null){
            System.out.println(manager.activatedAccount(client));
        } else {
            System.out.println("[ERRO] - CPF NÃO CADASTRADO!\n");
        }
    }

    public void updateClient() {
        String cpf = InputUtils.getValidCPF(scanner);

        Client client = bankRepository.searchCustomerByCPF(cpf);

        if (client != null) {
            manager.updateRegistration(client);
        } else {
            System.out.println("[ERRO] - CPF NÃO ENCONTRADO!\n");
        }
    }
}
