public class Client extends User {

    // Variables to control the Client's internal methods
    private String name;
    private final String cpf;
    private String dateOfBirth;
    private String address;
    private boolean account;

    // Instance for creating the client object
    public Client(String name, String cpf, String dateOfBirth, String address) {
        this.name = name;
        this.cpf = cpf;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.account = false;
    }

    // get methods, to externally access client information
    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public boolean isAccount() {
        return account;
    }

    public void setAccount(boolean account) {
        this.account = account;
    }

    // customer interaction methods

}
