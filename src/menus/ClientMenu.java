package menus;

import dao.ClientDao;
import dao.TransactionDao;
import models.Client;
import models.Transaction;
import util.BankRepository;
import util.InputUtils;

import java.util.List;
import java.util.Scanner;

public class ClientMenu implements Menu{
    private Scanner scanner;
    private Client client;
    private ClientDao clientDAO =  new ClientDao();
    private TransactionDao transactionDAO =   new TransactionDao();

    public ClientMenu(Client client, Scanner scanner) {
        this.client = client;
        this.scanner = scanner;
    }

    @Override
    public void exibir() {
        int option;
        do {
            System.out.println("=== ACESSO CLIENTE - " + client.getName() + " ===");
            System.out.println("1 - SAQUE");
            System.out.println("2 - DEPOSITO");
            System.out.println("3 - EXTRATO");
            System.out.println("0 - VOLTAR");
            System.out.println("====================================");
            System.out.print("OPÇÃO: ");

            option = scanner.nextInt();

            switch (option) {
                case 1 -> doWithdraw();
                case 2 -> doDeposit();
                case 3 -> showExtract();
                case 0 -> System.out.println("VOLTANDO...\n");
                default -> System.out.println("[ERRO] - OPÇÃO INVÁLIDA!\n");
            }
        } while (option != 0);
    }

    private void doDeposit() {
        System.out.print("DIGITE O VALOR PARA DEPÓSITO: R$ ");
        double value =  scanner.nextDouble();

        if (clientDAO.deposit(client, value)) {
            client.setBalance(client.getBalance() + value);
            System.out.println("[SUCESSO] - DEPÓSITO DE R$ " + value + " REALIZADO! Novo Saldo: R$ " + client.getBalance());
        } else {
            // O rollback ocorreu dentro do ClientDAO
            System.out.println("[ERRO] - DEPÓSITO FALHOU. Consulte o log para detalhes.");
        }
    }

    private void doWithdraw() {
        System.out.print("DIGITE O VALOR PARA SAQUE: R$ ");
        double value =  scanner.nextDouble();

        if (!(value < 0) && (client.getBalance() < value)) {
            System.out.println("[ERRO] - SALDO INSUFICIENTE! Saldo: R$ " + client.getBalance());
            return;
        }

        if (clientDAO.withdraw(client, value)) {
            client.setBalance(client.getBalance() - value);
            System.out.println("[SUCESSO] - SAQUE DE R$ " + value + " REALIZADO! Novo Saldo: R$ " + client.getBalance());
        } else {
            System.out.println("[ERRO] - SAQUE FALHOU. Consulte o log para detalhes.");
        }
    }

    private void showExtract() {
        System.out.println("\n=== EXTRATO DE TRANSAÇÕES ===");
        List<Transaction> extract = transactionDAO.getExtractByClientId(client.getId());

        if (extract.isEmpty()) {
            System.out.println("[INFO] - SEM MOVIMENTAÇÃO NO PERÍODO.");
        } else {
            System.out.println("DATA/HORA           | TIPO      | VALOR");
            System.out.println("--------------------|-----------|--------------");
            for (Transaction t : extract) {
                String sign = t.getType().equals("SAQUE") ? "-" : "+";
                System.out.printf("%s | %-6s | %s R$ %.2f\n",
                        t.getTransactionDate().toString().substring(0, 19),
                        t.getType(), sign, t.getValue());
            }
        }
        System.out.println("\n----------------------------------------------");
        System.out.println("SALDO: " + client.getBalance());
        System.out.println("----------------------------------------------\n");
    }
}
