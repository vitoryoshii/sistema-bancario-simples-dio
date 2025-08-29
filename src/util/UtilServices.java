package util;

import main.MainSystemBank;
import models.Client;
import models.Manager;

public class UtilServices {
    private MainSystemBank mainSystemBank =  new MainSystemBank();

    // Method search customer by CPF
    public Client searchCustomerByCPF(String cpf) {
        for (Client c : mainSystemBank.getClients()) {
            if (c.getCpf().equals(cpf)) {
                return c; // return client found
            }
        }
        return null; // return null
    }

    // Method search manager by CPF
    public Manager searchManagerByCPF(String cpf) {
        for (Manager m : mainSystemBank.getManagers()) {
            if (m.getCpf().equals(cpf)) {
                return m; // return manager found
            }
        }
        return null; // return null
    }
}
