package menus;

import main.MainSystemBank;
import models.Client;
import util.BankRepository;

import java.util.Scanner;

public class ClientMenu implements Menu{
    private final BankRepository bankRepository;
    private final Client client;
    private final Scanner scanner;

    public ClientMenu(BankRepository bankRepository, Client client, Scanner scanner) {
        this.bankRepository = bankRepository;
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
                case 1 -> makeLoot();
                case 2 -> makeDeposit();
                case 3 -> System.out.println(client.showExtract());
                case 0 -> System.out.println("VOLTANDO...\n");
                default -> System.out.println("[ERRO] - OPÇÃO INVÁLIDA!\n");
            }
        } while (option != 0);
    }

    public void makeLoot() {
        System.out.print("VALOR DO SAQUE: ");
        double value = scanner.nextDouble();

        if (!client.getAccount()){
            System.out.println("[ERRO] - CONTA NÃO ATIVADA!\n");
        } else {
            System.out.println(client.withdraw(value));
        }
    }

    public void makeDeposit() {
        System.out.print("VALOR DEPOSITO: ");
        double value = scanner.nextDouble();

        if  (!client.getAccount()){
            System.out.println("[ERRO] - CONTA NÃO ATIVADA!\n");
        } else {
            System.out.println(client.deposit(value));
        }
    }
}
