package main.java.com.cdc.atm.util;

import main.java.com.cdc.atm.model.Account;

import java.util.InputMismatchException;

public class Validation {

    public Validation isValidIntegerString(String strAmount, String errorMessage) throws InputMismatchException {
        if(!strAmount.matches("[0-9]+")){
            throw new InputMismatchException(errorMessage);
        }

        return this;
    }

    public Validation isValidAccount(Account account, String errorMessage) throws InputMismatchException{
        if(account == null) {
            throw new InputMismatchException(errorMessage);
        }

        return  this;
    }

    public Validation aLessThanB(Double a, Double b, String errorMessage) throws InputMismatchException{
        if(a > b){
            throw new InputMismatchException(errorMessage);
        }


        return this;
    }

    public Validation aGreaterThanB(Double a, Double b, String errorMessage) throws InputMismatchException{
        if(a < b){
            throw new InputMismatchException(errorMessage);
        }

        return this;
    }


}
