package main;

import models.Client;
import models.Manager;
import util.UtilServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainSystemBank {
    // Global control variables
    private static List<Client> clients = new ArrayList<>();
    private static List<Manager> managers = new ArrayList<>();

    private static UtilServices util = new UtilServices();
    private static Scanner scanner =  new Scanner(System.in);

    // Method access lists Client or Managers
    public static List<Client> getClients() {
        return clients;
    }

    public static List<Manager> getManagers() {
        return managers;
    }

    // Main start of the entire system
    static void main(String[] args) {
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

                        if (!cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
                            System.out.println("CPF inválido! Digite novamente no formato 000.000.000-00.");
                            cpf = null;
                        }

                    } while (cpf == null);

                    Client clientSearch = util.searchCustomerByCPF(cpf);

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

                        if (!cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
                            System.out.println("CPF inválido! Digite novamente no formato 000.000.000-00.");
                            cpf = null;
                        }

                    } while (cpf == null);

                    Manager managerSearch = util.searchManagerByCPF(cpf);

                    if (managerSearch == null) {
                        System.out.println("CPF não cadastrado!");

                        Manager manager = new Manager();
                        Manager newManager = manager.createManager(cpf);
                        MainSystemBank.getManagers().add(newManager);

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
            System.out.println(STR."=== ACESSO CLIENTE - \{client.getName()} ===");
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
            System.out.println(STR."=== ACESSO GERENTE - \{manager.getUser()} ===");
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
                    clients.add(newClient);
                    System.out.println("Criando Cliente...");
                    System.out.println(STR."Cliente: \{newClient.getName()} - CPF: \{newClient.getCpf()}\n");
                }
                case 2 -> {
                    System.out.print("DIGITE O CPF DO CLIENTE: ");
                    String cpf = scanner.next();

                    Client client = util.searchCustomerByCPF(cpf);

                    if (client == null){
                        System.out.println("CPF NÃO CADASTRADO. CADASTRE UM CLIENTE!");
                    } else {
                        System.out.println(manager.activatedAccount(client));
                    }
                }
                case 3 -> System.out.println(manager.listUsers());
                case 4 -> System.out.println(manager.listAccounts());
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida");
            }
        } while (option != 0);
    }
}
