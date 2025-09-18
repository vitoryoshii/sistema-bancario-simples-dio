package util;

import models.Client;
import models.Manager;

import java.util.ArrayList;
import java.util.List;

public class BankRepository {
    private final List<Client> clients;
    private final List<Manager> managers;

    public BankRepository() {
        this.clients = new ArrayList<>();
        this.managers = new ArrayList<>();
    }

    // Methods get e set to Lists Clients and Manager
    public List<Client> getClients() {
        return clients;
    }

    public List<Manager> getManagers() {
        return managers;
    }

    public void addClient(Client client) {
        this.clients.add(client);
    }

    public void addManager(Manager manager) {this.managers.add(manager);}

    // Method search customer by CPF
    public Client searchCustomerByCPF(String cpf) {
        for (Client c : getClients()) {
            if (c.getCpf().equals(cpf)) {
                return c; // return client found
            }
        }
        return null; // return null
    }

    // Method search manager by CPF
    public Manager searchManagerByCPF(String cpf) {
        for (Manager m : getManagers()) {
            if (m.getCpf().equals(cpf)) {
                return m; // return manager found
            }
        }
        return null; // return null
    }
}
