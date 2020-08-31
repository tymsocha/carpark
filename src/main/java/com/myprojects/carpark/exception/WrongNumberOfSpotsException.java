package com.myprojects.carpark.exception;

public class WrongNumberOfSpotsException extends Exception {
    @Override
    public String getMessage() {
        return "WRONG NUMBER OF SPOTS";
    }
}
