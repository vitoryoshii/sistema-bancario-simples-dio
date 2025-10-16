package menus;

import dao.ClientDao;
import dao.ManagerDao;
import dao.TransactionDao;
import models.Client;
import models.Manager;
import util.BankRepository;
import util.InputUtils;
import util.ValidationUtils;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

public class MainMenu implements Menu{
    private Scanner scanner;
    private ClientDao clientDAO =  new ClientDao();
    private ManagerDao managerDAO =  new ManagerDao();

    public MainMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void exibir() {
        do {
            System.out.println("===== BANCO DIGITAL =====");
            System.out.println("1 - ACESSO CLIENTE");
            System.out.println("2 - ACESSO GERENTE");
            System.out.println("=========================");
            System.out.println("3 - CADASTRO DE GERENCIAS");
            System.out.println("=========================");
            System.out.println("0 - SAIR");
            System.out.println("=========================");
            System.out.print("OPÇÃO: ");

            int option = scanner.nextInt();

            switch (option) {
                case 1 -> clientLogin();
                case 2 -> managerLogin();
                case 3 -> createManagerAccount();
                case 0 -> {
                    System.out.println("Encerrando o sistema...");
                    return;
                }
                default -> System.out.println("[ERRO] - OPÇÃO INVÁLIDA!\n");
            }
        } while (true);
    }

    public void clientLogin() {
        System.out.println("\n--- LOGIN CLIENTE ---");

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

            if (client.getAccount()) {
                System.out.println("[SUCESSO] - BEM-VINDO(A), " + client.getName() + "!");
                new ClientMenu(client, scanner).exibir();
            } else {
                System.out.println("[ERRO] - Sua conta está INATIVA. Contate o gerente para ativação.");
            }
        } else {
            System.out.println("[ERRO] - Cliente com CPF " + cpf + " não encontrado.");
        }
    }

    /**
     * Gerencia o fluxo de login do gerente (usa ValidationUtils para autenticação segura).
     */
    private void managerLogin() {
        System.out.println("\n--- LOGIN GERENTE ---");
        System.out.println("USUÁRIO: ");
        String user = scanner.next().trim();
        System.out.println("SENHA: ");
        String password = scanner.next().trim();

        Optional<Manager> managerOpt = null;
        try {
            managerOpt = managerDAO.findManagerByUser(user);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar cliente: " + e.getMessage());
            return;
        }

        ValidationUtils.checkUsernamePassword(scanner, user, password);
    }

    private void createManagerAccount() {
        System.out.println("\n=== CADASTRO DO PRIMEIRO GERENTE ===");
        Manager manager = new Manager();

        Manager newManager = manager.createManager();

        if (new ManagerDao().insertManager(newManager)) {
            System.out.println("\n[SUCESSO] - Gerente " + newManager.getName() + " cadastrado com sucesso!");
            System.out.println("Por favor, faça LOGIN com seu novo USUÁRIO/SENHA.\n");
        } else {
            System.out.println("[ERRO] - Gerente não cadastrado!");
        }
    }
}
