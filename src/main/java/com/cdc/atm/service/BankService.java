package main.java.com.cdc.atm.service;

import main.java.com.cdc.atm.model.Account;
import main.java.com.cdc.atm.model.Transaction;
import main.java.com.cdc.atm.repository.AccountRepository;
import main.java.com.cdc.atm.repository.TransactionRepository;

import java.util.Optional;

public class BankService {
    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    public BankService(){
        accountRepository = new AccountRepository();
        transactionRepository = new TransactionRepository();
    }

    public Account getAccount(int accountNumber){
        return accountRepository.getAccount(accountNumber);
    }

    public boolean authenticateAccount(int accountNumber, int pin) {
        Account existAcc = accountRepository.getAccount(accountNumber);
        return existAcc != null && existAcc.validatePin(pin);
    }

    public void displayTransactionHistory(int accountNumber){
        this.transactionRepository.getTransactions(accountNumber);
    }

    public void saveTransaction(Transaction transaction){
        this.transactionRepository.save(transaction);
    }
}
