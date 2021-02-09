package main.java;

public class Atm {
    private Screen screen;
    private Bank bank;
    private Keypad keypad;
    private boolean userAuthenticated;
    private int userAccountNo;


    private final static int WITHDRAW = 1;
    private final static int FUND_TRANSFER = 2;


    public Atm(){
        userAuthenticated = false;
        screen = new Screen();
        bank = new Bank();
        keypad = new Keypad();
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

        userAuthenticated = bank.authenticateAccount(accNo, pin);
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
                Withdraw withdraw = new Withdraw(bank, screen, keypad, userAccountNo);
                withdraw.execute();
                continueTransaction(withdraw.isExitStatus());
                break;
            case FUND_TRANSFER:
                FundTransfer transfer = new FundTransfer(bank, screen, keypad, userAccountNo);
                transfer.execute();
                continueTransaction(transfer.isExitStatus());
                break;
            case 3:
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

            switch (inputMenu) {
                case 1:
                    transaction();
                    break;
                default:
                    userAuthenticated = false;
                    run();
                    break;
            }
        }
    }
}
