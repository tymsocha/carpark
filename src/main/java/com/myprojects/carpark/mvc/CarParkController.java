package com.myprojects.carpark.mvc;

import com.myprojects.carpark.domain.CarParkApi;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//Klasa będąca kontrolerem aplikacji, która ma bezpośrednie połączenie z frontową częscią aplikacji.
//Wysyła i odbiera dane z interfejsu użytkownika w postaci URLi i dodatkowych parametrów
//Tutaj są implementowane tzw. endpointy czyli punkty końcowe aplikacji, do których można się odwołać najczęsciej po to aby pobrać dane.
//@RestController - adnotacja reprezentująca komponentu springowego zajmującego się tworzeniem endpointów
//@CrossOrigin - adnotacja umożliwiająca połączenie pomiędzy aplikacją kliencką a aplikacją od strony serwerowej
//@AllArgsConstructor - Adnotacja z biblioteki Lombok, tworząca konstruktor wykorzystująca wszystkie pola klasy
@RestController
@CrossOrigin
@AllArgsConstructor
public class CarParkController {

    //Pola powstałe dzięki utworzeniu beanów w danych klasach
    CarParkApi carParkApi;

    //@RequestMapping - adnotacja, w której określana jest metoda RESTowa (np.: POST, GET, DELETE), a także url, dzięki któremu tą metodę można wykorzystać w aplikacji klienckiej
    //Klasa ResponseEntity jest użyta specjalnie na potrzeby aplikacji restowych, gdzie można określić inee parametry jak np.: kod odpowiedzi
    //@PathVariable - adnotacja określająca zmienną zawartą w ścieżce do endpointa, jest ona wymagana
    //Metoda ta generuje miejsca parkingowe i zapisuje dane w bazie
    @RequestMapping(method = RequestMethod.POST, value = "floor/{floors}/spots/{spots}")
    public ResponseEntity<?> generateCarPark(
            @PathVariable("floors") Integer floors,
            @PathVariable("spots") Integer spots
    ) {
        return ResponseEntity.ok(carParkApi.generateSlots(floors, spots));
    }

    //@RequestMapping - adnotacja, w której określana jest metoda RESTowa (np.: POST, GET, DELETE), a także url, dzięki któremu tą metodę można wykorzystać w aplikacji klienckiej
    //Klasa ResponseEntity jest użyta specjalnie na potrzeby aplikacji restowych, gdzie można określić inee parametry jak np.: kod odpowiedzi
    @RequestMapping(method = RequestMethod.GET, value = "check")
    public ResponseEntity<?> checkIfCarParkGenerated() {
        return ResponseEntity.ok(carParkApi.checkIfSpotsGenerated());
    }

    //@RequestMapping - adnotacja, w której określana jest metoda RESTowa (np.: POST, GET, DELETE), a także url, dzięki któremu tą metodę można wykorzystać w aplikacji klienckiej
    //Klasa ResponseEntity jest użyta specjalnie na potrzeby aplikacji restowych, gdzie można określić inee parametry jak np.: kod odpowiedzi
    //@RequestParam - adnotacja określająca parametry opcjonalne przekazywane do endpointa
    //Metoda zwracająca listę zajętych miejsc danym czasie
    @RequestMapping(method = RequestMethod.GET, value = "occupiedSlots")
    public ResponseEntity<?> getSpotsOccupiedInTimePeriod(
            @RequestParam String timeString,
            @RequestParam(required = false) Integer floor
            ) {
       return ResponseEntity.ok(carParkApi.getOccupiedSlots(timeString, floor));
    }

    //@RequestMapping - adnotacja, w której określana jest metoda RESTowa (np.: POST, GET, DELETE), a także url, dzięki któremu tą metodę można wykorzystać w aplikacji klienckiej
    //Klasa ResponseEntity jest użyta specjalnie na potrzeby aplikacji restowych, gdzie można określić inee parametry jak np.: kod odpowiedzi
    //@RequestParam - adnotacja określająca parametry opcjonalne przekazywane do endpointa
    //@PathVariable - adnotacja określająca zmienną zawartą w ścieżce do endpointa, jest ona wymagana
    //Metoda zwracająca statystyki przez ile czasu dane miejsce było zajęte
    @RequestMapping(method = RequestMethod.GET, value = "occupation/floor/{floor}")
    public ResponseEntity<?> getOccupationTimeAmount(
            @PathVariable("floor") Integer floor,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate
    ) {
        return ResponseEntity.ok(carParkApi.getAmountOfOccupationTimeForSlot(floor, startDate, endDate));
    }

    //@RequestMapping - adnotacja, w której określana jest metoda RESTowa (np.: POST, GET, DELETE), a także url, dzięki któremu tą metodę można wykorzystać w aplikacji klienckiej
    //Klasa ResponseEntity jest użyta specjalnie na potrzeby aplikacji restowych, gdzie można określić inee parametry jak np.: kod odpowiedzi
    //@RequestParam - adnotacja określająca parametry opcjonalne przekazywane do endpointa
    //@PathVariable - adnotacja określająca zmienną zawartą w ścieżce do endpointa, jest ona wymagana
    //Metoda zwracająca statystyki, przez ile czasu dane miejsce było zajęte, jakie było zużycie energii na dane miejsce i jego koszt
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

    //@RequestMapping - adnotacja, w której określana jest metoda RESTowa (np.: POST, GET, DELETE), a także url, dzięki któremu tą metodę można wykorzystać w aplikacji klienckiej
    //Klasa ResponseEntity jest użyta specjalnie na potrzeby aplikacji restowych, gdzie można określić inee parametry jak np.: kod odpowiedzi
    //@RequestParam - adnotacja określająca parametry opcjonalne przekazywane do endpointa
    //@PathVariable - adnotacja określająca zmienną zawartą w ścieżce do endpointa, jest ona wymagana
    //Metoda zwracająca statystyki, przez ile czasu dane miejsce było zajęte, jakie było zużycie energii na dane miejsce i jego koszt, dla danego piętra i przedziału czasowego
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

    //@RequestMapping - adnotacja, w której określana jest metoda RESTowa (np.: POST, GET, DELETE), a także url, dzięki któremu tą metodę można wykorzystać w aplikacji klienckiej
    //Klasa ResponseEntity jest użyta specjalnie na potrzeby aplikacji restowych, gdzie można określić inee parametry jak np.: kod odpowiedzi
    //@RequestParam - adnotacja określająca parametry opcjonalne przekazywane do endpointa
    //Metoda zwracająca statystyki, przez ile czasu dane miejsce było zajęte, jakie było zużycie energii na dane miejsce i jego koszt, dla całego parkingu i przedziału czasowego
    @RequestMapping(method = RequestMethod.GET, value = "energyConsumption")
    public ResponseEntity<?> getElectricityConsumptionForCarPark(
            @RequestParam Long energyConsumption,
            @RequestParam Long cost,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate
    ) {
        return ResponseEntity.ok(carParkApi.getElectricityConsumptionAndCostForCarPark(energyConsumption, cost, startDate, endDate));
    }

    //@RequestMapping - adnotacja, w której określana jest metoda RESTowa (np.: POST, GET, DELETE), a także url, dzięki któremu tą metodę można wykorzystać w aplikacji klienckiej
    //Klasa ResponseEntity jest użyta specjalnie na potrzeby aplikacji restowych, gdzie można określić inee parametry jak np.: kod odpowiedzi
    //@RequestParam - adnotacja określająca parametry opcjonalne przekazywane do endpointa
    //@PathVariable - adnotacja określająca zmienną zawartą w ścieżce do endpointa, jest ona wymagana
    //Metoda zwracająca liczbę pracowników potrzebnych na piętro, cały parking i koszt dziennego utrzymania parkingu
    @RequestMapping(method = RequestMethod.GET, value = "employees/spots/{spots}")
    public ResponseEntity<?> getNumberOfEmployeesForFloorsAndTheirDailySalary(
            @PathVariable("spots") Integer spotsToService,
            @RequestParam Long hourlySalary
    ) {
        return ResponseEntity.ok(carParkApi.getEmployeesAndPriceOfSalaries(spotsToService, hourlySalary));
    }
}
