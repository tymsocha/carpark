package com.myprojects.carpark.domain.exception;

//Klasa dziedzicząca po klasie wyjątku, stworzona na podstawy aplikacji.
public class WrongNumberOfFloorsException extends Exception {
    //Nadpisanie metody z klasy rodzica
    @Override
    public String getMessage() {
        return "WRONG NUMBER OF FLOORS";
    }
}
