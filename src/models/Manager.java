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
            System.out.print("Digite seu nome: ");
            name = scanner.nextLine();
            if (!ValidationUtils.isValidName(name)) {
                System.out.println("Nome inválido! Use apenas letras e espaços.");
            }
        } while (!ValidationUtils.isValidName(name));

        do {
            System.out.print("Digite seu cpf (000.000.000-00): ");
            cpf = scanner.nextLine();
            if (!ValidationUtils.isValidCPF(cpf)) {
                System.out.println("CPF inválido! Digite um CPF valido.");
            }
        } while (!ValidationUtils.isValidCPF(cpf));

        do {
            System.out.print("Digite seu data de nascimento (00-00-0000): ");
            dateOfBirth = scanner.nextLine();
            if (!ValidationUtils.isValidDate(dateOfBirth)) {
                System.out.println("Data de nacimento inválida! Digite no formato dd-MM-yyyy");
            }
        }while (!ValidationUtils.isValidDate(dateOfBirth));

        do {
            System.out.print("Digite seu endereço: ");
            address = scanner.nextLine();
            if (!ValidationUtils.isValidAddress(address)) {
                System.out.println("Endereço inválido! Digite novamente.");
            }
        } while (!ValidationUtils.isValidAddress(address));

        return new Client(name, cpf, dateOfBirth, address);
    }

    // Method to assign an account
    public String activatedAccount(Client client) {
        System.out.println("ATIVANDO CONTA CLIENTE");

        if (!client.getAccount()) {
            client.setAccount(true);
            return STR."Conta: \{client.getCpf()} - \{client.getName()} - ativada com sucesso!\n";
        } else {
            return STR."Conta: \{client.getCpf()} - \{client.getName()} - já está ativada!\n";
        }
    }

    // Method list users
    public String listUsers(List<Client> clients) {
        if (clients.isEmpty()) {
            return "Nenhum cliente cadastrado.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("===== Lista de Clientes =====\n");

        for (Client c : clients) {
            sb.append("Nome: ").append(c.getName()).append("\n")
                    .append("CPF: ").append(c.getCpf()).append("\n")
                    .append("Data de Nascimento: ").append(c.getDateOfBirth()).append("\n")
                    .append("Endereço: ").append(c.getAddress()).append("\n")
                    .append("---------------------------\n");
        }

        return sb.toString();
    }

    // Method list account
    public String listAccounts(List<Client> clients) {
        if (clients.isEmpty()) {
            return "Nenhuma conta cadastrada.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("===== LISTA DE CONTAS =====\n");

        for (Client c : clients) {
            sb.append("Nome: ").append(c.getName()).append("\n")
                    .append("CPF: ").append(c.getCpf()).append("\n")
                    .append("Conta ativa: ").append(c.getAccount() ? "Sim" : "Não").append("\n")
                    .append("---------------------------\n");
        }

        return sb.toString();
    }

    // Method that creates when there is no manager on the list
    public Manager createManager(String cpf) {
        System.out.println("CADASTRO DE GERENTE");

        // Requests customer data, verifies if it is correct, if not, validates the operations
        do {
            System.out.print("Digite seu nome: ");
            name = scanner.nextLine();
            if (!ValidationUtils.isValidName(name)) {
                System.out.println("Nome inválido! Use apenas letras e espaços.");
            }
        } while (!ValidationUtils.isValidName(name));

        do {
            System.out.print("Digite seu data de nascimento (00-00-0000): ");
            dateOfBirth = scanner.nextLine();
            if (!ValidationUtils.isValidDate(dateOfBirth)) {
                System.out.println("Data de nacimento inválida! Digite no formato dd-MM-yyyy");
            }
        } while (!ValidationUtils.isValidDate(dateOfBirth));

        do {
            System.out.print("Digite seu endereço: ");
            address = scanner.nextLine();
            if (!ValidationUtils.isValidAddress(address)) {
                System.out.println("Endereço inválido! Digite novamente.");
            }
        } while (!ValidationUtils.isValidAddress(address));

        do {
            System.out.print("Digite seu usuário (usuario.demonstrativo): ");
            user = scanner.nextLine();
            if (!ValidationUtils.isValidUser(user)) {
                System.out.println("Usuário inválido! Digite novamente.");
            }
        } while (!ValidationUtils.isValidUser(user));

        do {
            System.out.print("Digite seu password: ");
            password = scanner.nextLine();
        } while (!password.matches("^[a-zA-Z]+$"));

        return new Manager(name, cpf, dateOfBirth, address,  user, password);
    }
}
