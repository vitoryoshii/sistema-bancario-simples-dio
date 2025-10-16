package dao;

import models.Transaction;
import util.BankRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for the Transaction entity.
 * Responsible for inserting new records and searching transaction history.
 */
public class TransactionDao {
    /**
     * Inserts a new transaction record into the Oracle database.
     * * @param connection The JDBC connection (must come from BankRepository.getConnection() and have autoCommit=false).
     * @param transaction The Transaction object to save.
     * @throws SQLException If an error occurs while executing SQL.
     */
    public void insertTransaction(Connection connection, Transaction transaction) throws SQLException {
        String sql = "INSERT INTO transaction (id_client, type, value) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, transaction.getIdClient());
            stmt.setString(2, transaction.getType());
            stmt.setDouble(3, transaction.getValue());

            stmt.executeUpdate();
        }
    }

    /**
     * Fetches all transactions for a specific customer from the database.
     * * @param idClient The customer ID to fetch the statement from.
     * @return A list of Transaction objects.
     */
    public List<Transaction> getExtractByClientId(int idClient) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Transaction> extract = new ArrayList<>();

        String sql = "SELECT id_transaction, id_client, type, value, transaction_date " +
                     "FROM transaction " +
                     "WHERE id_client = ? " +
                     "ORDER BY transaction_date DESC";

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
