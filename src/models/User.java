package models;

/**
 * An abstract base class defining the common personal attributes for all users
 * (Clients and Managers) within the banking system.
 * <p>
 * This class holds fundamental registration data, such as identification, name,
 * date of birth, and address. It cannot be instantiated directly.
 */
public abstract class User {
    protected int id;
    protected String name;
    protected String cpf;
    protected String dateOfBirth;
    protected String address;

    /**
     * Constructs a {@code User} object with all necessary personal information.
     * <p>
     * The ID is not set here; it is typically assigned by the database upon insertion
     * and set later using {@link #setId(int)}.
     * * @param name The full name of the user.
     * @param cpf The user's national identification number (CPF).
     * @param dateOfBirth The user's date of birth, typically in the format DD/MM/YYYY.
     * @param address The user's residential address.
     */
    public User(String name, String cpf, String dateOfBirth, String address) {
        this.name = name;
        this.cpf = cpf;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    /**
     * Constructs an empty {@code User} object.
     * <p>
     * Used primarily when creating an object that will be populated later, such as
     * when retrieving data from the database.
     */
    public User() {}

    // --- Getters ---

    /**
     * Gets the unique database ID of the user.
     * @return The user's ID as an {@code int}.
     */
    public int getId() {return id;}

    /**
     * Gets the full name of the user.
     * @return The user's name as a {@code String}.
     */
    public String getName() {return name;}

    /**
     * Gets the user's CPF (national identification number).
     * @return The user's CPF as a {@code String}.
     */
    public String getCpf() {return cpf;}

    /**
     * Gets the user's date of birth.
     * @return The date of birth as a formatted {@code String}.
     */
    public String getDateOfBirth() {return dateOfBirth;}

    /**
     * Gets the user's residential address.
     * @return The user's address as a {@code String}.
     */
    public String getAddress() {return address;}

    // --- Setters ---

    /**
     * Sets the unique database ID of the user.
     * @param id The ID assigned by the persistence layer.
     */
    public void setId(int id) {this.id = id;}

    /**
     * Sets the full name of the user.
     * @param name The new name.
     */
    public void setName(String name) {this.name = name;}

    /**
     * Sets the user's CPF (national identification number).
     * @param cpf The new CPF.
     */
    public void setCpf(String cpf) {this.cpf = cpf;}

    /**
     * Sets the user's date of birth.
     * @param dateOfBirth The new date of birth string.
     */
    public void setDateOfBirth(String dateOfBirth) {this.dateOfBirth = dateOfBirth;}

    /**
     * Sets the user's residential address.
     * @param address The new address string.
     */
    public void setAddress(String address) {this.address = address;}
}
