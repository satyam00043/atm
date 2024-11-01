public class Account {
    private double balance;

    public Account(double balance) {
        this.balance = balance;
    }

    public synchronized boolean withdraw(double amount) {
        if (amount <= balance) {
            System.out.println(Thread.currentThread().getName() + " is withdrawing " + amount);
            balance -= amount;
            System.out.println("Withdrawal successful! Remaining balance: " + balance);
            return true;
        } else {
            System.out.println(Thread.currentThread().getName() + " tried to withdraw " + amount + ", but insufficient balance!");
            return false;
        }
    }

    public double getBalance() {
        return balance;
    }
}
public class ATM {
    private Account account;

    public ATM(Account account) {
        this.account = account;
    }

    public void checkBalance() {
        System.out.println(Thread.currentThread().getName() + " checking balance: " + account.getBalance());
    }

    public void withdraw(double amount) {
        account.withdraw(amount);
    }
}
public class ATMUser extends Thread {
    private ATM atm;
    private double amountToWithdraw;

    public ATMUser(ATM atm, double amountToWithdraw, String userName) {
        super(userName);
        this.atm = atm;
        this.amountToWithdraw = amountToWithdraw;
    }

    @Override
    public void run() {
        synchronized (atm) {
            atm.checkBalance();
            atm.withdraw(amountToWithdraw);
        }
    }
}
public class ATMUser extends Thread {
    private ATM atm;
    private double amountToWithdraw;

    public ATMUser(ATM atm, double amountToWithdraw, String userName) {
        super(userName);
        this.atm = atm;
        this.amountToWithdraw = amountToWithdraw;
    }

    @Override
    public void run() {
        synchronized (atm) {
            atm.checkBalance();
            atm.withdraw(amountToWithdraw);
        }
    }
}
public class Main {
    public static void main(String[] args) {
        Account sharedAccount = new Account(500.0); // Shared account with initial balance
        ATM sharedATM = new ATM(sharedAccount);

        // Creating users
        ATMUser user1 = new ATMUser(sharedATM, 100.0, "User1");
        ATMUser user2 = new ATMUser(sharedATM, 150.0, "User2");
        ATMUser user3 = new ATMUser(sharedATM, 200.0, "User3");

        // Starting threads
        user1.start();
        user2.start();
        user3.start();
    }
}
