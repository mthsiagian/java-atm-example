package main.java.com.cdc.atm.service;

import main.java.com.cdc.atm.model.Keypad;
import main.java.com.cdc.atm.model.Screen;
import main.java.com.cdc.atm.model.Account;
import main.java.com.cdc.atm.model.Transaction;
import main.java.com.cdc.atm.util.Validation;

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
    private Validation validation = new Validation();

    public FundTransferService(BankService bankService, Screen screen, Keypad keypad, int accountNumber){
        this.bankService = bankService;
        this.screen = screen;
        this.keypad = keypad;
        this.sourceAccount = bankService.getAccount(accountNumber);
        this.exitStatus = false;
        this.minimumTransfer = 1;
        this.maximumTransfer = 1000;
    }

    public void execute() {
        keypad.getNextLine();

        String destinationAccount = recordDestinationAccount();
        if("".equals(destinationAccount)) {
            this.exitStatus = true;
            return;
        }

        String amount = recordTransactionAmount();
        if("".equals(amount)) {
            this.exitStatus = true;
            return;
        }

        int referenceNumber = referenceNumberSummary();
        keypad.getNextLine();

        transferMenu(destinationAccount, amount, referenceNumber);
    }

    public Transaction getTransactionDetail(){
        Transaction Th = new Transaction();
        date = LocalDateTime.now();
        Th.setType("FUND_TRANSFER");
        Th.setSourceAccount(sourceAccount.getAccountNumber());
        Th.setDestinationAccount(destinationAccount.getAccountNumber());
        Th.setTransactionDate(date);
        Th.setAmount(amount);
        return Th;
    }

    public boolean isExitStatus() {
        return exitStatus;
    }

    private void transferMenu(String destinationAccountNo, String strAmount, int referenceNumber) {
        displayConfirmFundTransfer(destinationAccountNo, strAmount, referenceNumber);
        String inputMenu = keypad.getNextLine();

        if("1".equals(inputMenu)) {
            continueTransfer(destinationAccountNo, strAmount, referenceNumber);
        }
        this.exitStatus = true;
    }

    private void continueTransfer(String destinationAccountNo, String strAmount, int referenceNo){
        try {
            validate(destinationAccountNo, strAmount);

            sourceAccount.debit(amount);
            destinationAccount.credit(amount);
            bankService.saveTransaction(this.getTransactionDetail());
            displayFundTransferSummary(sourceAccount, destinationAccount, referenceNo, amount);
        } catch (InputMismatchException e) {
            screen.displayMessageLine(e.getMessage());
        }
    }

    private void validate(String destinationAccountNo, String strAmount) {
        validation.isValidIntegerString(destinationAccountNo, "Invalid Account");

        destinationAccount = bankService.getAccount(Integer.parseInt(destinationAccountNo));
        validation.isValidAccount(destinationAccount, "Invalid Account");

        validation.isValidIntegerString(strAmount, "Invalid Amount");

        this.amount = Double.parseDouble(strAmount);
        validation.aGreaterThanB(amount, minimumTransfer,"Minimum amount to withdraw is "+screen.formatDollar(minimumTransfer));
        validation.aLessThanB(amount, maximumTransfer,"Maximum amount to withdraw is "+screen.formatDollar(maximumTransfer));
        validation.aGreaterThanB(sourceAccount.getAvailableBalance(), amount, "Insufficient balance " +screen.formatDollar(amount));
    }

    private String recordDestinationAccount(){
        screen.displayMessageLine("\nPlease enter destination account and press enter to continue or");
        screen.displayMessage("press enter to go back to Transaction :");
        return  getTransactionValue();
    }

    private String recordTransactionAmount(){
        screen.displayMessageLine("\nPlease enter transfer amount and");
        screen.displayMessageLine("press enter to continue or");
        screen.displayMessage("press enter to go back to Transaction: ");
        return getTransactionValue();
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
        screen.displayMessageLine("\nReference Number :"+ refNo);
        screen.displayMessage("Please enter to continue");

        return refNo;
    }

    private void displayConfirmFundTransfer(String destinationAccount, String amount, int refNo){
        screen.displayMessageLine("\n[+] Transfer Confirmation [+]");
        screen.displayMessageLine("Destination Account :" + destinationAccount );
        screen.displayMessageLine("Transfer Amount :" +  amount);
        screen.displayMessageLine("Reference Number : " +refNo);

        screen.displayMessageLine("\n[1] - Confirm Trx");
        screen.displayMessageLine("[2] - Cancel Trx");
        screen.displayMessage("[?] Input Option [2] :");
    }

    private void displayFundTransferSummary(Account sourceAccount, Account destinationAccount, int refNo, double amount){
        screen.displayMessageLine("\n[+] Fund Transfer Summary [+]");
        screen.displayMessageLine("Destination Account :" + destinationAccount.getAccountNumber());
        screen.displayMessageLine("Transfer Amount :" +  amount);
        screen.displayMessageLine("Reference Number : " +refNo);
        screen.displayMessageLine("Balance : " + sourceAccount.getAvailableBalance());
    }

}
