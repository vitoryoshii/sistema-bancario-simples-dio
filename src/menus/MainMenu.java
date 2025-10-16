package menus;

import dao.ClientDao;
import dao.ManagerDao;
import models.Client;
import models.Manager;
import util.InputUtils;
import util.ValidationUtils;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

/**
 * Represents the main interaction point and primary navigation interface for the Bank System.
 * <p>
 * This class implements the {@code Menu} interface and is responsible for routing users
 * to the {@link ClientMenu} or {@link ManagerMenu} (implied) after successful login,
 * and handling the initial creation of a Manager account.
 */
public class MainMenu implements Menu{
    private Scanner scanner;
    private ClientDao clientDAO =  new ClientDao();
    private ManagerDao managerDAO =  new ManagerDao();

    /**
     * Constructs the Main Menu, initializing it with a scanner for user input.
     *
     * @param scanner The {@code Scanner} object used to capture user input from the console.
     */
    public MainMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Displays the main menu options and handles the core interaction loop.
     * <p>
     * Options include: Client Login, Manager Login, Manager Registration, and Exit.
     */
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

    /**
     * Manages the client login process using CPF validation and account status checks.
     * <p>
     * If successful and the account is active, it launches the {@link ClientMenu}.
     * Handles {@code SQLException} during the database lookup.
     */
    private void clientLogin() {
        System.out.println("\n===== LOGIN CLIENTE =====");

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
                System.out.println("[SUCESSO] - BEM-VINDO(A), " + client.getName() + "!\n");
                new ClientMenu(client, scanner).exibir();
            } else {
                System.out.println("[ERRO] - SUA CONTA ESTÁ INATIVA. Contate o gerente para ativação.");
            }
        } else {
            System.out.println("[ERRO] - Cliente com CPF " + cpf + " não encontrado.");
        }
    }

    /**
     * Manages the **manager login** flow.
     * <p>
     * Prompts for username and password, performs a database lookup via {@link ManagerDao#findManagerByUser(String)},
     * and delegates password and authentication logic to {@link ValidationUtils#checkUsernamePassword(Scanner, String, String)}.
     */
    private void managerLogin() {
        System.out.println("\n===== LOGIN GERENTE =====");
        System.out.print("USUÁRIO: ");
        String user = scanner.next().trim();
        System.out.print("SENHA: ");
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

    /**
     * Initiates the account creation process for a new Manager.
     * <p>
     * Prompts the user for all necessary details (handled by {@code Manager.createManager()}) and attempts to persist the new {@link Manager}
     * object to the database via {@link ManagerDao#insertManager(Manager)}.
     */
    private void createManagerAccount() {
        System.out.println("\n=== CADASTRO DE GERENTE ===");
        Manager manager = new Manager();

        Manager newManager = manager.createManager();

        if (new ManagerDao().insertManager(newManager)) {
            System.out.println("\n[SUCESSO] - GERENTE " + newManager.getName() + " CADASTRADO COM SUCESSO!");
            System.out.println("Por favor, faça LOGIN com seu novo USUÁRIO/SENHA.\n");
        } else {
            System.out.println("[ERRO] - GERENTE NÃO CADASTRADO!\n");
        }
    }
}
