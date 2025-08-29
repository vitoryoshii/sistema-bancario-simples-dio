package models;

import main.MainSystemBank;

import java.util.Scanner;

public class Manager {
    private Scanner scanner =  new Scanner(System.in);
    private MainSystemBank mainSystemBank = new MainSystemBank();

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
            System.out.println("Digite seu nome: ");
            name = scanner.nextLine();
        } while (!name.matches("[a-zA-Z ]+"));

        do {
            System.out.println("Digite seu cpf (000.000.000-00): ");
            cpf = scanner.nextLine();
        } while (!cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}"));

        do {
            System.out.println("Digite seu data de nascimento (00-00-0000): ");
            dateOfBirth = scanner.nextLine();
        }while (!dateOfBirth.matches("\\d{2}\\-\\d{2}\\-\\d{4}"));

        do {
            System.out.println("Digite seu endereço: ");
            address = scanner.nextLine();
        } while (!address.matches("[a-zA-ZÀ-ÿ0-9 .,-º]+"));

        return new Client(name, cpf, dateOfBirth, address);
    }

    // Method to assign an account
    public String activatedAccount(Client client) {
        System.out.println("ATIVANDO CONTA CLIENTE");

        if (!client.isAccount()) {
            client.setAccount(true);
            return STR."Conta: \{client.getCpf()} - \{client.getName()} - ativada com sucesso!";
        } else {
            return STR."Conta: \{client.getCpf()} - \{client.getName()} - já está ativada!";
        }
    }

    // Method list users
    public String listUsers() {
        if (mainSystemBank.getClients().isEmpty()) {
            return "Nenhum cliente cadastrado.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("===== Lista de Clientes =====\n");

        for (Client c : mainSystemBank.getClients()) {
            sb.append("Nome: ").append(c.getName()).append("\n")
                    .append("CPF: ").append(c.getCpf()).append("\n")
                    .append("Data de Nascimento: ").append(c.getDateOfBirth()).append("\n")
                    .append("Endereço: ").append(c.getAddress()).append("\n")
                    .append("---------------------------\n");
        }

        return sb.toString();
    }

    // Method list account
    public String listAccounts() {
        if (mainSystemBank.getClients().isEmpty()) {
            return "Nenhuma conta cadastrada.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("===== LISTA DE CONTAS =====\n");

        for (Client c : mainSystemBank.getClients()) {
            sb.append("Nome: ").append(c.getName()).append("\n")
                    .append("CPF: ").append(c.getCpf()).append("\n")
                    .append("Conta ativa: ").append(c.isAccount() ? "Sim" : "Não").append("\n")
                    .append("---------------------------\n");
        }

        return sb.toString();
    }

    // Method that creates when there is no manager on the list
    public Manager createManager() {
        System.out.println("CADASTRO DE GERENTE");

        // Requests customer data, verifies if it is correct, if not, validates the operations
        do {
            System.out.print("Digite seu nome: ");
            name = scanner.nextLine();
        } while (!name.matches("[a-zA-Z ]+"));

        do {
            System.out.println("Digite seu cpf (000.000.000-00): ");
            cpf = scanner.nextLine();
        } while (!cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}"));

        do {
            System.out.println("Digite seu data de nascimento (00-00-0000): ");
            dateOfBirth = scanner.nextLine();
        }while (!dateOfBirth.matches("\\d{2}\\-\\d{2}\\-\\d{4}"));

        do {
            System.out.println("Digite seu endereço: ");
            address = scanner.nextLine();
        } while (!address.matches("[a-zA-ZÀ-ÿ0-9 .,-º]+"));

        do {
            System.out.println("Digite seu usuário (usuario.demonstrativo): ");
            user = scanner.nextLine();
        } while (!user.matches("^[a-zA-Z]+\\.[a-zA-Z]+$"));

        do {
            System.out.println("Digite seu password: ");
            password = scanner.nextLine();
        } while (!password.matches("^[a-zA-Z]+$"));

        return new Manager(name, cpf, dateOfBirth, address,  user, password);
    }
}
