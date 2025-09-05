package models;

import java.util.List;
import java.util.Scanner;

import util.ValidationUtils;

public class Manager {
    private Scanner scanner =  new Scanner(System.in);

    // Variables to control the models.Manager internal methods
    private String name;
    private String cpf;
    private String dateOfBirth;
    private String address;
    private String user;
    private String password;

    // constructor method of the manager object
    public Manager(String name, String cpf, String dateOfBirth, String address, String user, String password) {
        this.name = name;
        this.cpf = cpf;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.user = user;
        this.password = password;
    }

    // constructor method empty
    public Manager() {

    }

    // Method to return in attributes
    public String getCpf() {
        return cpf;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    // Method to create new customers
    public Client createClient() {
        scanner = new Scanner(System.in);

        // Capture customer information so you can create
        System.out.println("CADASTRO DE CLIENTE");

        // Requests customer data, verifies if it is correct, if not, validates the operations
        do {
            System.out.print("DIGITE SEU NOME: ");
            name = scanner.nextLine();
            if (!ValidationUtils.isValidName(name)) {
                System.out.println("[ERRO] - NOME INVÁLIDO! APENAS LETRAS E ESPAÇOS.");
            }
        } while (!ValidationUtils.isValidName(name));

        do {
            System.out.print("DIGITE O CPF (000.000.000-00): ");
            cpf = scanner.nextLine();
            if (!ValidationUtils.isValidCPF(cpf)) {
                System.out.println("[ERRO] - CPF INVÁLIDO! DIGITE NOVAMENTE.");
            }
        } while (!ValidationUtils.isValidCPF(cpf));

        do {
            System.out.print("DIGITE SUA DATA DE NASCIMENTO (DD-MM-YYYY): ");
            dateOfBirth = scanner.nextLine();
            if (!ValidationUtils.isValidDate(dateOfBirth)) {
                System.out.println("[ERRO] - DATA INVÁLIDA! DIGITE NOVAMENTE");
            }
        }while (!ValidationUtils.isValidDate(dateOfBirth));

        do {
            System.out.print("DIGITE SEU ENDEREÇO: ");
            address = scanner.nextLine();
            if (!ValidationUtils.isValidAddress(address)) {
                System.out.println("[ERRO] - ENDEREÇO INVÁLIDO! DIGITE NOVAMENTE.");
            }
        } while (!ValidationUtils.isValidAddress(address));

        return new Client(name, cpf, dateOfBirth, address);
    }

    // Method to assign an account
    public String activatedAccount(Client client) {
        System.out.println("ATIVANDO CONTA CLIENTE");

        if (!client.getAccount()) {
            client.setAccount(true);
            return "[SUCESSO] - CONTA: " + client.getCpf() + " - " + client.getName() + " - ATIVADA!\n";
        } else {
            return "[ERRO] - CONTA: " + client.getCpf() + " - " + client.getName() + " - CONTA JÁ ATIVA!\n";
        }
    }

    // Method list users
    public String listUsers(List<Client> clients) {
        if (clients.isEmpty()) {
            return "NENHUM CLIENTE NA LISTA\n";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("===== LISTA DE CLIENTE =====\n");

        for (Client c : clients) {
            sb.append("NOME: ").append(c.getName()).append("\n")
                    .append("CPF: ").append(c.getCpf()).append("\n")
                    .append("DATA DE NASCIMENTO: ").append(c.getDateOfBirth()).append("\n")
                    .append("ENDEREÇO: ").append(c.getAddress()).append("\n")
                    .append("---------------------------\n");
        }

        return sb.toString();
    }

    // Method list account
    public String listAccounts(List<Client> clients) {
        if (clients.isEmpty()) {
            return "NENHUMA CONTA NA LISTA.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("===== LISTA DE CONTAS =====\n");

        for (Client c : clients) {
            sb.append("NOME: ").append(c.getName()).append("\n")
                    .append("CPF: ").append(c.getCpf()).append("\n")
                    .append("CONTA ATIVA: ").append(c.getAccount() ? "SIM" : "NÃO").append("\n")
                    .append("SALDO DA CONTA: ").append(c.getBalance()).append("\n")
                    .append("---------------------------\n");
        }

        return sb.toString();
    }

    // Method that creates when there is no manager on the list
    public Manager createManager(String cpf) {
        System.out.println("CADASTRO DE GERENTE");

        // Requests customer data, verifies if it is correct, if not, validates the operations
        do {
            System.out.print("DIGITE SEU NOME: ");
            name = scanner.nextLine();
            if (!ValidationUtils.isValidName(name)) {
                System.out.println("[ERRO] - NOME INVÁLIDO! APENAS LETRAS E ESPAÇOS.");
            }
        } while (!ValidationUtils.isValidName(name));

        do {
            System.out.print("DIGITE SUA DATA DE NASCIMENTO (DD-MM-YYYY): ");
            dateOfBirth = scanner.nextLine();
            if (!ValidationUtils.isValidDate(dateOfBirth)) {
                System.out.println("[ERRO] - DATA INVÁLIDA! DIGITE NOVAMENTE.");
            }
        } while (!ValidationUtils.isValidDate(dateOfBirth));

        do {
            System.out.print("DIGITE SEU ENDERECO: ");
            address = scanner.nextLine();
            if (!ValidationUtils.isValidAddress(address)) {
                System.out.println("[ERRO] - ENDEREÇO INVÁLIDO! DIGITE NOVAMENTE.");
            }
        } while (!ValidationUtils.isValidAddress(address));

        do {
            System.out.print("DIGITE SEU USUÁRIO (USER.DEMOSTRATIVO): ");
            user = scanner.nextLine();
            if (!ValidationUtils.isValidUser(user)) {
                System.out.println("[ERRO] - USUÁRIO INVÁLIDO! DIGITE NOVAMENTE.");
            }
        } while (!ValidationUtils.isValidUser(user));

        do {
            System.out.print("DIGITE SUA SENHA: ");
            password = scanner.nextLine();
        } while (!password.matches("^[a-zA-Z]+$"));

        return new Manager(name, cpf, dateOfBirth, address,  user, password);
    }
}
