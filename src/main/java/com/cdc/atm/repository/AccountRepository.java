package main.java.com.cdc.atm.repository;

import main.java.com.cdc.atm.model.Account;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AccountRepository {
    private List<Account> accounts;

    public AccountRepository(){
        String dataPath = "data/account.csv";
        try(Stream<String> lines = Files.lines(Paths.get(dataPath))) {
            List<String> acc = new ArrayList<>();
            accounts = lines
                    .map(line -> {
                        List<String> arr = Arrays.asList(line.split(","));
                        try{
                            if(acc.contains(arr.get(3))) {
                                throw new CloneNotSupportedException("[ Error ]: Duplicated data.");
                            }
                        } catch (CloneNotSupportedException e) {
                            System.out.println(e.getMessage());
                            System.exit(0);
                        }
                        acc.add(arr.get(3));

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


}
