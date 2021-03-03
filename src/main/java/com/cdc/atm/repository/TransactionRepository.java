package main.java.com.cdc.atm.repository;

import main.java.com.cdc.atm.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {
    private List<Transaction> transactions;


    public TransactionRepository(){
        transactions = new ArrayList<>();
    }

    public void getTransactions(int accountNumber){
        transactions
                .stream()
                .filter(t -> accountNumber == t.getSourceAccount())
                .sorted((t1, t2) -> t2.getTransactionDate().compareTo(t1.getTransactionDate()))
                .limit(10)
                .forEach(p -> System.out.println(p.getSourceAccount()+ " " + p.getType()+ " " + p.getAmount()+ " " + p.getTransactionDate()));
    }

    public void save(Transaction transaction){
        this.transactions.add(transaction);
    }
}
