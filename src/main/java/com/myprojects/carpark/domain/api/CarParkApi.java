package com.myprojects.carpark.domain.api;

import com.myprojects.carpark.domain.dto.*;
import com.myprojects.carpark.domain.service.CarParkService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//@Service - adnotacja mówiąca springowi, aby utworzył beana typu danej klasy, która ma zadania serwisu
//@AllArgsConstructor - adnotacja z biblioteki Lombok tworząca konstruktor zewszystkimi polami tej klasy
//@FieldDefaults(level = AccessLevel.PRIVATE) - nadaje każdemu polu w klasie prywatny modyfikator dostępu
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarParkApi {

    //Pole powstałe dzięki utworzeniu beana na podstawie klasy CarParkService
    CarParkService carParkService;

    //@Transactional - adnotacja zapewniająca wykonanie wtedy i tylko wtedy, gdy nie wystąpi żaden błąd i wszystkie metody na niższych poziomach wykonają się poprawnie
    //Metoda generująca losowo zajęte miejsca parkingowe
    @Transactional
    public GenerationTimeDto generateSlots(Integer numberOfFloors, Integer numberOfSlots) {
        return carParkService.generateSlots(numberOfFloors, numberOfSlots);
    }

    //@Transactional - adnotacja zapewniająca wykonanie wtedy i tylko wtedy, gdy nie wystąpi żaden błąd i wszystkie metody na niższych poziomach wykonają się poprawnie
    //Dodatkowo pole readOny ustawione na wartość true oznacza, że jedyna ingerencja w bazę danych to ich odczyt
    //Metoda zwracająca listę zajętych miejsc danym czasie
    @Transactional(readOnly = true)
    public List<String> getOccupiedSlots(String timeString, Integer floor) {
        return carParkService.getOccupiedSlots(timeString, floor);
    }

    //@Transactional - adnotacja zapewniająca wykonanie wtedy i tylko wtedy, gdy nie wystąpi żaden błąd i wszystkie metody na niższych poziomach wykonają się poprawnie
    //Dodatkowo pole readOny ustawione na wartość true oznacza, że jedyna ingerencja w bazę danych to ich odczyt
    //Metoda zwracająca statystyki przez ile czasu dane miejsce było zajęte
    @Transactional(readOnly = true)
    public List<OccupationTimeDTO> getAmountOfOccupationTimeForSlot(Integer floor, String startDate, String endDate) {
        return carParkService.getAmountOfOccupationTimePerSlot(floor, startDate, endDate);
    }

    //@Transactional - adnotacja zapewniająca wykonanie wtedy i tylko wtedy, gdy nie wystąpi żaden błąd i wszystkie metody na niższych poziomach wykonają się poprawnie
    //Dodatkowo pole readOny ustawione na wartość true oznacza, że jedyna ingerencja w bazę danych to ich odczyt
    //Metoda zwracająca statystyki, przez ile czasu dane miejsce było zajęte, jakie było zużycie energii na dane miejsce i jego koszt
    @Transactional(readOnly = true)
    public EnergyConsumptionDto getElectricityConsumptionAndCost(String spot, Double energyConsumption, Double cost, String startDate, String endDate) {
        return carParkService.getElectricityConsumptionAndCost(spot, energyConsumption, cost, startDate, endDate);
    }

    //@Transactional - adnotacja zapewniająca wykonanie wtedy i tylko wtedy, gdy nie wystąpi żaden błąd i wszystkie metody na niższych poziomach wykonają się poprawnie
    //Dodatkowo pole readOny ustawione na wartość true oznacza, że jedyna ingerencja w bazę danych to ich odczyt
    //Metoda zwracająca statystyki, przez ile czasu dane miejsce było zajęte, jakie było zużycie energii na dane miejsce i jego koszt, dla danego piętra i przedziału czasowego
    @Transactional(readOnly = true)
    public FloorEnergyDto getElectricityConsumptionAndCostPerFloor(Integer floor, Double energyConsumption, Double cost, String startDate, String endDate) {
        return carParkService.getElectricityConsumptionAndCostPerFloor(floor, energyConsumption, cost, startDate, endDate);
    }

    //@Transactional - adnotacja zapewniająca wykonanie wtedy i tylko wtedy, gdy nie wystąpi żaden błąd i wszystkie metody na niższych poziomach wykonają się poprawnie
    //Dodatkowo pole readOny ustawione na wartość true oznacza, że jedyna ingerencja w bazę danych to ich odczyt
    //Metoda zwracająca statystyki, przez ile czasu dane miejsce było zajęte, jakie było zużycie energii na dane miejsce i jego koszt, dla całego parkingu i przedziału czasowego
    @Transactional(readOnly = true)
    public CarParkEnergyDto getElectricityConsumptionAndCostForCarPark(Double energyConsumption, Double cost, String startDate, String endDate) {
        return carParkService.getEletricityConsumptionAndCostForCarPark(energyConsumption, cost, startDate, endDate);
    }

    //@Transactional - adnotacja zapewniająca wykonanie wtedy i tylko wtedy, gdy nie wystąpi żaden błąd i wszystkie metody na niższych poziomach wykonają się poprawnie
    //Dodatkowo pole readOny ustawione na wartość true oznacza, że jedyna ingerencja w bazę danych to ich odczyt
    //Metoda zwracająca liczbę pracowników potrzebnych na piętro, cały parking i koszt dziennego utrzymania parkingu
    @Transactional(readOnly = true)
    public EmployeesNumberDto getEmployeesAndPriceOfSalaries(Integer spotsOccupied, Double hourlySalary)  {
        return carParkService.getAllEmployeesAndPriceOfSalaries(spotsOccupied, hourlySalary);
    }

    //@Transactional - adnotacja zapewniająca wykonanie wtedy i tylko wtedy, gdy nie wystąpi żaden błąd i wszystkie metody na niższych poziomach wykonają się poprawnie
    //Dodatkowo pole readOny ustawione na wartość true oznacza, że jedyna ingerencja w bazę danych to ich odczyt
    //Metoda zwraca informację czy w bazie danych znajdują się wygenerowane dane
    @Transactional(readOnly = true)
    public Boolean checkIfSpotsGenerated() {
        return carParkService.checkIfSpotsGenerated();
    }

    @Transactional
    public ClosedFloorDto closeTheFloor(Integer floor, String startDate, String endDate) {
        return carParkService.closeTheFloor(floor, startDate, endDate);
    }

    public List<ConclusionDto> getAverageOccupationTimesForFloors() {
        return carParkService.getAverageOccupationTimesForFloors();
    }

    public ConclusionDto getAverageOccupationTimeForCarPark() {
        return carParkService.getAverageOccupationTimeForCarPark();
    }
}
