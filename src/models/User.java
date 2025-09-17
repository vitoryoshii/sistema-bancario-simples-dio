package models;

public abstract class User {

    protected String name;
    protected String cpf;
    protected String dateOfBirth;
    protected String address;

    // constructor method of the User object
    public User(String name, String cpf, String dateOfBirth, String address) {
        this.name = name;
        this.cpf = cpf;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    // constructor method empty
    public User() {}

    // Method to return in and set attributes
    public String getName() {return name;}

    public String getCpf() {return cpf;}

    public String getDateOfBirth() {return dateOfBirth;}

    public String getAddress() {return address;}

    public void setName(String name) {this.name = name;}

    public void setCpf(String cpf) {this.cpf = cpf;}

    public void setDateOfBirth(String dateOfBirth) {this.dateOfBirth = dateOfBirth;}

    public void setAddress(String address) {this.address = address;}
}
