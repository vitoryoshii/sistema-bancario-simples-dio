import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainSystemBank {

    private final static Scanner scanner = new Scanner(System.in);
    public static List<Client> clients = new ArrayList<>();
    private static Manager manager = new Manager();

    public static void main(String[] args) {
        scanner.useDelimiter("\\n");

        int option = 0;
        do {
            System.out.println("=== BANCO DIGITAL ===");
            System.out.println("1 - CRIAR CLIENTE");
            System.out.println("2 - ATIVAR CONTA CLIENTE");
            System.out.println("3 - LISTAR CLIENTES");
            System.out.println("4 - LISTAR CONTAS CLIENTES");
            System.out.println("0 - Sair");
            System.out.println("===========================");
            option = scanner.nextInt();

            switch (option){
                case 1 -> createClient();
                case 2 -> activatedAccount();
                case 3 -> System.out.println(manager.listUsers());
                case 4 -> System.out.println(manager.listAccounts());
                case 0 -> System.exit(0);
                default -> System.out.println("Opção inválida");
            }

        }while (true);
    }

    public static void createClient(){
        Client newClient = manager.createClient();
        clients.add(newClient);
        System.out.println("Criando Cliente...");
        System.out.println("Cliente: " + newClient.getName() + " - CPF: " + newClient.getCpf());
    }

    public static void activatedAccount(){
        System.out.println("DIGITE O CPF DO CLIENTE: ");
        String cpf = scanner.next();

        Client client = manager.searchCustomerByCPF(cpf);

        if (client == null){
            System.out.println("CPF NÃO CADASTRADO. CADASTRE UM CLIENTE!");
        } else {
            System.out.println(manager.activatedAccount(client));
        }
    }
}
