package main.java.com.cdc.atm.service;

import main.java.Keypad;
import main.java.Screen;
import main.java.com.cdc.atm.model.Account;
import main.java.com.cdc.atm.model.Transaction;

import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Random;

public class FundTransferService {
    private BankService bankService;
    private Screen screen;
    private Keypad keypad;
    private Account sourceAccount, destinationAccount;
    private boolean exitStatus;
    private double minimumTransfer, maximumTransfer, amount;
    private LocalDateTime date;

    public FundTransferService(BankService bankService, Screen screen, Keypad keypad, int accountNumber){
        this.bankService = bankService;
        this.screen = screen;
        this.keypad = keypad;
        this.sourceAccount = bankService.getAccount(accountNumber);
        this.exitStatus = false;
        this.minimumTransfer = 1;
        this.maximumTransfer = 1000;
    }

    public void  execute() {
        keypad.getNextLine();

        screen.displayFundTransferMenu();
        String destinationAccount = getTransactionValue();
        if(destinationAccount.equals("")) {
            this.exitStatus = true;
            return;
        }

        screen.displayInputAmountFundTransfer();
        String amount = getTransactionValue();
        if(amount.equals("")) {
            this.exitStatus = true;
            return;
        }

        int referenceNumber = referenceNumberSummary();
        keypad.getNextLine();

        transferMenu(destinationAccount, amount, referenceNumber);
    }

    private void transferMenu(String destinationAccountNo, String strAmount, int referenceNumber) {
        screen.displayConfirmFundTransfer(destinationAccountNo, strAmount, referenceNumber);
        String inputMenu = keypad.getNextLine();

        if(inputMenu.equals("1")) {
            continueTransfer(destinationAccountNo, strAmount, referenceNumber);
        }
        this.exitStatus = true;
    }

    private void continueTransfer(String destinationAccountNo, String strAmount, int referenceNo){
        try {
            if(!destinationAccountNo.matches("[0-9]+")){
                throw new InputMismatchException("Invalid Account");
            }
            destinationAccount = bankService.getAccount(Integer.parseInt(destinationAccountNo));
            if (destinationAccount == null) {
                throw new InputMismatchException("Invalid Account");
            }

            if(!strAmount.matches("[0-9]+")){
                throw new InputMismatchException("Invalid Amount");
            }

            this.amount = Double.parseDouble(strAmount);
            if(amount < minimumTransfer) {
                throw new InputMismatchException("Minimum amount to withdraw is "+screen.formatDollar(minimumTransfer));
            }

            if(amount > maximumTransfer) {
                throw new InputMismatchException("Maximum amount to withdraw is "+screen.formatDollar(maximumTransfer));
            }

            if(sourceAccount.getAvailableBalance() < amount) {
                throw new InputMismatchException("Insufficient balance " +screen.formatDollar(amount));
            }

            sourceAccount.debit(amount);
            destinationAccount.credit(amount);
            date = LocalDateTime.now();
            screen.displayFundTransferSummary(sourceAccount, destinationAccount, referenceNo, amount);
        } catch (InputMismatchException e) {
            screen.displayMessageLine(e.getMessage());
        }
    }

    private String getTransactionValue() {
        String value = keypad.getInputWithEnterListener();
        if(value == null){
            this.exitStatus = true;
        }
        return value;
    }

    private int referenceNumberSummary(){
        int refNo = new Random().nextInt(999999);
        screen.displayReferenceNumberFundTransfer(refNo);

        return refNo;
    }

    public Transaction getTransactionDetail(){
        Transaction Th = new Transaction();
        date = LocalDateTime.now();
        Th.setType("FUND_TRANSFER");
        Th.setSourceAccount(sourceAccount.getAccountNumber());
        Th.setTransactionDate(date);
        Th.setAmount(amount);
        return Th;
    }

    public boolean isExitStatus() {
        return exitStatus;
    }


}
