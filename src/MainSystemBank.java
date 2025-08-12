import java.util.Scanner;

public class MainSystemBank {

    private final static Scanner scanner = new Scanner(System.in);
    private static BankAccount account;

    public static void main(String[] args) {
        scanner.useDelimiter("\\n");

        //Create a new client
        System.out.print("Digite seu nome: ");
        String name = scanner.next();

        System.out.print("Digite seu CPF (Somente números): ");
        String cpf = scanner.next();

        Client client = new Client(name, cpf);

        System.out.print("Qual o depósito inicial: ");
        double initialDeposit = scanner.nextDouble();

        account = new BankAccount(client, initialDeposit);

        System.out.println("Conta Bancaria: " + client.getName() + " | " + client.getCpf());

        int option = 0;
        do {
            System.out.println("===ESCOLHA UMA OPERAÇÃO===");
            System.out.println("1 - CONSULTAR SALDO");
            System.out.println("2 - CONSULTAR CHEQUE ESPECIAL");
            System.out.println("3 - DEPOSITAR DINHEIRO");
            System.out.println("4 - SACAR DINHEIRO");
            System.out.println("5 - PAGAR BOLETO");
            System.out.println("6 - VERIFICAR SE USO DO CHEQUE ESPECIAL");
            System.out.println("0 - Sair");
            System.out.println("===========================");
            option = scanner.nextInt();

            switch (option){
                case 1 -> account.checkBalance();
                case 2 -> account.checkSpecialCheck();
                case 3 -> deposit();
                case 4 -> withdraw();
                case 5 -> payBill();
                case 6 -> accountUsingCheckSpecial();
                case 0 -> System.exit(0);
                default -> System.out.println("Opção inválida");
            }

        }while (true);
    }

    public static void deposit(){
        System.out.print("Digite o valor a depositar: ");
        double depositAmount = scanner.nextDouble();

        while (depositAmount <= 0){
            System.out.println("O valor deve ser positivo!");
            System.out.print("Digite novamente: ");
            depositAmount = scanner.nextDouble();
        }
        account.deposit(depositAmount);
    }

    public static void withdraw(){
        System.out.print("Digite o valor a sacar: ");
        double withdrawAmount = scanner.nextDouble();

        while (withdrawAmount <= 0) {
            System.out.println("O valor deve ser positivo");
            System.out.print("Digite novamente: ");
            withdrawAmount = scanner.nextDouble();
        }
        account.withdraw(withdrawAmount);
    }

    public static void payBill(){
        System.out.print("Digite o valor do boleto: ");
        double billAmount = scanner.nextDouble();

        while (billAmount <= 0){
            System.out.println("O valor deve ser positivo");
            System.out.print("Digite novamente: ");
            billAmount = scanner.nextDouble();
        }
        account.payBill(billAmount);
    }

    public static void accountUsingCheckSpecial(){
        if (account.usingCheckSpecial() == true){
            System.out.println("A conta está usando o cheque especial");
        } else {
            System.out.println("A conta não está usando o cheque especial");
        }
    }
}
