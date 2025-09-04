package main;

import models.Client;
import models.Manager;
import util.BankRepository;
import util.ValidationUtils;

import java.util.Scanner;

public class MainSystemBank {

    private static BankRepository bankRepository = new BankRepository();
    private static Scanner scanner =  new Scanner(System.in);

    // Main start of the entire system
    public static void main(String[] args) {
        scanner.useDelimiter("\\n");
        mainMenu();
    }

    // Method to access menu
    public static void mainMenu() {

        do {
            System.out.println("=== BANCO DIGITAL ===");
            System.out.println("1 - ACESSO CLIENTE");
            System.out.println("2 - ACESSO GERENTE");
            System.out.println("0 - SAIR");
            System.out.println("===========================");
            System.out.print("OPÇÃO: ");

            int option = scanner.nextInt();

            switch (option){
                case 1 -> {
                    String cpf;
                    do {
                        System.out.print("Digite o CPF para continuar (formato: 000.000.000-00): ");
                        cpf = scanner.next();
                        if (!ValidationUtils.isValidCPF(cpf)) {
                            System.out.println("CPF inválido! Digite um CPF válido.");
                            cpf = null;
                        }
                    } while (cpf == null);

                    Client clientSearch = bankRepository.searchCustomerByCPF(cpf);

                    if (clientSearch == null) {
                        System.out.println("CPF não cadastrado! Deve ser criado o cliente através do gerente.");
                    } else {
                        clientMenu(clientSearch);
                    }
                }
                case 2 -> {
                    String cpf;
                    do {
                        System.out.print("Digite o CPF para continuar (formato: 000.000.000-00): ");
                        cpf = scanner.next();
                        if (!ValidationUtils.isValidCPF(cpf)) {
                            System.out.println("CPF inválido! Digite um CPF válido.");
                            cpf = null;
                        }
                    } while (cpf == null);

                    Manager managerSearch = bankRepository.searchManagerByCPF(cpf);

                    if (managerSearch == null) {
                        System.out.println("CPF não cadastrado!");

                        Manager manager = new Manager();
                        Manager newManager = manager.createManager(cpf);
                        bankRepository.addManager(newManager);

                        System.out.println("CADASTRO realizado com sucesso!");
                    } else {
                        System.out.print("Digite usuário: ");
                        String user = MainSystemBank.scanner.next();
                        System.out.print("Digite a senha: ");
                        String password = MainSystemBank.scanner.next();

                        if (managerSearch.getUser().equals(user) && managerSearch.getPassword().equals(password)) {
                            System.out.println("Login realizado com sucesso!");
                            managerMenu(managerSearch);
                        } else {
                            System.out.println("Usuário ou senha incorretos!");
                        }
                    }
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Opção inválida");
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
            System.out.println("===========================");
            System.out.print("OPÇÃO: ");

            option = MainSystemBank.scanner.nextInt();

            switch (option) {
                case 1 -> {
                    System.out.print("Valor do Saque: ");
                    double value = scanner.nextDouble();

                    if (client.getAccount() == false){
                        System.out.println("Não será possivél continuar. Conta não ativa!");
                    } else {
                        String stringReturn = client.withdraw(value);
                        System.out.println(stringReturn);
                    }
                }
                case 2 -> {
                    System.out.print("Valor do Deposito: ");
                    double value = scanner.nextDouble();

                    if  (client.getAccount() == false){
                        System.out.println("Não será possivél continuar. Conta não ativa!");
                    } else {
                        String stringReturn = client.deposit(value);
                        System.out.println(stringReturn);
                    }
                }
                case 3 -> {
                    System.out.println(client.showExtract());
                }
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida");
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
            System.out.println("0 - VOLTAR");
            System.out.println("===========================");
            System.out.print("OPÇÃO: ");

            option = scanner.nextInt();

            switch (option){
                case 1 -> {
                    Client newClient = manager.createClient();
                    bankRepository.addClient(newClient);
                    System.out.println("Criando Cliente...");
                    System.out.println("Cliente: " + newClient.getName() + " - CPF: " + newClient.getCpf() + "\n");
                }
                case 2 -> {
                    System.out.print("DIGITE O CPF DO CLIENTE: ");
                    String cpf = scanner.next();

                    Client client = bankRepository.searchCustomerByCPF(cpf);

                    if (client == null){
                        System.out.println("CPF NÃO CADASTRADO. CADASTRE UM CLIENTE!");
                    } else {
                        System.out.println(manager.activatedAccount(client));
                    }
                }
                case 3 -> System.out.println(manager.listUsers(bankRepository.getClients()));
                case 4 -> System.out.println(manager.listAccounts(bankRepository.getClients()));
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida");
            }
        } while (option != 0);
    }
}
