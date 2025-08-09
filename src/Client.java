public class Client {

    private final String name;
    private final String cpf;

    private boolean isValidCPFFormat(String cpf) {
        return cpf != null && cpf.matches("\\d{11}");
    }

    public Client(String name, String cpf){
        if(!isValidCPFFormat(cpf)){
            System.out.println("CPF inválido!");
        }
        this.name = name;
        this.cpf = cpf;
    }

    public String getName(){
        return name;
    }

    public String getCpf(){
        return cpf;
    }


}
