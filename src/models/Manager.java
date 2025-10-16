package models;

import java.util.Scanner;

import util.InputUtils;

/**
 * Represents a Manager entity, extending the base properties of a {@code User}.
 * <p>
 * This class holds specific login credentials (username and password) and includes
 * utility methods for capturing user input to create new {@link Client} and
 * {@code Manager} records.
 */
public class Manager extends User {
    private static final Scanner scanner = new Scanner(System.in);

    // Variables to control the models.Manager internal methods
    private String user;
    private String password;

    /**
     * Constructs a Manager object with full personal and login credentials.
     * * @param name The full name of the manager.
     * @param cpf The manager's national identification number (CPF).
     * @param dateOfBirth The manager's date of birth in the format DD/MM/YYYY.
     * @param address The manager's residential address.
     * @param user The unique login username for the manager.
     * @param password The manager's password hash (or plaintext, depending on {@code ManagerDao} implementation).
     */
    public Manager(String name, String cpf, String dateOfBirth, String address, String user, String password) {
        super(name, cpf, dateOfBirth, address);
        this.user = user;
        this.password = password;
    }

    /**
     * Constructs an empty Manager object, typically used as a placeholder or
     * before capturing user input for a new registration.
     */
    public Manager() {
        super();
    }

    // --- Getters ---

    /**
     * Gets the manager's login username.
     *
     * @return The username as a {@code String}.
     */
    public String getUser() {
        return user;
    }

    /**
     * Gets the manager's stored password (should ideally be a hashed value).
     *
     * @return The password string.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Guides the user through collecting necessary information to create a new {@link Client} record.
     * <p>
     * This method acts as a factory, prompting for client name, CPF, birth date, and address
     * using utilities from {@link InputUtils}.
     *
     * @return A newly constructed {@code Client} object with captured information.
     */
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

    /**
     * Guides the user through collecting all necessary information to create a new {@code Manager} record.
     * <p>
     * This method is intended for initial system setup when no manager exists. It collects
     * personal and login data using static utilities.
     *
     * @return A newly constructed {@code Manager} object with captured credentials.
     */
    public static Manager createManager() {
        String managerCPF, managerName, managerDateOfBirth, managerAddress, managerUser, managerPassword;

        System.out.println("CADASTRO DE GERENTE");

        // Call the responsible functions to capture the information
        managerCPF = InputUtils.getValidCPF(scanner);
        managerName = InputUtils.getName(scanner);
        managerDateOfBirth = InputUtils.getDateOfBirth(scanner);
        managerAddress = InputUtils.getAddress(scanner);
        managerUser = InputUtils.getUser(scanner);
        managerPassword = InputUtils.getPassword(scanner);

        return new Manager(managerName, managerCPF, managerDateOfBirth, managerAddress, managerUser, managerPassword);
    }
}
