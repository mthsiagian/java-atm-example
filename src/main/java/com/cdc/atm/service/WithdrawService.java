package main.java.com.cdc.atm.service;

import main.java.Keypad;
import main.java.Screen;
import main.java.com.cdc.atm.model.Account;
import main.java.com.cdc.atm.model.Transaction;

import java.time.LocalDateTime;

public class WithdrawService {
    private BankService bankService;
    private Screen screen;
    private Keypad keypad;
    private Account account;
    private double amount;
    private LocalDateTime date;
    private boolean exitStatus;


    public WithdrawService(BankService bankService, Screen screen, Keypad keypad, int accountNumber){
        this.bankService = bankService;
        this.screen = screen;
        this.keypad = keypad;
        this.account = bankService.getAccount(accountNumber);
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
        date = LocalDateTime.now();
        Th.setType("WITHDRAWAL");
        Th.setTransactionDate(date);
        Th.setSourceAccount(account.getAccountNumber());
        Th.setAmount(amount);
        return Th;
    }

    private void transaction(double amount){
        double accountBalance = account.getAvailableBalance();
        date = LocalDateTime.now();
        if(accountBalance >= amount) {
            account.debit(amount);
            screen.displayWithdrawSummary(amount, account.getAvailableBalance(), date);
        } else {
            screen.displayMessageLine("Insufficient balance "+ accountBalance);
        }
    }
}
