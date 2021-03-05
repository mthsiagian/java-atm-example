package main.java.com.cdc.atm.model;

import main.java.com.cdc.atm.model.Account;

import java.time.LocalDateTime;

public class Screen {

    public void displayMessage(String message) {
        System.out.print(message);
    }

    public void displayMessageLine(String message){
        System.out.println(message);
    }

    public void displayErrorMessageLine(String message) {
        System.out.println("[Failed] : " + message + "\n");
    }

    public String formatDollar(double amount) {
        return String.format("$%,.2f", amount);
    }

    public void displayWithdrawMenu(){
        displayMessageLine("\n[+] Withdraw Menu [+]");
        displayMessageLine("[1] - $10");
        displayMessageLine("[2] - $50");
        displayMessageLine("[3] - $100");
        displayMessageLine("[4] - Other");
        displayMessageLine("[5] - Back");
        displayMessage("[?] Input menu : ");
    }

    public void displayOtherWithdrawMenu(){
        displayMessageLine("\n[+] Other withdraw  [+]");
        displayMessage("[?] - Enter amount to withdraw : ");
    }

    public void displayWithdrawSummary(double withdrawAmount, double availableBalance, LocalDateTime transactionDate){
        displayMessageLine("\nDate        :" + transactionDate.toString());
        displayMessageLine("Withdraw    :" + formatDollar(withdrawAmount));
        displayMessageLine("Balance     :" + formatDollar(availableBalance));
    }

    public void displayFundTransferMenu(){
        displayMessageLine("\nPlease enter destination account and press enter to continue or");
        displayMessage("press enter to go back to Transaction :");
    }

    public void displayInputAmountFundTransfer(){
        displayMessageLine("\nPlease enter transfer amount and");
        displayMessageLine("press enter to continue or");
        displayMessage("press enter to go back to Transaction: ");
    }

    public void displayReferenceNumberFundTransfer(int refNo){
        displayMessageLine("\nReference Number :"+ refNo);
        displayMessage("Please enter to continue");
    }

    public void displayConfirmFundTransfer(String destinationAccount, String amount, int refNo){
        displayMessageLine("\n[+] Transfer Confirmation");
        displayMessageLine("Destination Account :" + destinationAccount );
        displayMessageLine("Transfer Amount :" +  amount);
        displayMessageLine("Reference Number : " +refNo);

        displayMessageLine("\n[1] - Confirm Trx");
        displayMessageLine("[2] - Cancel Trx");
        displayMessage("[?] Input Option [2] :");
    }

    public void displayFundTransferSummary(Account sourceAccount, Account destinationAccount, int refNo, double amount){
        displayMessageLine("\n[+] Fund Transfer Summary");
        displayMessageLine("Destination Account :" + destinationAccount.getAccountNumber());
        displayMessageLine("Transfer Amount :" +  amount);
        displayMessageLine("Reference Number : " +refNo);
        displayMessageLine("Balance : " + sourceAccount.getAvailableBalance());

    }
}
