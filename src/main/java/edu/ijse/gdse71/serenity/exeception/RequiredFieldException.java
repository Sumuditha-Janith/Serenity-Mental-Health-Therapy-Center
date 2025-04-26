package edu.ijse.gdse71.serenity.exeception;

public class RequiredFieldException extends Exception {
    public RequiredFieldException(String fieldName) {
        super("Please fill the " + fieldName + " field");
    }
}
