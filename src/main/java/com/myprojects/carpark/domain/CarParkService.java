package com.myprojects.carpark.domain;

import com.myprojects.carpark.domain.dto.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarParkService {

    CarSpotsGenerator carSpotsGenerator;
    OccupationRepository occupationRepository;
    SlotRepository slotRepository;
    Mapper mapper;

    GenerationTimeDto generateSlots(Integer floorsNumber, Integer spotsNumber) {
        Long start = System.currentTimeMillis();
        List<Slot> generatedSlots = carSpotsGenerator.generateCarParkMap(floorsNumber, spotsNumber);
        carSpotsGenerator.generateTimeUnitsThrough30Days();
        carSpotsGenerator.randomlySetOccupiedSlotsThrough30Days();
        Long end = System.currentTimeMillis();
        return mapper.mapToGenerationTimeDto(generatedSlots, (end - start));
    }

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
                    .occupiedTime(timeDTO.getOccupiedTime())
                    .energyConsumption(timeDTO.getOccupiedTime() * energyConsumption)
                    .energyCost(timeDTO.getOccupiedTime() / 2 * energyConsumption * cost)
                    .build();
        } catch (NullPointerException e) {
            System.out.println("WRONG SPOT NUMBER");
        }

        return EnergyConsumptionDto.builder().build();
    }


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

    private List<Integer> getAllFloorNumbers(List<OccupationTimeDTO> timeDTOS) {
        return timeDTOS.stream()
                .map(OccupationTimeDTO::getFloor)
                .distinct()
                .collect(Collectors.toList());
    }

    EmployeesNumberDto getAllEmployeesAndPriceOfSalaries(Integer spotsOccupied, Long hourlySalary) {
        return null;
    }

    Boolean checkIfSpotsGenerated() {
        List<Slot> slotList = slotRepository.findAll();
        return !slotList.isEmpty();
    }
}

