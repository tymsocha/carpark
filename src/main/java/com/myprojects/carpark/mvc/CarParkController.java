package com.myprojects.carpark.mvc;

import com.myprojects.carpark.domain.CarParkApi;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@AllArgsConstructor
public class CarParkController {

    CarParkApi carParkApi;

    @RequestMapping(method = RequestMethod.POST, value = "floor/{floors}/spots/{spots}")
    public ResponseEntity<?> generateCarPark(
            @PathVariable("floors") Integer floors,
            @PathVariable("spots") Integer spots
    ) {
        return ResponseEntity.ok(carParkApi.generateSlots(floors, spots));
    }

    @RequestMapping(method = RequestMethod.GET, value = "check")
    public ResponseEntity<?> checkIfCarParkGenerated() {
        return ResponseEntity.ok(carParkApi.checkIfSpotsGenerated());
    }

    @RequestMapping(method = RequestMethod.GET, value = "occupiedSlots")
    public ResponseEntity<?> getSpotsOccupiedInTimePeriod(
            @RequestParam String timeString,
            @RequestParam(required = false) Integer floor
            ) {
       return ResponseEntity.ok(carParkApi.getOccupiedSlots(timeString, floor));
    }

    @RequestMapping(method = RequestMethod.GET, value = "occupation/floor/{floor}")
    public ResponseEntity<?> getOccupationTimeAmount(
            @PathVariable("floor") Integer floor,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate
    ) {
        return ResponseEntity.ok(carParkApi.getAmountOfOccupationTimeForSlot(floor, startDate, endDate));
    }

    @RequestMapping(method = RequestMethod.GET, value = "energyConsumption/spot/{spot}")
    public ResponseEntity<?> getElectricityConsumptionPerSpotAndCost(
            @PathVariable("spot") String spot,
            @RequestParam Long energyConsumption,
            @RequestParam Long cost,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate
    ) {
        return ResponseEntity.ok(carParkApi.getElectricityConsumptionAndCost(spot, energyConsumption, cost, startDate, endDate));
    }

    @RequestMapping(method = RequestMethod.GET, value = "energyConsumption/floor/{floor}")
    public ResponseEntity<?> getElectricityConsumptionPerFloorAndCost(
            @PathVariable("floor") Integer floor,
            @RequestParam Long energyConsumption,
            @RequestParam Long cost,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate
    ) {
        return ResponseEntity.ok(carParkApi.getElectricityConsumptionAndCostPerFloor(floor, energyConsumption, cost, startDate, endDate));
    }

    @RequestMapping(method = RequestMethod.GET, value = "energyConsumption")
    public ResponseEntity<?> getElectricityConsumptionForCarPark(
            @RequestParam Long energyConsumption,
            @RequestParam Long cost,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate
    ) {
        return ResponseEntity.ok(carParkApi.getElectricityConsumptionAndCostForCarPark(energyConsumption, cost, startDate, endDate));
    }

    @RequestMapping(method = RequestMethod.GET, value = "employees/spots/{spots}")
    public ResponseEntity<?> getNumberOfEmployeesForFloorsAndTheirDailySalary(
            @PathVariable("spots") Integer spotsToService,
            @RequestParam Long hourlySalary
    ) {
        return ResponseEntity.ok(carParkApi.getEmployeesAndPriceOfSalaries(spotsToService, hourlySalary));
    }
}
