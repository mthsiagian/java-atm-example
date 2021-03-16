package main.java.com.cdc.atm.model;

public class Screen {

    public void displayMessage(String message) {
        System.out.print(message);
    }

    public void displayMessageLine(String message){
        System.out.println(message);
    }

    public void displayErrorMessageLine(String message) {
        System.out.println("[Failed] : " + message + "\n");
    }

    public String formatDollar(double amount) {
        return String.format("$%,.2f", amount);
    }

}
