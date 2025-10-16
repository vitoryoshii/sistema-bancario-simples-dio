package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class responsible for managing the connection to the Oracle database.
 * Centralizes JDBC configuration and transaction control methods.
 * (commit, rollback e close).
 */
public class BankRepository {

    private static final String JDBCURL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USER = "SYSTEM";
    private static final String PASSWORD = "master";

    /**
     * Establishes and returns a new connection to the Oracle Database.
     *
     * @return Objeto Connection.
     * @throws SQLException If a database connection error occurs.
     */
    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(JDBCURL, USER, PASSWORD);
            connection.setAutoCommit(false);

            return connection;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar: " + e.getMessage(), e);
        }
    }

    /**
     * Commits all pending operations in the transaction.
     * @param connection The connection activates.
     */
    public static void commitTransaction(Connection connection) {
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao confirmar (commit) a transação: " + e.getMessage(), e);
        }
    }

    /**
     * Rollbacks all pending operations in the transaction.
     * @param connection The connection activates.
     */
    public static void rollbackTransaction(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao desfazer (rollback) a transação: " +  e.getMessage(), e);
        }
    }

    /**
     * Fecha a conexão com o banco de dados.
     * @param connection A conexão a ser fechada.
     */
    public static void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar a conexão: " + e.getMessage());
        }
    }
}
