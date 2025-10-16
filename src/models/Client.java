package models;

public class Client extends User {

    // Variables to control the models.Client's internal methods
    private boolean account;
    private double balance;

    // Instance for creating the client object
    public Client(String name, String cpf, String dateOfBirth, String address) {
        super(name, cpf, dateOfBirth, address);
        this.account = false;
        this.balance = 0.00;
    }

    // get methods, to externally access client information
    public boolean getAccount() {return account;}

    public double getBalance() {return balance;}

    public void setAccount(boolean account) {
        this.account = account;
    }

    public void setBalance(double balance) {this.balance = balance;}

}
