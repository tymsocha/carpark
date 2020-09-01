package com.myprojects.carpark.domain;

import com.myprojects.carpark.exception.WrongNumberOfFloorsException;
import com.myprojects.carpark.exception.WrongNumberOfSpotsException;
import com.myprojects.carpark.utils.GeneralConstants;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class CarSpotsGenerator {

    SlotRepository slotRepository;
    TimeUnitRepository timeUnitRepository;
    OccupationRepository occupationRepository;

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
                } else if (spotNumber.toString().length() > 2) {
                    Integer hundredthNumber = floorNumber + 1;
                    slot = Slot.builder()
                            .floorNumber(floorNumber)
                            .name(hundredthNumber.toString() + "00")
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

    void generateTimeUnitsThrough30Days() {
        List<TimeUnit> timeUnits = new ArrayList<>();
        LocalDateTime date30DaysAgo = LocalDateTime.now().minusDays(30);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime date = LocalDateTime.of(date30DaysAgo.getYear(), date30DaysAgo.getMonth(), date30DaysAgo.getDayOfMonth(), 8, 0);
        boolean flag = true;
        TimeUnit timeUnit;

        while (flag) {
            if (!date.toLocalTime().isAfter(LocalTime.of(22, 1))) {
                timeUnit = TimeUnit.builder().dateTime(date).build();
                date = date.plusMinutes(30);
                timeUnits.add(timeUnit);
            } else {
                date = checkMonthAndSetNewDay(date);
            }

            if (date.plusMinutes(30).isAfter(now)) {
                flag = false;
            }
        }

        timeUnitRepository.saveAll(timeUnits);
    }


    LocalDateTime checkMonthAndSetNewDay(LocalDateTime date) {
        Integer day = date.getDayOfMonth();
        Integer month = date.getMonthValue();
        if (GeneralConstants.monthsWith31Days.contains(month) && day.equals(31)) {
            date = setDateOfNewMonth(date);
        } else if (GeneralConstants.monthsWith30Days.contains(month) && day.equals(30)) {
            date = setDateOfNewMonth(date);
        } else if (date.getMonthValue() == GeneralConstants.february && checkIfLastDayOfFebruary(date, day)) {
            date = setDateOfNewMonth(date);
        } else {
            date = LocalDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth() + 1, 8, 0);
        }
        return date;
    }

    private boolean checkIfLastDayOfFebruary(LocalDateTime dateTime, Integer day) {
        Year year = Year.of(dateTime.getYear());
        if (year.isLeap() && day.equals(29)) {
            return true;
        } else {
            return !year.isLeap() && day.equals(28);
        }
    }

    private LocalDateTime setDateOfNewMonth(LocalDateTime date) {
        return LocalDateTime.of(date.getYear(), date.getMonthValue() + 1, 1, 8, 0);
    }

    void randomlySetOccupiedSlotsThrough30Days() {
        List<Occupation> valuesOfOcuppiedSeats = new ArrayList<>();
        List<Slot> allSlots = slotRepository.findAll();
        List<TimeUnit> allTimeUnits = timeUnitRepository.findAll();
        Random random = new Random();
        Occupation occupation;
        for (Slot slot : allSlots) {
            for (TimeUnit timeUnit : allTimeUnits) {
                occupation = Occupation.builder()
                                .slot(slot)
                                .timeUnit(timeUnit)
                                .occupied(random.nextBoolean())
                                .build();
                valuesOfOcuppiedSeats.add(occupation);
            }
        }
        occupationRepository.saveAll(valuesOfOcuppiedSeats);
    }
}
