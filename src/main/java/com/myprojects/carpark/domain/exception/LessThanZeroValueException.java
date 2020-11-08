package com.myprojects.carpark.domain.exception;

//Klasa dziedzicząca po klasie wyjątku, stworzona na podstawy aplikacji.
public class LessThanZeroValueException extends Exception {
    //Nadpisanie metody z klasy rodzica
    @Override
    public String getMessage() {
        return "VALUE IS LESS THAN ZERO";
    }
}
