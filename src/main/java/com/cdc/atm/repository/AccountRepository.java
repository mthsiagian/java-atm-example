package main.java.com.cdc.atm.repository;

import main.java.com.cdc.atm.model.Account;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AccountRepository {
    private List<Account> accounts;
    private final String defaultPath = FileSystems
            .getDefault()
            .getPath("data/account.csv")
            .toAbsolutePath()
            .toString();

    public AccountRepository(){
        String inputPath = System.getenv("ENV_ACC_PATH");
        String path = inputPath == null ? defaultPath : inputPath;

        this.initiateAccounts(path);
    }

    public Account getAccount(int accountNumber) {
        Optional<Account> account =  accounts
                .stream()
                .filter(acc -> acc.getAccountNumber() == accountNumber)
                .findFirst();
        return account.orElse(null);
    }

    private void initiateAccounts(String path){
        try(Stream<String> lines = Files.lines(Paths.get(path))) {
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

}
