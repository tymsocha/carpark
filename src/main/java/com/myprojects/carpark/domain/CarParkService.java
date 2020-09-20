package com.myprojects.carpark.domain;

import com.myprojects.carpark.domain.dto.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@Service - adnotacja mówiąca springowi, aby utworzył beana typu danej klasy, która ma zadania serwisu
//@AllArgsConstructor - adnotacja z biblioteki Lombok tworząca konstruktor zewszystkimi polami tej klasy
//@FieldDefaults(level = AccessLevel.PRIVATE) - nadaje każdemu polu w klasie prywatny modyfikator dostępu
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarParkService {

    //Pola powstałe dzięki utworzeniu beanów w danych klasach
    CarSpotsGenerator carSpotsGenerator;
    OccupationRepository occupationRepository;
    SlotRepository slotRepository;
    Mapper mapper;

    //Metoda generująca używająca metod generatora: generuje i zapisuje dane do bazy
    GenerationTimeDto generateSlots(Integer floorsNumber, Integer spotsNumber) {
        Long start = System.currentTimeMillis();
        List<Slot> generatedSlots = carSpotsGenerator.generateCarParkMap(floorsNumber, spotsNumber);
        carSpotsGenerator.generateTimeUnitsThrough30Days();
        carSpotsGenerator.randomlySetOccupiedSlotsThrough30Days();
        Long end = System.currentTimeMillis();
        return mapper.mapToGenerationTimeDto(generatedSlots, (end - start));
    }

    //Metoda zwraca nazwy miejsc, kóre są zajęte w zależności od podanego czasu i piętra
    List<String> getOccupiedSlots(String timeString, Integer floor) {
        List<String> occupiedSlots;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(timeString, formatter);

        if (floor == null) {
            occupiedSlots = occupationRepository.getOccupiedSlots(dateTime);
        } else {
            occupiedSlots = occupationRepository.getOccupiedSlots(dateTime, floor);
        }

        return occupiedSlots;
    }

    //Metoda zwraca statystyki, przez jaki czas dane miejsce było zajęte na danym pietrze w danym czasie
    List<OccupationTimeDTO> getAmountOfOccupationTimePerSlot(Integer floor, String startDateString, String endDateString) {
        if (startDateString == null || endDateString == null) {
            List<OccupationTimeDTO> dtos = occupationRepository.selectSpotsAndCountOccupiedTime(floor);
            dtos.forEach(dto -> dto.setOccupiedTime(dto.getOccupiedTime() / 2));
            return dtos;
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime startDate = LocalDateTime.parse(startDateString, formatter);
            LocalDateTime endDate = LocalDateTime.parse(endDateString, formatter);
            List<OccupationTimeDTO> dtos = occupationRepository.selectSpotsAndCountOccupiedTime(floor, startDate, endDate);
            dtos.forEach(dto -> dto.setOccupiedTime(dto.getOccupiedTime() / 2));
            return dtos;
        }
    }

    //Metoda zwracająca statystyki związane z konsumpcją prądu na danym miejscu w danym przedziale czasowym
    EnergyConsumptionDto getElectricityConsumptionAndCost(String spot, Long energyConsumption, Long cost, String startDateString, String endDateString) {
        List<OccupationTimeDTO> dtos;
        OccupationTimeDTO timeDTO;

        if (startDateString == null || endDateString == null) {
            dtos = occupationRepository.selectSpotAndCountOccupiedTime(spot);
            timeDTO = !dtos.isEmpty() ? dtos.get(0) : OccupationTimeDTO.builder().build();
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime startDate = LocalDateTime.parse(startDateString, formatter);
            LocalDateTime endDate = LocalDateTime.parse(endDateString, formatter);
            dtos = occupationRepository.selectSpotAndCountOccupiedTime(spot, startDate, endDate);
            timeDTO = !dtos.isEmpty() ? dtos.get(0) : OccupationTimeDTO.builder().build();
        }

        try {
            return EnergyConsumptionDto.builder()
                    .spot(timeDTO.getSlotName())
                    .occupiedTime(timeDTO.getOccupiedTime() / 2)
                    .energyConsumption(timeDTO.getOccupiedTime() / 2 * energyConsumption)
                    .energyCost(timeDTO.getOccupiedTime() / 2 * energyConsumption * cost)
                    .build();
        } catch (NullPointerException e) {
            System.out.println("WRONG SPOT NUMBER");
        }

        return EnergyConsumptionDto.builder().build();
    }

    //Metoda zwracająca statystyki związane z konsumpcją prądu i kosztem na danym piętrze, w danym przedziale czasowym
    FloorEnergyDto getElectricityConsumptionAndCostPerFloor(Integer floor, Long energyConsumption, Long cost, String startDate, String endDate) {
        List<OccupationTimeDTO> occupationTimes = getAmountOfOccupationTimePerSlot(floor, startDate, endDate);

        List<EnergyConsumptionDto> energyConsumptionDtos = mapper.mapToEnergyConsumptionListDto(occupationTimes, energyConsumption, cost);

        Long totalEnergyConsumptionForFloor = energyConsumptionDtos.stream()
                .mapToLong(EnergyConsumptionDto::getEnergyConsumption).sum();

        Long totalEnergyCostForFloor = energyConsumptionDtos.stream()
                .mapToLong(EnergyConsumptionDto::getEnergyCost).sum();

        return FloorEnergyDto.builder()
                .totalEnergyConsumption(totalEnergyConsumptionForFloor)
                .totalEnergyCost(totalEnergyCostForFloor)
                .energyPerSpot(energyConsumptionDtos)
                .build();
    }

    //Metoda zwracająca statystyki związane z konsumpcją prądu i kosztem na całym parkingu, w danym przedziale czasowym
    CarParkEnergyDto getEletricityConsumptionAndCostForCarPark(Long energyConsumption, Long cost, String startDateString, String endDateString) {
        List<OccupationTimeDTO> timeDTOS;
        List<FloorEnergyDto> floorEnergyDtos = new ArrayList<>();

        if (startDateString == null || endDateString == null) {
            timeDTOS = occupationRepository.selectAllParkingSpotsAndCountOccupiedTime();
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime startDate = LocalDateTime.parse(startDateString, formatter);
            LocalDateTime endDate = LocalDateTime.parse(endDateString, formatter);
            timeDTOS = occupationRepository.selectAllParkingSpotsAndCountOccupiedTime(startDate, endDate);
        }

        List<Integer> floors = getAllFloorNumbers(timeDTOS);

        for (Integer floor : floors) {
            floorEnergyDtos.add(getElectricityConsumptionAndCostPerFloor(floor, energyConsumption, cost, startDateString, endDateString));
        }

        Long totalEnergyConsumption = floorEnergyDtos.stream()
                .mapToLong(FloorEnergyDto::getTotalEnergyConsumption).sum();

        Long totalEnergyCost = floorEnergyDtos.stream()
                .mapToLong(FloorEnergyDto::getTotalEnergyCost).sum();

        return CarParkEnergyDto.builder()
                .floors(floorEnergyDtos)
                .totalEnergyConsumption(totalEnergyConsumption)
                .totalEnergyCost(totalEnergyCost)
                .build();
    }

    //Metoda zwracająca wszystkie numery pięter na podstawie listy obiektów typu TimeDto
    private List<Integer> getAllFloorNumbers(List<OccupationTimeDTO> timeDTOS) {
        return timeDTOS.stream()
                .map(OccupationTimeDTO::getFloor)
                .distinct()
                .collect(Collectors.toList());
    }

    //Metoda zwracająca dane na temat ilości pracowników na piętrze i całym parkingu, i ich wypłat, w zależności od ilości pracowników potrzebnych do obsługi zadanej ilości miejsc
    EmployeesNumberDto getAllEmployeesAndPriceOfSalaries(Integer spotsToService, Long hourlySalary) {
        List<Slot> slotList = slotRepository.findAll();
        Map<Integer, List<String>> mapOfFlorsAndSpots = new HashMap<>();
        Integer floorNumber = 0;
        Integer numberOfSpotsPerFloor;
        Integer numberOfFloors;
        EmployeesNumberDto employeesNumberDto = null;

        for (Slot slot : slotList) {
            if (!slot.getName().startsWith(floorNumber.toString())) {
                floorNumber = Integer.parseInt(Character.toString(slot.getName().charAt(0)));
            }
            mapOfFlorsAndSpots.putIfAbsent(floorNumber, new ArrayList<>());
            mapOfFlorsAndSpots.get(floorNumber).add(slot.getName());
        }

        if (!mapOfFlorsAndSpots.isEmpty()) {
            numberOfSpotsPerFloor = mapOfFlorsAndSpots.get(0).size();
            numberOfFloors = mapOfFlorsAndSpots.keySet().size();
            Integer numberOfEmployeesPerFloor = numberOfSpotsPerFloor % spotsToService == 0 ? numberOfSpotsPerFloor / spotsToService : numberOfSpotsPerFloor / spotsToService + 1;

            employeesNumberDto = EmployeesNumberDto.builder()
                    .numberOfEmployeesPerFloor(numberOfEmployeesPerFloor)
                    .numberOfAllEmployees(numberOfEmployeesPerFloor * numberOfFloors)
                    .salaryOfOneEmployeePerDay(14 * hourlySalary)
                    .totalPriceToPayPerDay(14 * hourlySalary * numberOfEmployeesPerFloor * numberOfFloors)
                    .build();
        }

        return employeesNumberDto;
    }

    //Metoda sprawdzająca czy miejsca zostały wygenerowane
    Boolean checkIfSpotsGenerated() {
        List<Slot> slotList = slotRepository.findAll();
        return !slotList.isEmpty();
    }
}

