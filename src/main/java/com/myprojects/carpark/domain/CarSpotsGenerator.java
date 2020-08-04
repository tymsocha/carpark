package com.myprojects.carpark.domain;

import com.myprojects.carpark.exception.WrongNumberOfFloorsException;
import com.myprojects.carpark.exception.WrongNumberOfSpotsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarSpotsGenerator {

    @Autowired
    private CarParkMap carParkMap;

    public CarParkMap generateCarParkMap(int numberOfFloors, int numberOfSpots) {
        try {
            checkProvidedValues(numberOfFloors, numberOfSpots);
        } catch (WrongNumberOfSpotsException | WrongNumberOfFloorsException e) {
            System.out.println(e);
        }



        return carParkMap;
    }

    private void checkProvidedValues(int numberOfFloors, int numberOfSpots) throws WrongNumberOfFloorsException, WrongNumberOfSpotsException {
        if (numberOfFloors < 1 || numberOfFloors > 10) {
            throw new WrongNumberOfFloorsException();
        }
        if (numberOfSpots < 1 || numberOfSpots > 100) {
            throw new WrongNumberOfSpotsException();
        }
    }


}
