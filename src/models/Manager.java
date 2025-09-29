package models;

import java.util.List;
import java.util.Scanner;

import util.InputUtils;
import util.ValidationUtils;

public class Manager extends User {
    private static final Scanner scanner = new Scanner(System.in);

    // Variables to control the models.Manager internal methods
    private String user;
    private String password;

    // constructor method of the manager object
    public Manager(String name, String cpf, String dateOfBirth, String address, String user, String password) {
        super(name, cpf, dateOfBirth, address);
        this.user = user;
        this.password = password;
    }

    // constructor method empty
    public Manager() {
        super();
    }

    // Method to return in attributes
    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    // Method to create new customers
    public Client createClient() {
        // Capture customer information so you can create
        String clientName, clientCPF, clientDateOfBirth, clientAddress;

        System.out.println("CADASTRO DE CLIENTE");

        // Call the responsible functions to capture the information
        clientName = InputUtils.getName(scanner);
        clientCPF = InputUtils.getValidCPF(scanner);
        clientDateOfBirth = InputUtils.getDateOfBirth(scanner);
        clientAddress = InputUtils.getAddress(scanner);

        return new Client(clientName, clientCPF, clientDateOfBirth, clientAddress);
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
    public static Manager createManager(String cpf) {
        String managerName, managerDateOfBirth, managerAddress, managerUser, managerPassword;

        System.out.println("CADASTRO DE GERENTE");

        // Call the responsible functions to capture the information
        managerName = InputUtils.getName(scanner);
        managerDateOfBirth = InputUtils.getDateOfBirth(scanner);
        managerAddress = InputUtils.getAddress(scanner);
        managerUser = InputUtils.getUser(scanner);
        managerPassword = InputUtils.getPassword(scanner);

        return new Manager(managerName, cpf, managerDateOfBirth, managerAddress, managerUser, managerPassword);
    }

    public void updateRegistration(Client client) {
        scanner.useDelimiter("\\n");

        System.out.println("\nATUALIZAÇÃO CADASTRAL");

        if (client == null) {
            System.out.println("[ERRO] - CPF NÃO ENCONTRADO!\n");
            return;
        }

        // Update name or maintains the current
        System.out.println("NOME ATUAL: [" + client.getName() + "] - ENTER PARA PULAR ALTERAÇÃO");
        String nameUpdate = scanner.next();
        if (!nameUpdate.isBlank()) {
            if (ValidationUtils.isValidName(nameUpdate)) {
                client.setName(nameUpdate);
            } else {
                System.out.println("[ERRO] - NOME INVÁLIDO, MANTENDO O VALOR ANTIGO.\n");
            }
        }

        // Update date birthed or maintains
        System.out.println("DATA ATUAL: [" + client.getDateOfBirth() + "] - ENTER PARA PULAR ALTERAÇÃO");
        String dateBirthedUpdate = scanner.next();
        if (!dateBirthedUpdate.isBlank()) {
            if (ValidationUtils.isValidDate(dateBirthedUpdate)) {
                client.setDateOfBirth(dateBirthedUpdate);
            } else {
                System.out.println("[ERRO] - DATA INVÁLIDA, MANTENDO O VALOR ANTIGO.\n");
            }
        }

        // Update address or maintains the current
        System.out.println("ENDEREÇO ATUAL: [" + client.getAddress() + "] - ENTER PARA PULAR ALTERAÇÃO");
        String addressUpdate = scanner.next();
        if (!addressUpdate.isBlank()) {
            if (ValidationUtils.isValidAddress(addressUpdate)) {
                client.setAddress(addressUpdate);
            } else {
                System.out.println("[ERRO] - ENDEREÇO INVÁLIDO, MANTENDO O VALOR ANTIGO.\n");
            }
        }

        // String the updated data
        System.out.println("\n[SUCESSO] - DADOS ATUALIZADO!\n");

        StringBuilder sb = new StringBuilder();
        sb.append("---------------------------\n")
                .append("NOME: ").append(client.getName()).append("\n")
                .append("CPF: ").append(client.getCpf()).append("\n")
                .append("DATA DE NASCIMENTO: ").append(client.getDateOfBirth()).append("\n")
                .append("ENDEREÇO: ").append(client.getAddress()).append("\n")
                .append("---------------------------\n");
        System.out.println(sb);
    }
}
