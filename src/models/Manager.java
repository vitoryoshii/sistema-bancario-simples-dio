package models;

import java.util.List;
import java.util.Scanner;

import util.InputUtils;
import util.ValidationUtils;

public class Manager extends User {
    private static final Scanner scanner = new Scanner(System.in);

    // Variables to control the models.Manager internal methods
    private String user;
    private String password;

    // constructor method of the manager object
    public Manager(String name, String cpf, String dateOfBirth, String address, String user, String password) {
        super(name, cpf, dateOfBirth, address);
        this.user = user;
        this.password = password;
    }

    // constructor method empty
    public Manager() {
        super();
    }

    // Method to return in attributes
    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    // Method to create new customers
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

    // Method that creates when there is no manager on the list
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
