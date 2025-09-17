package main;

import models.Client;
import models.Manager;
import util.BankRepository;
import util.ValidationUtils;

import java.util.Scanner;

public class MainSystemBank {

    private static final BankRepository bankRepository = new BankRepository();
    private static final Scanner scanner =  new Scanner(System.in);

    // Main start of the entire system
    public static void main(String[] args) {
        scanner.useDelimiter("\\n");
        mainMenu();
    }

    // Method to access menu
    public static void mainMenu() {

        do {
            System.out.println("===== BANCO DIGITAL =====");
            System.out.println("1 - ACESSO CLIENTE");
            System.out.println("2 - ACESSO GERENTE");
            System.out.println("0 - SAIR");
            System.out.println("=========================");
            System.out.print("OPÇÃO: ");

            int option = scanner.nextInt();

            switch (option){
                case 1 -> {
                    String cpf;
                    do {
                        System.out.print("DIGITE O CPF (000.000.000-00): ");
                        cpf = scanner.next();
                        if (!ValidationUtils.isValidCPF(cpf)) {
                            System.out.println("[ERRO] - CPF INVÁLIDO! DIGITE NOVAMENTE.\n");
                            cpf = null;
                        }
                    } while (cpf == null);

                    Client clientSearch = bankRepository.searchCustomerByCPF(cpf);

                    if (clientSearch == null) {
                        System.out.println("[ERRO] - CPF NÃO CADASTRADO! O GERENTE DEVE CRIAR O CADASTRO.\n");
                    } else {
                        clientMenu(clientSearch);
                    }
                }
                case 2 -> {
                    String cpf;
                    do {
                        System.out.print("DIGITE O CPF (000.000.000-00): ");
                        cpf = scanner.next();
                        if (!ValidationUtils.isValidCPF(cpf)) {
                            System.out.println("[ERRO] - CPF INVÁLIDO! DIGITE NOVAMENTE.\n");
                            cpf = null;
                        }
                    } while (cpf == null);

                    Manager managerSearch = bankRepository.searchManagerByCPF(cpf);

                    if (managerSearch == null) {
                        System.out.println("[ERRO] - CPF NÃO CADASTRADO!\n");

                        Manager newManager = Manager.createManager(cpf);
                        bankRepository.addManager(newManager);

                        System.out.println("[SUCESSO] - CADASTRO REALIZADO!\n");
                    } else {
                        System.out.print("DIGITE O USUÁRIO: ");
                        String user = MainSystemBank.scanner.next();
                        System.out.print("DIGITE A SENHA: ");
                        String password = MainSystemBank.scanner.next();

                        if (managerSearch.getUser().equals(user) && managerSearch.getPassword().equals(password)) {
                            System.out.println("[SUCESSO] - LOGIN REALIZADO!\n");
                            managerMenu(managerSearch);
                        } else {
                            System.out.println("[ERRO] - USUÁRIO OU SENHA INCORRETA!\n");
                        }
                    }
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("[ERRO] - OPÇÃO INVÁLIDA!");
            }
        }   while (true);
    }

    public static void clientMenu(Client client) {
        int option;
        do {
            System.out.println("=== ACESSO CLIENTE - " + client.getName() + " ===");
            System.out.println("1 - SAQUE");
            System.out.println("2 - DEPOSITO");
            System.out.println("3 - EXTRATO");
            System.out.println("0 - VOLTAR");
            System.out.println("====================================");
            System.out.print("OPÇÃO: ");

            option = MainSystemBank.scanner.nextInt();

            switch (option) {
                case 1 -> {
                    System.out.print("VALOR DO SAQUE: ");
                    double value = scanner.nextDouble();

                    if (!client.getAccount()){
                        System.out.println("[ERRO] - CONTA NÃO ATIVADA!\n");
                    } else {
                        String stringReturn = client.withdraw(value);
                        System.out.println(stringReturn);
                    }
                }
                case 2 -> {
                    System.out.print("VALOR DEPOSITO: ");
                    double value = scanner.nextDouble();

                    if  (!client.getAccount()){
                        System.out.println("[ERRO] - CONTA NÃO ATIVADA!\n");
                    } else {
                        String stringReturn = client.deposit(value);
                        System.out.println(stringReturn);
                    }
                }
                case 3 -> {
                    System.out.println(client.showExtract());
                }
                case 0 -> System.out.println("VOLTANDO...\n");
                default -> System.out.println("[ERRO] - OPÇÃO INVÁLIDA!");
            }
        } while (option != 0);
    }

    public static void managerMenu(Manager manager) {
        int option;
        do {
            System.out.println("=== ACESSO GERENTE - " + manager.getUser() + " ===");
            System.out.println("1 - CRIAR CLIENTE");
            System.out.println("2 - ATIVAR CONTA CLIENTE");
            System.out.println("3 - LISTAR CLIENTES");
            System.out.println("4 - LISTAR CONTAS CLIENTES");
            System.out.println("====================================");
            System.out.println("5 - ATUALIZAÇÃO CADASTRAL (IMPLEMENTAR)");
            System.out.println("====================================");
            System.out.println("0 - VOLTAR");
            System.out.println("====================================");
            System.out.print("OPÇÃO: ");

            option = scanner.nextInt();

            switch (option){
                case 1 -> {
                    Client newClient = manager.createClient();
                    bankRepository.addClient(newClient);
                    System.out.println("CRIANDO CLIENTE...");
                    System.out.println("CLIENTE: " + newClient.getName() + " - CPF: " + newClient.getCpf() + "\n");
                }
                case 2 -> {
                    System.out.print("DIGITE O CPF DO CLIENTE: ");
                    String cpf = scanner.next();

                    Client client = bankRepository.searchCustomerByCPF(cpf);

                    if (client == null){
                        System.out.println("[ERRO] - CPF NÃO CADASTRADO!\n");
                    } else {
                        System.out.println(manager.activatedAccount(client));
                    }
                }
                case 3 -> System.out.println(manager.listUsers(bankRepository.getClients()));
                case 4 -> System.out.println(manager.listAccounts(bankRepository.getClients()));
                /*
                    Criar funcionalidade nova no sistema, para poder atualizar o cadastro de um cliente.
                    - Deve pedir CPF do cliente, procurar dentro da lista do cliente.
                    - Trazer as informações atuais do cliente (pode ser no formato que está implementado para listar usuário).
                    - Pedir as informações para atualizar o cadastro e caso não queira alterar alguma informação só continuar (com enter).
                        - Realizar as verificações no padrão já existente em produção.
                    - Apresentar mensagem padrão de sucesso ou erro.
                */
                case 0 -> System.out.println("VOLTANDO...\n");
                default -> System.out.println("[ERRO] - OPÇÃO INVÁLIDA");
            }
        } while (option != 0);
    }
}
