package main.java.com.cdc.atm.repository;

import main.java.com.cdc.atm.model.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionRepository {
    private List<Transaction> transactions;


    public TransactionRepository(){
        transactions = new ArrayList<>();
    }

    public List<Transaction> getTransactions(int accountNumber){
        return transactions
                .stream()
                .filter(t ->
                        accountNumber == t.getSourceAccount() ||
                        accountNumber == t.getDestinationAccount()
                )
                .sorted((t1, t2) -> t2.getTransactionDate().compareTo(t1.getTransactionDate()))
                .limit(10)
                .collect(Collectors.toList());
    }

    public void save(Transaction transaction){
        this.transactions.add(transaction);
    }
}
