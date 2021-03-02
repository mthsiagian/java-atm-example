package main.java;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Bank {
    private List<Account> accounts;
    private List<Transaction> transactions;

    public Bank(){
        String dataPath = "data/account.csv";
        try (Stream<String> lines = Files.lines(Paths.get(dataPath))) {
            List<String> accs = new ArrayList<>();
            accounts = lines
                    .map(line -> {
                        List<String> arr = Arrays.asList(line.split(","));
                        try{
                            if(accs.contains(arr.get(3))) {
                                throw new CloneNotSupportedException("[ Error ]: Duplicated data.");
                            }
                        } catch (CloneNotSupportedException e) {
                            System.out.println(e.getMessage());
                            System.exit(0);
                        }
                        accs.add(arr.get(3));

                        return new Account(
                                arr.get(0),
                                Integer.parseInt(arr.get(1)),
                                Integer.parseInt(arr.get(2)),
                                Integer.parseInt(arr.get(3))
                        );
                    })
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }

        transactions = new ArrayList<>();
    }

    public Account getAccount(int accountNumber) {
        for (Account account: accounts) {
            if(account.getAccountNumber() == accountNumber){
                return account;
            }
        }
        return null;
    }

    public boolean authenticateAccount(int accountNumber, int pin) {
        Account existAcc = getAccount(accountNumber);
        return existAcc != null && existAcc.validatePin(pin);
    }

    public void displayTransactionHistory(int userAccountNumber){
        transactions
                .stream()
                .filter(t -> getAccount(userAccountNumber).getAccountNumber() == t.getSourceAccount())
                .sorted((t1, t2) -> t2.getTransactionDate().compareTo(t1.getTransactionDate()))
                .limit(10)
                .forEach(p -> System.out.println(p.getSourceAccount()+ " " + p.getType()+ " " + p.getAmount()+ " " + p.getTransactionDate())
        );
    }

    public void saveTransaction(Transaction transaction){
        this.transactions.add(transaction);
    }
}
