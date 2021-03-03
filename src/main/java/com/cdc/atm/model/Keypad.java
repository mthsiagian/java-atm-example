package main.java.com.cdc.atm.model;


import java.util.InputMismatchException;
import java.util.Scanner;

public class Keypad {
    private Scanner input;

    public Keypad(){
        input = new Scanner(System.in);
    }

    public int getCredentialInput(String type, int digit) {
        try {
            String userInput = input.next();
            if (!userInput.matches("[0-9]+")) {
                throw new InputMismatchException(type + " should only contain numbers.");
            }

            if(userInput.length() != digit) {
                throw new InputMismatchException(type + " should have " + digit + " digits.");
            }

            return Integer.parseInt(userInput);
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
            System.out.print("Enter "+ type + ":");
            return getCredentialInput(type, digit);
        }
    }

    public String getInputWithEnterListener(){
        return input.nextLine();
    }

    public int getInput(){
        return input.nextInt();
    }

    public double getInputDouble(){
        return input.nextDouble();
    }

    public String getNextLine(){
        return input.nextLine();
    }
}
