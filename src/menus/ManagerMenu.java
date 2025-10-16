package menus;

import dao.ClientDao;
import models.Client;
import models.Manager;
import util.BankRepository;
import util.InputUtils;
import util.ValidationUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ManagerMenu implements Menu{
    private Manager manager;
    private Scanner scanner;
    private ClientDao clientDAO = new ClientDao();

    public ManagerMenu(Manager manager, Scanner scanner) {
        this.manager = manager;
        this.scanner = scanner;
    }

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

    public void createClient() {
        Client newClient = manager.createClient();

        if (clientDAO.insertClient(newClient)) {
            System.out.println("\n[SUCESSO] - Cliente " + newClient.getName() + " cadastrado com sucesso!");
        } else {
            System.out.println("[ERRO] - Cliente não cadastrado!");
        }
    }

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
                System.out.println("[SUCESSO] - Conta do cliente " + client.getName() + " AGORA está " + statusText + ".\n");
            } else {
                System.out.println("[ERRO] - Falha ao atualizar status no BD.");
            }

        } else {
            System.out.println("[ERRO] - Cliente com CPF " + cpf + " não encontrado.");
        }
    }

    private void listAllClients() {
        System.out.println("\n=== LISTA DE CLIENTES ===");

        List<Client> clients = clientDAO.findAllClients();

        if (clients.isEmpty()) {
            System.out.println("[INFO] - Nenhum cliente cadastrado.");
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

    private void listAllAccounts() {
        System.out.println("\n=== LISTA DE CONTAS ===");

        List<Client> clients = clientDAO.findAllClients();

        if (clients.isEmpty()) {
            System.out.println("[INFO] - Nenhuma conta cadastrado.");
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

    public void updateClient() {
        System.out.println("\nATUALIZAÇÃO CADASTRAL");
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
            System.out.println("[ERRO] - Cliente com CPF " + cpf + " não encontrado.");
            return;
        }

        Client client = clientOpt.get();
        boolean updated = false;

        System.out.println("\nCLIENTE ENCONTRADO: " + client.getName());
        System.out.println("----------------------------------------");

        // ==== NOME ====
        System.out.println("NOME ATUAL: [" + client.getName() + "]");
        System.out.print("DIGITE NOVO NOME (ou ENTER para manter): ");
        String newName = scanner.nextLine().trim();
        if (!newName.isBlank()) {
            if (ValidationUtils.isValidName(newName)) {
                client.setName(newName);
                updated = true;
            } else {
                System.out.println("[AVISO] - Nome inválido. Mantendo o valor atual.");
            }
        }

        // ==== DATA DE NASCIMENTO ====
        System.out.println("DATA NASC. ATUAL: [" + client.getDateOfBirth() + "]");
        System.out.print("DIGITE NOVA DATA (DD-MM-YYYY) (ou ENTER para manter): ");
        String newDate = scanner.nextLine().trim();
        if (!newDate.isBlank()) {
            if (ValidationUtils.isValidDate(newDate)) {
                client.setDateOfBirth(newDate);
                updated = true;
            } else {
                System.out.println("[AVISO] - Data inválida. Mantendo o valor atual.");
            }
        }

        // ==== ENDEREÇO ====
        System.out.println("ENDEREÇO ATUAL: [" + client.getAddress() + "]");
        System.out.print("DIGITE NOVO ENDEREÇO (ou ENTER para manter): ");
        String newAddress = scanner.nextLine().trim();
        if (!newAddress.isBlank()) {
            if (ValidationUtils.isValidAddress(newAddress)) {
                client.setAddress(newAddress);
                updated = true;
            } else {
                System.out.println("[AVISO] - Endereço inválido. Mantendo o valor atual.");
            }
        }

        // ==== SALVAR ====
        if (updated) {
            if (clientDAO.updateClientInfo(client)) {
                System.out.println("\n[SUCESSO] - Dados do cliente atualizados no sistema!");
                System.out.println("Novo Nome: " + client.getName());
                System.out.println("Nova Data de Nascimento: " + client.getDateOfBirth());
                System.out.println("Novo Endereço: " + client.getAddress());
            } else {
                System.out.println("\n[ERRO] - Falha ao salvar no banco de dados.");
            }
        } else {
            System.out.println("\n[INFO] - Nenhuma alteração válida foi realizada. Dados mantidos.");
        }
    }

}
