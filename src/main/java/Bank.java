package main.java;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Bank {
    private List<Account> accounts;

    public Bank(){
        String dataPath = "data/account.csv";
        try (Stream<String> lines = Files.lines(Paths.get(dataPath))) {
            accounts = lines
                    .map(line -> {
                        List<String> arr = Arrays.asList(line.split(","));
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
        return existAcc != null ? existAcc.validatePin(pin) : false;
    }
}
