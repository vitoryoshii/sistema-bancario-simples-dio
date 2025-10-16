package menus;

import dao.ClientDao;
import models.Client;
import models.Manager;
import util.InputUtils;
import util.ValidationUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Represents the dedicated administrative menu interface for a logged-in bank manager.
 * <p>
 * This class implements the {@code Menu} interface and provides functionality for
 * managing client accounts, including creation, activation/deactivation, listing,
 * and updating registration information.
 */
public class ManagerMenu implements Menu{
    private Manager manager;
    private Scanner scanner;
    private ClientDao clientDAO = new ClientDao();

    /**
     * Constructs the Manager Menu, setting the context for the logged-in manager.
     *
     * @param manager The {@link Manager} object representing the authenticated user.
     * @param scanner The {@code Scanner} object for reading user input.
     */
    public ManagerMenu(Manager manager, Scanner scanner) {
        this.manager = manager;
        this.scanner = scanner;
    }

    /**
     * Displays the manager menu options and handles the core interaction loop.
     * <p>
     * Options include: Create Client, Activate/Deactivate Account, List Clients,
     * List Accounts, Update Registration Info, and Log Out (0).
     */
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
                case 2 -> activateClientAccount();
                case 3 -> listAllClients();
                case 4 -> listAllAccounts();
                case 5 -> updateClient();
                case 0 -> System.out.println("VOLTANDO...\n");
                default -> System.out.println("[ERRO] - OPÇÃO INVÁLIDA\n");
            }
        } while (option != 0);
    }

    /**
     * Initiates the account creation process for a new {@link Client}.
     * <p>
     * Gathers client details (via {@code Manager.createClient()}) and attempts to
     * persist the new client to the database via {@link ClientDao#insertClient(Client)}.
     */
    public void createClient() {
        Client newClient = manager.createClient();

        if (clientDAO.insertClient(newClient)) {
            System.out.println("\n[SUCESSO] - CLIENTE " + newClient.getName() + " CADASTRADO COM SUCESSO!");
        } else {
            System.out.println("[ERRO] - CLIENTE NÃO CADASTRADO!");
        }
    }

    /**
     * Prompts for a client's CPF and toggles the activation status of their account.
     * <p>
     * The new status is the opposite of the current status. The update is performed
     * using {@link ClientDao#updateAccountStatus(String, boolean)}.
     */
    private void activateClientAccount() {
        System.out.println("\n=== ATIVAÇÃO/DESATIVAÇÃO DE CONTA ===");
        InputUtils.clearBuffer(scanner);
        String cpf = InputUtils.getValidCPF(scanner);

        Optional<Client> clientOpt = null;
        try {
            clientOpt = clientDAO.findClientByCpf(cpf);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar cliente: " + e.getMessage());
            return;
        }

        if (clientOpt.isPresent()) {
            Client client = clientOpt.get();
            boolean newStatus = !client.getAccount();
            String statusText = newStatus ? "ATIVA" : "INATIVA";

            if (clientDAO.updateAccountStatus(client.getCpf(), newStatus)) {
                client.setAccount(newStatus);
                System.out.println("[SUCESSO] - CONTA DO CLIENTE " + client.getName() + " AGORA ESTÁ " + statusText + ".\n");
            } else {
                System.out.println("[ERRO] - FALHA AO ATUALIZAR STATUS DO BD.");
            }

        } else {
            System.out.println("[ERRO] - CLIENTE COM CPF: " + cpf + " NÃO ENCONTRADO.");
        }
    }

    /**
     * Retrieves all client registration records from the database and prints a formatted list
     * containing name, CPF, birth_date, and address.
     * <p>
     * Uses {@link ClientDao#findAllClients()} to fetch the data.
     */
    private void listAllClients() {
        System.out.println("\n=== LISTA DE CLIENTES ===");

        List<Client> clients = clientDAO.findAllClients();

        if (clients.isEmpty()) {
            System.out.println("[INFO] - NENHUM CLIENTE CADASTRADO.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Client c : clients) {
            sb.append("ID: ").append(c.getId()).append("\n")
                    .append("NOME: ").append(c.getName()).append("\n")
                    .append("CPF: ").append(c.getCpf()).append("\n")
                    .append("DATA DE NASCIMENTO: ").append(c.getDateOfBirth()).append("\n")
                    .append("ENDEREÇO: ").append(c.getAddress()).append("\n")
                    .append("---------------------------\n");
        }
        sb.toString();
        System.out.println(sb);
    }

    /**
     * Retrieves all client account records from the database and prints a formatted list
     * containing name, CPF, account status, and current balance.
     * <p>
     * Uses {@link ClientDao#findAllClients()} to fetch the data.
     */
    private void listAllAccounts() {
        System.out.println("\n=== LISTA DE CONTAS ===");

        List<Client> clients = clientDAO.findAllClients();

        if (clients.isEmpty()) {
            System.out.println("[INFO] - NENHUMA CONTA CADASTRADA.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Client c : clients) {
            sb.append("ID: ").append(c.getId()).append("\n")
                    .append("NOME: ").append(c.getName()).append("\n")
                    .append("CPF: ").append(c.getCpf()).append("\n")
                    .append("CONTA ATIVA: ").append(c.getAccount() ? "SIM" : "NÃO").append("\n")
                    .append("SALDO DA CONTA: ").append(c.getBalance()).append("\n")
                    .append("---------------------------\n");
        }
        sb.toString();
        System.out.println(sb);
    }

    /**
     * Manages the flow for updating a client's registration information (name, birth date, address).
     * <p>
     * Prompts the manager for the client's CPF, fetches the current data, and allows for optional
     * updates to individual fields with validation (via {@link ValidationUtils}).
     * Persists changes using {@link ClientDao#updateClientInfo(Client)}.
     */
    public void updateClient() {
        System.out.println("\n==== ATUALIZAÇÃO CADASTRAL ====");
        scanner.nextLine();

        String cpf = InputUtils.getValidCPF(scanner);

        Optional<Client> clientOpt;
        try {
            clientOpt = clientDAO.findClientByCpf(cpf);
        } catch (SQLException e) {
            System.err.println("[ERRO] - Buscar cliente: " + e.getMessage());
            return;
        }

        if (!clientOpt.isPresent()) {
            System.out.println("[ERRO] - CLIENTE COM CPF: " + cpf + " NÃO ENCONTRADO.");
            return;
        }

        Client client = clientOpt.get();
        boolean updated = false;

        System.out.println("\nCLIENTE ENCONTRADO: " + client.getName());
        System.out.println("----------------------------------------");

        // ==== NAME ====
        System.out.println("NOME ATUAL: [" + client.getName() + "]");
        System.out.print("DIGITE NOVO NOME (ou ENTER para manter): ");
        String newName = scanner.nextLine().trim();
        if (!newName.isBlank()) {
            if (ValidationUtils.isValidName(newName)) {
                client.setName(newName);
                updated = true;
            } else {
                System.out.println("[AVISO] - NOME INVÁLIDO. Mantendo o valor atual.");
            }
        }

        // ==== BIRTH DATE ====
        System.out.println("\nDATA NASC. ATUAL: [" + client.getDateOfBirth() + "]");
        System.out.print("DIGITE NOVA DATA (DD-MM-YYYY) (ou ENTER para manter): ");
        String newDate = scanner.nextLine().trim();
        if (!newDate.isBlank()) {
            if (ValidationUtils.isValidDate(newDate)) {
                client.setDateOfBirth(newDate);
                updated = true;
            } else {
                System.out.println("[AVISO] - DATA INVÁLIDA. Mantendo o valor atual.");
            }
        }

        // ==== ADDRESS ====
        System.out.println("\nENDEREÇO ATUAL: [" + client.getAddress() + "]");
        System.out.print("DIGITE NOVO ENDEREÇO (ou ENTER para manter): ");
        String newAddress = scanner.nextLine().trim();
        if (!newAddress.isBlank()) {
            if (ValidationUtils.isValidAddress(newAddress)) {
                client.setAddress(newAddress);
                updated = true;
            } else {
                System.out.println("[AVISO] - ENDEREÇO INVÁLIDO. Mantendo o valor atual.");
            }
        }

        // ==== SALVAR ====
        if (updated) {
            if (clientDAO.updateClientInfo(client)) {
                System.out.println("\n[SUCESSO] - DADOS DO CLIENTE ATUALIZADO NO SISTEMA!\n");
                System.out.println("Novo Nome: " + client.getName());
                System.out.println("Nova Data de Nascimento: " + client.getDateOfBirth());
                System.out.println("Novo Endereço: " + client.getAddress());
            } else {
                System.out.println("\n[ERRO] - FALHA AO SALVAR NO BANCO DE DADOS.\n");
            }
        } else {
            System.out.println("\n[INFO] - NENHUMA ATUALIZAÇÃO VALIDA FOI REALIZADA. DADOS MANTIDOS!\n");
        }
    }

}
