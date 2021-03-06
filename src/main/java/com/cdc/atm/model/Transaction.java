package main.java.com.cdc.atm.model;

import java.time.LocalDateTime;

public class Transaction {
    private int sourceAccount, destinationAccount;
    private double amount;
    private String type;
    private LocalDateTime date;

    public Transaction(){
        this.sourceAccount = 0;
        this.destinationAccount = 0;
        this.amount = 0;
        this.type = "";
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(int sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getTransactionDate() {
        return date;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.date = transactionDate;
    }

    public int getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(int destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "sourceAccount=" + sourceAccount +
                ", destinationAccount=" + destinationAccount +
                ", amount=" + amount +
                ", type='" + type + '\'' +
                ", date=" + date +
                '}';
    }
}
