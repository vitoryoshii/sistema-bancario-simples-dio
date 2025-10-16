package models;

/**
 * Represents a Client entity, extending the base properties of a {@code User}.
 * <p>
 * This class holds specific banking data for a customer, including their account status
 * (active/inactive) and current monetary balance.
 */
public class Client extends User {

    // Variables to control the models.Client's internal methods
    private boolean account;
    private double balance;

    /**
     * Constructs a new Client object.
     * <p>
     * Initializes the client with basic personal information inherited from {@code User}.
     * By default, the account is set to **inactive** ({@code false}) and the balance
     * is set to **zero** ({@code 0.0}).
     *
     * @param name The full name of the client.
     * @param cpf The client's national identification number (CPF).
     * @param dateOfBirth The client's date of birth in the format DD/MM/YYYY.
     * @param address The client's residential address.
     */
    public Client(String name, String cpf, String dateOfBirth, String address) {
        super(name, cpf, dateOfBirth, address);
        this.account = false;
        this.balance = 0.0;
    }

    // --- Getters and Setters ---

    /**
     * Gets the current activation status of the client's bank account.
     *
     * @return {@code true} if the account is active, {@code false} if it is inactive.
     */
    public boolean getAccount() {return account;}

    /**
     * Gets the current monetary balance of the client's account.
     *
     * @return The current balance as a {@code double}.
     */
    public double getBalance() {return balance;}

    /**
     * Sets the activation status of the client's bank account.
     * <p>
     * This status is typically controlled by the manager.
     *
     * @param account The new account status ({@code true} for active, {@code false} for inactive).
     */
    public void setAccount(boolean account) {
        this.account = account;
    }

    /**
     * Sets the current monetary balance of the client's account.
     * <p>
     * This method is usually called after a successful transaction (deposit or withdrawal)
     * has been persisted to the database.
     *
     * @param balance The new balance value.
     */
    public void setBalance(double balance) {this.balance = balance;}

}
