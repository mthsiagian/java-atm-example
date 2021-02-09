package main.java;

import java.util.InputMismatchException;
import java.util.Random;

public class FundTransfer {
    private Bank bank;
    private Screen screen;
    private Keypad keypad;
    private Account sourceAccount, destinationAccount;
    private boolean exitStatus;
    private double minimumTransfer, maximumTransfer;

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

    public boolean isExitStatus() {
        return exitStatus;
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

    private void transferMenu(String destinationAccountNo, String amount, int referenceNumber) {
        screen.displayConfirmFundTransfer(destinationAccountNo, amount, referenceNumber);
        String inputMenu = keypad.getNextLine();

        switch (inputMenu) {
            case "1":
                continueTransfer(destinationAccountNo, amount, referenceNumber);
                this.exitStatus = true;
                break;
            default:
                this.exitStatus = true;
                break;
        }
    }

    private void continueTransfer(String destinationAccountNo, String amount, int referenceNo){
        try {
            if(!destinationAccountNo.matches("[0-9]+")){
                throw new InputMismatchException("Invalid Account");
            }
            destinationAccount = bank.getAccount(Integer.parseInt(destinationAccountNo));
            if (destinationAccount == null) {
                throw new InputMismatchException("Invalid Account");
            }

            if(!amount.matches("[0-9]+")){
                throw new InputMismatchException("Invalid Amount");
            }

            double dblAmount = Double.parseDouble(amount);
            if(dblAmount < minimumTransfer) {
                throw new InputMismatchException("Minimum amount to withdraw is "+screen.formatDollar(minimumTransfer));
            }

            if(dblAmount > maximumTransfer) {
                throw new InputMismatchException("Maximum amount to withdraw is "+screen.formatDollar(maximumTransfer));
            }

            if(sourceAccount.getAvailableBalance() < dblAmount) {
                throw new InputMismatchException("Insufficient balance " +screen.formatDollar(dblAmount));
            }

            sourceAccount.debit(dblAmount);
            destinationAccount.credit(dblAmount);
            screen.displayFundTransferSummary(sourceAccount, destinationAccount, referenceNo, dblAmount);
        } catch (InputMismatchException e) {
            screen.displayMessageLine(e.getMessage());
        }
    }


}
