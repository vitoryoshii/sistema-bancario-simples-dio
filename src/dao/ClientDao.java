package dao;

import models.Client;
import models.Transaction;
import util.BankRepository;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object (DAO) for the Client entity.
 * Responsible for **CRUD** (Create, Read, Update) operations and **transaction handling**
 * (deposit and withdrawal) on the CLIENT table in the Oracle database.
 */
public class ClientDao {
    private final TransactionDao transactionDao = new TransactionDao();

    /**
     * Inserts a new customer into the Oracle database.
     * The method now uses the {@link Client#getAccount()} and {@link Client#getBalance()}
     * for the initial status and balance.
     *
     * @param client The Client object to be saved, containing CPF, name, address, and date of birth.
     * @return {@code true} if the insertion is successful and the transaction is committed, {@code false} otherwise.
     * @throws SQLException If the CPF already exists or there is another database error.
     * The transaction is **rolled back** on error.
     */
    public boolean insertClient(Client client) {
        Connection connection = null;
        String sql = "INSERT INTO CLIENT (CPF, NAME, ADDRESS, BIRTH_DATE, STATUS_ACCOUNT, BALANCE) " +
                     "VALUES (?, ?, ?, TO_DATE(?, 'DD/MM/YYYY'), ?, ?)";

        try {
            connection = BankRepository.getConnection();

            try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, client.getCpf());
                stmt.setString(2, client.getName());
                stmt.setString(3, client.getAddress());
                stmt.setString(4, client.getDateOfBirth());
                stmt.setInt(5, client.getAccount() ? 1 : 0);
                stmt.setDouble(6, client.getBalance());

                if (stmt.executeUpdate() > 0) {
                    BankRepository.commitTransaction(connection);
                    return true;
                } else {
                    BankRepository.rollbackTransaction(connection);
                    return false;
                }
            }
        }  catch (SQLException e) {
            BankRepository.rollbackTransaction(connection);
            System.err.println("[ERRO DAO] - Falha ao cadastrar cliente: " + e.getMessage());
            return false;
        } finally {
            BankRepository.closeConnection(connection);
        }
    }

    /**
     * Searches for a customer by CPF in the database.
     * It maps the database data to a Client object.
     *
     * @param cpf The CPF (Primary Key candidate) to be searched.
     * @return An {@code Optional<Client>} containing the found customer, or
     * {@code Optional.empty()} if no customer with the given CPF is found.
     * @throws SQLException In case of an unexpected database access error.
     */
    public Optional<Client> findClientByCpf(String cpf) throws SQLException {
        String sql = "SELECT ID_CLIENT, CPF, NAME, ADDRESS, BIRTH_DATE, STATUS_ACCOUNT, BALANCE " +
                     "FROM CLIENT " +
                     "WHERE CPF = ?";

        try (Connection connection = BankRepository.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, cpf);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id_client");
                    String cpfClient = rs.getString("cpf");
                    String name = rs.getString("name");
                    String address = rs.getString("address");

                    String birthDateFormatted = null;
                    Date sqlDate = rs.getDate("birth_date");
                    if (sqlDate != null) {
                        LocalDate localDate = sqlDate.toLocalDate();
                        birthDateFormatted = localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    }

                    boolean statusAccount = rs.getBoolean("status_account");
                    double balance = rs.getDouble("balance");

                    Client client = new Client(name, cpfClient, birthDateFormatted, address);
                    client.setId(id);
                    client.setBalance(balance);
                    client.setAccount(statusAccount);

                    return Optional.of(client);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar cliente: " + e.getMessage());
        }

        return Optional.empty();
    }

    // --------------------------------------------------------------------------
    // TRANSACTION LOGIC (WITHDRAWAL AND DEPOSIT)
    // --------------------------------------------------------------------------

    /**
     * Attempts to make a **withdrawal**. This is an **atomic transaction**.
     * 1. Updates the balance in the CLIENT table by subtracting the value.
     * 2. Inserts the record into the TRANSACTION table.
     *
     * The entire operation is wrapped in a transaction (commit/rollback).
     *
     * @param client The client making the withdrawal (used to get the ID).
     * @param value The amount to be withdrawn.
     * @return {@code true} if both the balance update and transaction record insertion are successful, {@code false} otherwise.
     * @throws SQLException If the balance update fails or if an error occurs during the transaction insertion.
     */
    public boolean withdraw(Client client, double value) {
        Connection connection = null;
        String sql = "UPDATE CLIENT SET BALANCE = BALANCE - ? WHERE ID_CLIENT = ?";

        try {
            connection = BankRepository.getConnection();

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setDouble(1, value);
                stmt.setInt(2, client.getId());

                if (stmt.executeUpdate() == 0) {
                    throw new SQLException("Erro: Saldo não atualizado. Cliente não encontrado ou erro de BD.");
                }
            }

            Transaction transaction = new Transaction(client.getId(), "SAQUE", value);
            transactionDao.insertTransaction(connection, transaction);

            BankRepository.commitTransaction(connection);
            return true;
        } catch (SQLException e) {
            BankRepository.rollbackTransaction(connection);
            System.err.println("Falha na transação de saque. Desfeita: " + e.getMessage());
            return false;
        }  finally {
            BankRepository.closeConnection(connection);
        }
    }

    /**
     * Attempts to make a **deposit**. This is an **atomic transaction**.
     * 1. Updates the balance in the CLIENT table by adding the value.
     * 2. Inserts the record into the TRANSACTION table.
     *
     * The entire operation is wrapped in a transaction (commit/rollback).
     *
     * @param client The client making the deposit (used to get the ID).
     * @param value The amount to be deposited.
     * @return {@code true} if both the balance update and transaction record insertion are successful, {@code false} otherwise.
     * @throws SQLException If the balance update fails or if an error occurs during the transaction insertion.
     */
    public boolean deposit(Client client, double value) {
        Connection connection = null;
        String sql = "UPDATE CLIENT SET BALANCE = BALANCE + ? WHERE ID_CLIENT = ?";

        try {
            connection = BankRepository.getConnection();

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setDouble(1, value);
                stmt.setInt(2, client.getId());

                if (stmt.executeUpdate() == 0) {
                    throw new SQLException("Erro: Saldo não atualizado. Cliente não encontrado ou erro de BD.");
                }
            }

            Transaction transaction = new Transaction(client.getId(), "DEPOSITO", value);
            transactionDao.insertTransaction(connection, transaction);

            BankRepository.commitTransaction(connection);
            return true;
        } catch (SQLException e) {
            BankRepository.rollbackTransaction(connection);
            System.err.println("Falha na transação de deposito. Desfeita: " + e.getMessage());
            return false;
        }  finally {
            BankRepository.closeConnection(connection);
        }
    }

    /**
     * Updates the activation status of a customer's account based on their CPF.
     *
     * @param cpf The CPF of the customer whose account status will be updated.
     * @param status The new status: {@code true} for active, {@code false} for inactive (mapped to 1 or 0 in the database).
     * @return {@code true} if the account status is successfully updated and the transaction is committed, {@code false} otherwise.
     * @throws SQLException In case of an unexpected database access error.
     */
    public boolean updateAccountStatus(String cpf, boolean status) {
        Connection conn = null;
        String sql = "UPDATE CLIENT SET STATUS_ACCOUNT = ? WHERE CPF = ?";
        try {
            conn = BankRepository.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setBoolean(1, status);
                stmt.setString(2, cpf);

                if (stmt.executeUpdate() > 0) {
                    BankRepository.commitTransaction(conn);
                    return true;
                }
            }
        } catch (SQLException e) {
            BankRepository.rollbackTransaction(conn);
            System.err.println("Erro ao atualizar status da conta: " + e.getMessage());
        } finally {
            BankRepository.closeConnection(conn);
        }
        return false;
    }

    /**
     * Retrieves a list of all customers registered in the database.
     * The result includes all registration and account data and is ordered alphabetically by the customer's name.
     *
     * @return A {@code List<Client>} containing all customers found. Returns an **empty list** if no customers are found or an error occurs.
     * @throws SQLException In case of an unexpected database access error (handled internally).
     */
    public List<Client> findAllClients() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT ID_CLIENT, CPF, NAME, ADDRESS, BIRTH_DATE, STATUS_ACCOUNT, BALANCE " +
                     "FROM CLIENT " +
                     "ORDER BY NAME";

        try (Connection conn = BankRepository.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String formattedDate = null;
                Date sqlDate = rs.getDate("birth_date");
                if (sqlDate != null) {
                    LocalDate localDate = sqlDate.toLocalDate();
                    formattedDate = localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                }

                Client client = new Client(
                        rs.getString("name"),
                        rs.getString("cpf"),
                        formattedDate,
                        rs.getString("address")
                );

                client.setId(rs.getInt("id_client"));
                client.setBalance(rs.getDouble("balance"));
                client.setAccount(rs.getBoolean("status_account"));

                clients.add(client);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar todos os clientes: " + e.getMessage());
        }

        return clients;
    }


    /**
     * Updates a customer's registration data (name, date of birth, address) based on their CPF.
     *
     * The method includes logic for parsing the date string from the Client object,
     * supporting "yyyy-MM-dd" or "dd/MM/yyyy" formats, and handling null/invalid dates.
     *
     * @param client The Client object containing the CPF (key) and the new data to be updated.
     * @return {@code true} if the update is successful and the transaction is committed, {@code false} otherwise.
     * @throws SQLException In case of an unexpected database access error.
     */
    public boolean updateClientInfo(Client client) {
        Connection conn = null;
        String sql = "UPDATE CLIENT SET NAME = ?, BIRTH_DATE = ?, ADDRESS = ? " +
                     "WHERE CPF = ?";

        try {
            conn = BankRepository.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, client.getName());

                String dateStr = client.getDateOfBirth().toString().trim();
                LocalDate localDate = null;
                if (dateStr.contains(" ")) {
                    dateStr = dateStr.split(" ")[0];
                }

                try {
                    if (dateStr.contains("/")) {
                        localDate = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    } else if (dateStr.contains("-")) {
                        localDate = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    }
                } catch (Exception e) {
                    System.err.println("[AVISO] - Formato de data inválido: " + dateStr);
                }

                if (localDate != null) {
                    stmt.setDate(2, Date.valueOf(localDate));
                } else {
                    stmt.setNull(2, java.sql.Types.DATE);
                }
                    stmt.setString(3, client.getAddress());
                    stmt.setString(4, client.getCpf());

                if (stmt.executeUpdate() > 0) {
                    BankRepository.commitTransaction(conn);
                    return true;
                }
            }
        } catch (SQLException e) {
            BankRepository.rollbackTransaction(conn);
            System.err.println("Erro ao atualizar dados cadastrais do cliente: " + e.getMessage());
        } finally {
            BankRepository.closeConnection(conn);
        }
        return false;
    }
}
