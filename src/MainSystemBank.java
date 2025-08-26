import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainSystemBank {

    private final static Scanner scanner = new Scanner(System.in);
    static List<Client> clients = new ArrayList<>();
    private static Manager manager = new Manager();

    public static void main(String[] args) {
        scanner.useDelimiter("\\n");

        int option = 0;
        do {
            System.out.println("=== BANCO DIGITAL ===");
            System.out.println("1 - Criar Cliente");
            System.out.println("0 - Sair");
            System.out.println("===========================");
            option = scanner.nextInt();

            switch (option){
                case 1 -> createClient();
                case 0 -> System.exit(0);
                default -> System.out.println("Opção inválida");
            }

        }while (true);
    }

    public static void createClient(){
        Client newClient = manager.createClient();
        clients.add(newClient);
        System.out.println("Criando Cliente...");
        System.out.println("cliente: " + newClient.getName() + " - CPF: " + newClient.getCpf());
    }
}
