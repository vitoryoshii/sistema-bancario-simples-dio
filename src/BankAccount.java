import java.io.PrintStream;

public class BankAccount {

    private Client holder;
    private double balance;
    private double specialCheckLimit;
    private double usedCheckSpecial;

    public BankAccount(Client holder, double initialDeposit){
        this.holder = holder;
        this.balance = initialDeposit;
        this.usedCheckSpecial = 0;

        if (initialDeposit <= 500){
            this.specialCheckLimit = 50;
        } else {
            this.specialCheckLimit = initialDeposit * 0.5;
        }
    }

    public void checkBalance(){
        System.out.printf("Saldo atul: R$ %.2f%n", balance);
    }

    public void checkSpecialCheck(){
        System.out.printf("Limite de Cheque Especial: R$ %.2f%n", specialCheckLimit);
        System.out.printf("Usado: R$ %.2f%n", usedCheckSpecial);
    }

    public void deposit(double value){
        if (usedCheckSpecial > 0){
            double valueWithFee = usedCheckSpecial * 1.2;

            if (value > valueWithFee) {
                value -= valueWithFee;
                usedCheckSpecial = 0;
                balance += value;
                System.out.printf("Cheque especial quitado. Novo Saldo: R$ %.2f%n", balance);
            }  else {
                double partPaid = value / 1.2;
                usedCheckSpecial -= partPaid;
                System.out.printf("Cheque especial parcialmente pago. Ainda deve R$ %.2f%n", usedCheckSpecial);
                return;
            }
        } else {
            balance += value;
            System.out.printf("Depósito realizado. Novo Saldo: R$ %.2f%n", balance);
        }
    }

    public void withdraw(double value){
        double totalAvailable = balance + (specialCheckLimit - usedCheckSpecial);

        if (value > totalAvailable){
            System.out.println("Saque não permitido! Saldo insuficiente.");
        }

        if (value < balance){
            balance -= value;
        } else {
            double remainder = value - balance;
            balance = 0;
            usedCheckSpecial +=  remainder;
        }

        System.out.printf("Saque de R$ %.2f realizado com sucesso.%n", value);
    }

    public void payBill(double value){
        System.out.println("Tentando pagar o Boleto...");
        withdraw(value);
    }

    public boolean usingCheckSpecial(){
         return usedCheckSpecial > 0;
    }
}
