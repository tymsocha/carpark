package com.myprojects.carpark.exception;

public class WrongNumberOfFloorsException extends Exception{
    @Override
    public String getMessage() {
        return "WRONG NUMBER OF FLOORS";
    }
}
