package com.myprojects.carpark.domain;

import com.myprojects.carpark.domain.dto.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarParkApi {

    CarParkService carParkService;

    @Transactional
    public GenerationTimeDto generateSlots(Integer numberOfFloors, Integer numberOfSlots) {
        return carParkService.generateSlots(numberOfFloors, numberOfSlots);
    }

    @Transactional(readOnly = true)
    public List<String> getOccupiedSlots(String timeString, Integer floor) {
        return carParkService.getOccupiedSlots(timeString, floor);
    }

    @Transactional(readOnly = true)
    public List<OccupationTimeDTO> getAmountOfOccupationTimeForSlot(Integer floor, String startDate, String endDate) {
        return carParkService.getAmountOfOccupationTimePerSlot(floor, startDate, endDate);
    }

    @Transactional(readOnly = true)
    public EnergyConsumptionDto getElectricityConsumptionAndCost(String spot, Long energyConsumption, Long cost, String startDate, String endDate) {
        return carParkService.getElectricityConsumptionAndCost(spot, energyConsumption, cost, startDate, endDate);
    }

    @Transactional(readOnly = true)
    public FloorEnergyDto getElectricityConsumptionAndCostPerFloor(Integer floor, Long energyConsumption, Long cost, String startDate, String endDate) {
        return carParkService.getElectricityConsumptionAndCostPerFloor(floor, energyConsumption, cost, startDate, endDate);
    }

    @Transactional(readOnly = true)
    public CarParkEnergyDto getElectricityConsumptionAndCostForCarPark(Long energyConsumption, Long cost, String startDate, String endDate) {
        return carParkService.getEletricityConsumptionAndCostForCarPark(energyConsumption, cost, startDate, endDate);
    }

    @Transactional(readOnly = true)
    public EmployeesNumberDto getEmployeesAndPriceOfSalaries(Integer spotsOccupied, Long hourlySalary) {
        return carParkService.getAllEmployeesAndPriceOfSalaries(spotsOccupied, hourlySalary);
    }

    @Transactional(readOnly = true)
    public Boolean checkIfSpotsGenerated() {
        return carParkService.checkIfSpotsGenerated();
    }
}
