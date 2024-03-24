package ATMInterface;

public class Main {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000); // Starting balance
        ATM atm = new ATM(account);
        atm.run();
    }
}

