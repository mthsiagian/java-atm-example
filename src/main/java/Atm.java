package main.java;

import main.java.com.cdc.atm.service.BankService;
import main.java.com.cdc.atm.service.FundTransferService;
import main.java.com.cdc.atm.service.WithdrawService;

public class Atm {
    private Screen screen;
    private BankService bankService;
    private Keypad keypad;
    private boolean userAuthenticated;
    private int userAccountNo;


    private final static int WITHDRAW = 1;
    private final static int FUND_TRANSFER = 2;
    private final static int TRANSACTION_HISTORY = 3;


    public Atm(){
        bankService = new BankService();
        screen = new Screen();
        keypad = new Keypad();
        userAuthenticated = false;
    }

    public void run() {
        while(!userAuthenticated) {
            welcome();
        }
        transaction();
    }

    private void welcome(){
        screen.displayMessageLine("[+] Welcome to ATM Simulator [+]");
        screen.displayMessage("Enter Account Number: ");
        int accNo = keypad.getCredentialInput("Account Number", 6);
        screen.displayMessage("Enter PIN: ");
        int pin = keypad.getCredentialInput("PIN", 6);

        userAuthenticated = bankService.authenticateAccount(accNo, pin);
        if(userAuthenticated) {
            userAccountNo = accNo;
        } else {
            screen.displayErrorMessageLine("Invalid Account Number/ PIN.");
        }
    }

    private void transaction(){
        int inputMenu = inputMainMenu();
        switch (inputMenu){
            case WITHDRAW:
                WithdrawService withdraw = new WithdrawService(bankService, screen, keypad, userAccountNo);
                withdraw.execute();
                bankService.saveTransaction(withdraw.getTransactionDetail());
                continueTransaction(withdraw.isExitStatus());
                break;
            case FUND_TRANSFER:
                FundTransferService transfer = new FundTransferService(bankService, screen, keypad, userAccountNo);
                transfer.execute();
                    bankService.saveTransaction(transfer.getTransactionDetail());
                continueTransaction(transfer.isExitStatus());
                break;
            case TRANSACTION_HISTORY:
                bankService.displayTransactionHistory(userAccountNo);
                continueTransaction(false);
                break;
            case 4:
                userAuthenticated = false;
                run();
                break;
            default:
                transaction();
                break;
        }
    }

    private int inputMainMenu() {
        screen.displayMainMenu();
        return keypad.getInput();
    }

    private void continueTransaction(boolean status){
        if(status) {
            transaction();
        } else {
            screen.displayAnotherTransaction();
            int inputMenu = keypad.getInput();

            if(inputMenu == 1) {
                transaction();
            } else {
                userAuthenticated = false;
                run();
            }
        }
    }
}
