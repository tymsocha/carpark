package com.myprojects.carpark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//Klasa główna aplikacji. Metoda main startuje aplikację i kontekst springowy, dzięki adnotacji @SpringBootApplication
@SpringBootApplication
public class CarParkApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarParkApplication.class, args);
    }
}
