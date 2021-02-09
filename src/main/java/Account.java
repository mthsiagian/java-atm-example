package main.java;

public class Account {
    private int accountNumber;
    private int pin;
    private double availableBalance;
    private String accountName;

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
        return inputPin == pin ? true : false;
    }

    public void credit(double amount) {
        availableBalance += amount;
    }

    public void debit(double amount) {
        availableBalance -= amount;
    }
}
