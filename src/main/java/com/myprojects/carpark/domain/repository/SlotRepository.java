package com.myprojects.carpark.domain.repository;

import com.myprojects.carpark.domain.dto.CarParkStructureDto;
import com.myprojects.carpark.domain.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

//Interfejs służący do połączenia z bazą danych i dzięki temu, że dziedziczy po Interfejsie JpaRepository, dziedziczy wszystkie jego metody
//@Repository - adnotacja mówiąca springowi, aby stworzył beana służącego do połączenia z bazą danych, na podstawie tego interfejsu.
@Repository
public interface SlotRepository extends JpaRepository<Slot, Long> {

    @Query(
            "SELECT new com.myprojects.carpark.domain.dto.CarParkStructureDto(s.floorNumber, COUNT(s)) FROM Slot s " +
            "GROUP BY s.floorNumber"
    )
    List<CarParkStructureDto> getAllFloorsAndSpots();
}
