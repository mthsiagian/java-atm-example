package main.java;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.Random;

public class FundTransfer {
    private Bank bank;
    private Screen screen;
    private Keypad keypad;
    private Account sourceAccount, destinationAccount;
    private boolean exitStatus;
    private double minimumTransfer, maximumTransfer, amount;
    private Date date;

    public FundTransfer(Bank bank, Screen screen, Keypad keypad, int accountNumber){
        this.bank = bank;
        this.screen = screen;
        this.keypad = keypad;
        this.sourceAccount = bank.getAccount(accountNumber);
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

        switch (inputMenu) {
            case "1":
                continueTransfer(destinationAccountNo, strAmount, referenceNumber);
                this.exitStatus = true;
                break;
            default:
                this.exitStatus = true;
                break;
        }
    }

    private void continueTransfer(String destinationAccountNo, String strAmount, int referenceNo){
        try {
            if(!destinationAccountNo.matches("[0-9]+")){
                throw new InputMismatchException("Invalid Account");
            }
            destinationAccount = bank.getAccount(Integer.parseInt(destinationAccountNo));
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
            date = new Date();
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
