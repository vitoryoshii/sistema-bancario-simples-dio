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

        mainMenu(clients, managers);
    }

    // Method to access menu
    public static void mainMenu(List<Client> clients, List<Manager> managers) {

        do {
            System.out.println("=== BANCO DIGITAL ===");
            System.out.println("1 - ACESSO CLIENTE");
            System.out.println("2 - ACESSO GERENTE");
            System.out.println("0 - SAIR");
            System.out.println("===========================");
            System.out.print("OPÇÃO: ");

            int option = MainSystemBank.scanner.nextInt();

            switch (option){
                case 1 -> {
                    String cpf;
                    do {
                        System.out.print("Digite o CPF para continuar (formato: 000.000.000-00): ");
                        cpf = MainSystemBank.scanner.next();

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
                        cpf = MainSystemBank.scanner.next();

                        if (!cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
                            System.out.println("CPF inválido! Digite novamente no formato 000.000.000-00.");
                            cpf = null;
                        }

                    } while (cpf == null);

                    Manager managerSearch = util.searchManagerByCPF(cpf);

                    Manager manager = new Manager();
                    if (managerSearch == null) {
                        System.out.println("CPF não cadastrado!");
                        Manager newManager = manager.createManager();
                        managers.add(newManager);

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
                case 0 -> System.exit(0);
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

                }
                case 2 -> {
                    break;
                }
                case 3 -> {
                    break;
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

            option = MainSystemBank.scanner.nextInt();

            switch (option){
                case 1 -> {
                    Client newClient = manager.createClient();
                    clients.add(newClient);
                    System.out.println("Criando Cliente...");
                    System.out.println(STR."Cliente: \{newClient.getName()} - CPF: \{newClient.getCpf()}");
                }
                case 2 -> {
                    System.out.println("DIGITE O CPF DO CLIENTE: ");
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
