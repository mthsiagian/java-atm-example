package main.java;

import java.util.Date;

public class Transaction {
    private int sourceAccount;
    private double amount;
    private String type;
    private Date date;

    public Transaction(){
        this.sourceAccount = 0;
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

    public Date getTransactionDate() {
        return date;
    }

    public void setTransactionDate(Date transactionDate) {
        this.date = transactionDate;
    }
}
