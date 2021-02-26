package main.java;

import java.util.Date;

public class Withdraw {
    private Bank bank;
    private Screen screen;
    private Keypad keypad;
    private Account account;
    private double amount;
    private Date date;
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
                amount = 10;
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
                amount = getOtherWithdrawalMenu();
                transaction(amount);
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

    public Transaction getTransactionDetail(){
        Transaction Th = new Transaction();
        Th.setType("WITHDRAWAL");
        Th.setTransactionDate(date);
        Th.setSourceAccount(account.getAccountNumber());
        Th.setAmount(amount);
        return Th;
    }

    private void transaction(double amount){
        double accountBalance = account.getAvailableBalance();
        date = new Date();
        if(accountBalance >= amount) {
            account.debit(amount);
            screen.displayWithdrawSummary(amount, account.getAvailableBalance(), date);
        } else {
            screen.displayMessageLine("Insufficient balance "+ accountBalance);
        }
    }
}
