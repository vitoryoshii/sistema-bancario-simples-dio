package dao;

import models.Manager;
import util.BankRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Data Access Object (DAO) for the Manager entity.
 * Responsible for managing persistence in the MANAGER table, including
 * **insertion** and **search by user/login**.
 */
public class ManagerDao {
    /**
     * Inserts a new manager into the database.
     * The manager's registration date is automatically set using {@code SYSDATE}.
     *
     * @param manager The {@code Manager} object to be saved. It must already contain the
     * password hash and other required data (CPF, name, user, etc.).
     * @return {@code true} if the insertion is successful and the transaction is committed, {@code false} otherwise.
     * @throws SQLException If a database error occurs (e.g., CPF/login already registered).
     * The transaction is **rolled back** on failure.
     */
    public boolean insertManager(Manager manager) {
        Connection connection = null;
        String sql = "INSERT INTO SYSTEM.MANAGER (CPF, NAME, ADDRESS, BIRTH_DATE, \"USER\", PASSWORD, REGISTER_DATE) " +
                     "VALUES (?, ?, ?, TO_DATE(?, 'DD/MM/YYYY'), ?, ?, SYSDATE)";

        try {
            connection = BankRepository.getConnection();

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, manager.getCpf());
                stmt.setString(2, manager.getName());
                stmt.setString(3, manager.getAddress());
                stmt.setString(4, manager.getDateOfBirth());
                stmt.setString(5, manager.getUser());
                stmt.setString(6, manager.getPassword());

                if (stmt.executeUpdate() > 0) {
                    BankRepository.commitTransaction(connection);
                    return true;
                } else {
                    BankRepository.rollbackTransaction(connection);
                    return false;
                }
            }
        } catch (SQLException e) {
            BankRepository.rollbackTransaction(connection);
            System.err.println("[ERRO DAO] - Falha ao cadastrar gerente: " + e.getMessage());
            return false;
        } finally {
            BankRepository.closeConnection(connection);
        }
    }

    /**
     * Searches for a manager by their user login in the database.
     *
     * @param user The username (login) to be searched.
     * @return An {@code Optional<Manager>} containing the found manager, or
     * {@code Optional.empty()} if no manager with the given user is found.
     * **Note:** Returns {@code null} if a {@code SQLException} occurs during the search.
     * @throws SQLException In case of an unexpected database access error.
     */
    public Optional<Manager> findManagerByUser(String user) throws SQLException {
        Connection connection = null;
        String sql = "SELECT ID_MANAGER, CPF, NAME, ADDRESS, BIRTH_DATE, \"USER\", PASSWORD " +
                     "FROM MANAGER " +
                     "WHERE \"USER\" = ?";

        try{
            connection = BankRepository.getConnection();
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, user);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int id = rs.getInt("id_manager");
                        String cpfClient = rs.getString("cpf");
                        String name = rs.getString("name");
                        String address = rs.getString("address");
                        String birthDate = rs.getString("birth_date");
                        String managerUser = rs.getString("user");
                        String password = rs.getString("password");

                        Manager manager = new Manager(name, cpfClient, birthDate, address, managerUser,  password);
                        manager.setId(id);

                        return Optional.of(manager);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar cliente: " + e.getMessage());
        } finally {
            BankRepository.closeConnection(connection);
        }
        return null;
    }
}
