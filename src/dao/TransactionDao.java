package dao;

import models.Transaction;
import util.BankRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for the Transaction entity.
 * Responsible for **inserting** new transaction records and **searching** the transaction history (extract).
 */
public class TransactionDao {
    /**
     * Inserts a new transaction record into the Oracle database.
     * <p>
     * This method is designed to be part of a larger, **atomic database transaction** * (e.g., a deposit or withdrawal). It does **not** manage the connection lifecycle
     * (opening/closing) or transaction control (commit/rollback).
     *
     * @param connection The active JDBC connection. It must have {@code autoCommit=false}
     * and be managed by the calling method (e.g., in {@code ClientDao.deposit()}).
     * @param transaction The {@code Transaction} object to save, containing the client ID, type, and value.
     * @throws SQLException If an error occurs while executing the SQL statement (e.g., connection issue).
     */
    public void insertTransaction(Connection connection, Transaction transaction) throws SQLException {
        String sql = "INSERT INTO TRANSACTION (ID_CLIENT, TYPE, VALUE) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, transaction.getIdClient());
            stmt.setString(2, transaction.getType());
            stmt.setDouble(3, transaction.getValue());

            stmt.executeUpdate();
        }
    }

    /**
     * Fetches all transactions for a specific customer from the database, effectively creating an account statement.
     * <p>
     * The results are ordered by {@code transaction_date} in **descending** order (most recent first).
     *
     * @param idClient The ID of the customer to fetch the statement from.
     * @return A {@code List<Transaction>} containing the customer's transaction history.
     * Returns an **empty list** if no transactions are found or a {@code SQLException} occurs.
     */
    public List<Transaction> getExtractByClientId(int idClient) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Transaction> extract = new ArrayList<>();

        String sql = "SELECT ID_TRANSACTION, ID_CLIENT, TYPE, VALUE, TRANSACTION_DATE " +
                     "FROM TRANSACTION " +
                     "WHERE ID_CLIENT = ? " +
                     "ORDER BY TRANSACTION_DATE DESC";

        try {
            connection = BankRepository.getConnection();
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idClient);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int idTransaction = rs.getInt("id_transaction");
                int clientId = rs.getInt("id_client");
                String type = rs.getString("type");
                double value = rs.getDouble("value");
                Timestamp transactionDate = rs.getTimestamp("transaction_date");

                Transaction t = new Transaction(idTransaction, clientId, type, value, transactionDate);
                extract.add(t);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar extrato do cliente: " + e.getMessage());
        } finally {
            BankRepository.closeConnection(connection);
        }

        return extract;
    }
}
