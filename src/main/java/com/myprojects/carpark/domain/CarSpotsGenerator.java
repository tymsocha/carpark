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

//@Component - adnotacja mówiąca springowi, aby utworzył beana typu danej klasy
//@AllArgsConstructor - adnotacja z biblioteki Lombok tworząca konstruktor zewszystkimi polami tej klasy
//@FieldDefaults(level = AccessLevel.PRIVATE) - nadaje każdemu polu w klasie prywatny modyfikator dostępu
@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class CarSpotsGenerator {

    //Pola powstałe dzięki utworzeniu beanów w danych klasach
    SlotRepository slotRepository;
    TimeUnitRepository timeUnitRepository;
    OccupationRepository occupationRepository;

    //Metoda generująca listę miejsc parkingowych i sprawdzająca arametry parkingu
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

    //Metoda generująca listę miejsc i zapisująca je do bazy danych
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

    //Metoda sprawdzająca czy podane liczby reprezentujące ilość pięter i miejsc są poprawne
    //Gdy nie są metoda wyrzuca wyjątki
    private void checkProvidedValues(int numberOfFloors, int numberOfSpots) throws WrongNumberOfFloorsException, WrongNumberOfSpotsException {
        if (numberOfFloors < 1 || numberOfFloors > 10) {
            throw new WrongNumberOfFloorsException();
        }
        if (numberOfSpots < 1 || numberOfSpots > 100) {
            throw new WrongNumberOfSpotsException();
        }
    }

    //Metoda generująca obiekty typu TimeUnit wraz z datami do 30 dni wstecz, które są zapisywane do bazy danych
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

    //Metoda sprawdzajęca czy dany dzień jest ostatni w miesiącu i zwracająca kolejny dzień w kalendarzu
    private LocalDateTime checkMonthAndSetNewDay(LocalDateTime date) {
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

    //Metoda sprawdzająca czy dany rok jest przestępny i ile dni ma luty
    private boolean checkIfLastDayOfFebruary(LocalDateTime dateTime, Integer day) {
        Year year = Year.of(dateTime.getYear());
        if (year.isLeap() && day.equals(29)) {
            return true;
        } else {
            return !year.isLeap() && day.equals(28);
        }
    }

    //Metoda zwracająca kolejny dzień miesiąca
    private LocalDateTime setDateOfNewMonth(LocalDateTime date) {
        return LocalDateTime.of(date.getYear(), date.getMonthValue() + 1, 1, 8, 0);
    }

    //Metoda, która losowo wyznacza w jakim czasie i które miejsce było zajęte
    void randomlySetOccupiedSlotsThrough30Days() {
        List<Occupation> valuesOfOccupiedSeats = new ArrayList<>();
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
                valuesOfOccupiedSeats.add(occupation);
            }
        }
        occupationRepository.saveAll(valuesOfOccupiedSeats);
    }
}
