package com.myprojects.carpark.domain;

import com.myprojects.carpark.exception.WrongNumberOfFloorsException;
import com.myprojects.carpark.exception.WrongNumberOfSpotsException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class CarSpotsGenerator {

    SlotRepository slotRepository;

    List<Slot> generateCarParkMap(int numberOfFloors, int numberOfSpots) {
        List<Slot> slotList = new ArrayList<>();
        try {
            checkProvidedValues(numberOfFloors, numberOfSpots);
            slotList = createSlots(numberOfFloors, numberOfSpots);
        } catch (WrongNumberOfSpotsException | WrongNumberOfFloorsException e) {
            System.out.println(e.getMessage());
        }
        return slotList;
    }

    private List<Slot> createSlots(Integer numberOfFloors, Integer numberOfSpots) {
        List<Slot> slotList = new ArrayList<>();
        Slot slot;
        for (Integer floorNumber = 0; floorNumber < numberOfFloors; floorNumber++) {
            for (Integer spotNumber = 1; spotNumber <= numberOfSpots; spotNumber++) {
                if (spotNumber.toString().length() < 2) {
                    slot = Slot.builder()
                            .floorNumber(floorNumber)
                            .name(floorNumber.toString() + "0" + spotNumber.toString())
                            .build();
                    slotList.add(slot);
                } else {
                    slot = Slot.builder()
                            .floorNumber(floorNumber)
                            .name(floorNumber.toString() + spotNumber.toString())
                            .build();
                    slotList.add(slot);
                }
            }
        }
        System.out.println(slotList);
        return slotRepository.saveAll(slotList);
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
