package main.java;

public class Withdraw {
    private Bank bank;
    private Screen screen;
    private Keypad keypad;
    private Account account;
    private boolean exitStatus;


    public Withdraw(Bank bank, Screen screen, Keypad keypad, int accountNumber){
        this.bank = bank;
        this.screen = screen;
        this.keypad = keypad;
        this.account = bank.getAccount(accountNumber);
        this.exitStatus = false;
    }

    public void execute () {

        int inputMenu = getWithdrawalMenu();

        switch (inputMenu) {
            case 1:
                int amount = 10;
                transaction(amount);
                break;
            case 2:
                amount = 50;
                transaction(amount);
                break;
            case 3:
                amount = 100;
                transaction(amount);
                break;
            case 4:
                double customAmount = getOtherWithdrawalMenu();
                transaction(customAmount);
                break;
            case 5:
                exitStatus = true;
                break;
            default:
                execute();
        }
    }

    public boolean isExitStatus(){
        return exitStatus;
    }

    private int getWithdrawalMenu() {
        screen.displayWithdrawMenu();
        return keypad.getInput();
    }

    private double getOtherWithdrawalMenu() {
        screen.displayOtherWithdrawMenu();
        return keypad.getInputDouble();
    }

    private void transaction(double amount){
        double accountBalance = account.getAvailableBalance();
        if(accountBalance >= amount) {
            account.debit(amount);
            screen.displayWithdrawSummary(amount, account.getAvailableBalance());
        } else {
            screen.displayMessageLine("Insufficient balance "+ accountBalance);
        }
    }
}
