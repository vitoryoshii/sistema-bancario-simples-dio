package models;

import java.util.ArrayList;
import java.util.List;

public class Client extends User {

    // Variables to control the models.Client's internal methods
    private boolean account;
    private double balance;

    private List<String> extract = new ArrayList<>();

    // Instance for creating the client object
    public Client(String name, String cpf, String dateOfBirth, String address) {
        super(name, cpf, dateOfBirth, address);
        this.account = false;
        this.balance = 0;
    }

    // get methods, to externally access client information
    public boolean getAccount() {return account;}

    public double getBalance() {return balance;}

    public void setAccount(boolean account) {
        this.account = account;
    }

    // customer interaction methods
    // Withdraw method
    public String withdraw(double value) {
        if (value < 0) {return "[ERRO] - VALOR NEGATIVO!";}
        if (value >= balance) {return "[ERRO] - SALDO INSUFICIENTE!";}
        else {
            balance = balance - value;
            extract.add("SAQUE - R$ " + value);
            return "[SUCESSO] - SAQUE DE:  R$ " + value + " REALIZADO! - SALDO: R$ " + balance + "\n";
        }
    }

    public String deposit(double value) {
        if (value < 0) {return "[ERRO] - VALOR NEGATIVO!";}
        else {
            balance = balance + value;
            extract.add("DEPOSITO - R$ " + value);
            return "[SUCESSO] - DEPOSITO DE: R$ " + value + " REALIZADO! - SALDO: R$ " + balance + "\n";
        }
    }

    public String showExtract() {
        if (extract.isEmpty()) {
            return "SEM MOVIMENTAÇÃO.";
        }
        StringBuilder sb = new StringBuilder("=== EXTRATO ===\n");
        for (String line : extract) {
            sb.append(line).append("\n");
        }
        sb.append("SALDO ATUAL - R$ ").append(balance).append("\n");
        return sb.toString();
    }

}
