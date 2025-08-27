import java.awt.*;
import java.util.Scanner;

public class Manager {

    // Variables to control the Manager internal methods
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

    // Method to create new customers
    public Client createClient() {
        Scanner scanner = new Scanner(System.in);

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
            return "Conta: " + client.getCpf() + " - " + client.getName() + " - ativada com sucesso!";
        } else {
            return "Conta: " + client.getCpf() +  " - " + client.getName() + " - já está ativada!";
        }
    }

    // Method list users
    public String listUsers() {
        if (MainSystemBank.clients.isEmpty()) {
            return "Nenhum cliente cadastrado.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("===== Lista de Clientes =====\n");

        for (Client c : MainSystemBank.clients) {
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
        if (MainSystemBank.clients.isEmpty()) {
            return "Nenhum cliente cadastrado.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("===== LISTA DE CONTAS =====\n");

        for (Client c : MainSystemBank.clients) {
            sb.append("Nome: ").append(c.getName()).append("\n")
                    .append("CPF: ").append(c.getCpf()).append("\n")
                    .append("Conta ativa: ").append(c.isAccount() ? "Sim" : "Não").append("\n")
                    .append("---------------------------\n");
        }

        return sb.toString();
    }

    // Method search customer by CPF
    public Client searchCustomerByCPF(String cpf) {
        for (Client c : MainSystemBank.clients) {
            if (c.getCpf().equals(cpf)) {
                return c; // return client found
            }
        }
        return null; // return null
    }
}
