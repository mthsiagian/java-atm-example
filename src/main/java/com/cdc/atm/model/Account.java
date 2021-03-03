package main.java.com.cdc.atm.model;

public class Account {
    private final int accountNumber;
    private final int pin;
    private double availableBalance;
    private final String accountName;

    public Account(String userAccountName, int userPin, int userBalance, int userAccountNumber){
        accountNumber = userAccountNumber;
        pin = userPin;
        availableBalance = userBalance;
        accountName = userAccountName;
    }

    public String getAccountName() {
        return accountName;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public boolean validatePin(int inputPin) {
        return inputPin == pin;
    }

    public void credit(double amount) {
        availableBalance += amount;
    }

    public void debit(double amount) {
        availableBalance -= amount;
    }
}
