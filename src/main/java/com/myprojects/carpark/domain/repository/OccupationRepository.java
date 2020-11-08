package com.myprojects.carpark.domain.repository;

import com.myprojects.carpark.domain.dto.OccupationTimeDTO;
import com.myprojects.carpark.domain.entity.Occupation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

//Interfejs służący do połączenia z bazą danych i dzięki temu, że dziedziczy po Interfejsie JpaRepository, dziedziczy wszystkie jego metody
//@Repository - adnotacja mówiąca springowi, aby stworzył beana służącego do połączenia z bazą danych, na podstawie tego interfejsu.
@Repository
public interface OccupationRepository extends JpaRepository<Occupation, Long> {

    //Adnotacja @Query wysyła zapytanie w języku hql do bazy dancyh
    //Metoda ta wyciąga z bazy danych Listę nazw miejsc parkingowych, które są zajęte w danym czasie
    @Query(
            "SELECT DISTINCT slot.name FROM Slot slot " +
            "LEFT JOIN Occupation occupation ON occupation.slot.id = slot.id " +
            "LEFT JOIN TimeUnit time ON occupation.timeUnit.id = time.id " +
            "WHERE occupation.occupied = true " +
            "AND occupation.timeUnit.dateTime = :dateTime"
    )
    List<String> getOccupiedSlots(@Param("dateTime") LocalDateTime dateTime);

    //Adnotacja @Query wysyła zapytanie w języku hql do bazy dancyh
    //Metoda ta wyciąga z bazy danych Listę nazw miejsc parkingowych, które są zajęte w danym czasie, na danym piętrze
    @Query(
            "SELECT DISTINCT slot.name FROM Slot slot " +
            "LEFT JOIN Occupation occupation ON occupation.slot.id = slot.id " +
            "LEFT JOIN TimeUnit time ON occupation.timeUnit.id = time.id " +
            "WHERE occupation.timeUnit.dateTime = :dateTime " +
            "AND occupation.occupied = true " +
            "AND slot.floorNumber = :floor"
    )
    List<String> getOccupiedSlots(@Param("dateTime") LocalDateTime dateTime, @Param("floor") Integer floor);

    //Adnotacja @Query wysyła zapytanie w języku hql do bazy dancyh
    //Metoda ta wyciąga obiekty typu OccupationTimeDto, gdzie parametry w tych obiektach to:
    //nazwa miejsca parkingowego, licznik ile razy było zajęte i numer piętra
    //na poddstawie podanego piętra i faktu czy dane miejsce było zajęte
    @Query(
            "SELECT new com.myprojects.carpark.domain.dto.OccupationTimeDTO(s.name, count(s), s.floorNumber) FROM Occupation o " +
            "LEFT JOIN Slot s ON s.id = o.slot.id " +
            "WHERE s.floorNumber = :floor " +
            "AND o.occupied = true " +
            "GROUP BY s.name"
    )
    List<OccupationTimeDTO> selectSpotsAndCountOccupiedTime(@Param("floor") Integer floor);

    //Adnotacja @Query wysyła zapytanie w języku hql do bazy dancyh
    //Metoda ta wyciąga obiekty typu OccupationTimeDto, gdzie parametry w tych obiektach to:
    //nazwa miejsca parkingowego, licznik ile razy było zajęte i numer piętra
    //na poddstawie podanego piętra, faktu czy dane miejsce było zajęte i okresu czasu w jakim było zajęte
    @Query(
            "SELECT new com.myprojects.carpark.domain.dto.OccupationTimeDTO(s.name, count(s), s.floorNumber) FROM Occupation o " +
            "LEFT JOIN Slot s ON s.id = o.slot.id " +
            "LEFT JOIN TimeUnit t ON t.id = o.timeUnit.id " +
            "WHERE s.floorNumber = :floor " +
            "AND o.occupied = true " +
            "AND t.dateTime > :start " +
            "AND t.dateTime <= :end " +
            "GROUP BY s.name"
    )
    List<OccupationTimeDTO> selectSpotsAndCountOccupiedTime(
            @Param("floor") Integer floor, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end
    );

    //Adnotacja @Query wysyła zapytanie w języku hql do bazy dancyh
    //Metoda ta wyciąga obiekty typu OccupationTimeDto, gdzie parametry w tych obiektach to:
    //nazwa miejsca parkingowego, licznik ile razy było zajęte i numer piętra
    //na poddstawie podanego miejsca, faktu czy dane miejsce było zajęte i okresu czasu w jakim było zajęte
    @Query(
            "SELECT new com.myprojects.carpark.domain.dto.OccupationTimeDTO(s.name, count(s), s.floorNumber) FROM Occupation o " +
            "LEFT JOIN Slot s ON s.id = o.slot.id " +
            "LEFT JOIN TimeUnit t ON t.id = o.timeUnit.id " +
            "WHERE s.name = :spot " +
            "AND o.occupied = true " +
            "AND t.dateTime > :start " +
            "AND t.dateTime <= :end " +
            "GROUP BY s.name"
    )
    List<OccupationTimeDTO> selectSpotAndCountOccupiedTime(
            @Param("spot") String spot,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    //Adnotacja @Query wysyła zapytanie w języku hql do bazy dancyh
    //Metoda ta wyciąga obiekty typu OccupationTimeDto, gdzie parametry w tych obiektach to:
    //nazwa miejsca parkingowego, licznik ile razy było zajęte i numer piętra
    //na poddstawie podanego miejsca i faktu czy dane miejsce było zajęte
    @Query(
            "SELECT new com.myprojects.carpark.domain.dto.OccupationTimeDTO(s.name, count(s), s.floorNumber) FROM Occupation o " +
            "LEFT JOIN Slot s ON s.id = o.slot.id " +
            "WHERE s.name = :spot " +
            "AND o.occupied = true " +
            "GROUP BY s.name"
    )
    List<OccupationTimeDTO> selectSpotAndCountOccupiedTime(
            @Param("spot") String spot
    );

    //Adnotacja @Query wysyła zapytanie w języku hql do bazy dancyh
    //Metoda ta wyciąga obiekty typu OccupationTimeDto, gdzie parametry w tych obiektach to:
    //nazwa miejsca parkingowego, licznik ile razy było zajęte i numer piętra
    //na poddstawie podanego miejsca i faktu czy dane miejsce było zajęte
    @Query(
            "SELECT DISTINCT new com.myprojects.carpark.domain.dto.OccupationTimeDTO(s.name, count(s), s.floorNumber) FROM Occupation o " +
            "LEFT JOIN Slot s ON s.id = o.slot.id " +
            "WHERE o.occupied = true " +
            "GROUP BY s.name"
    )
    List<OccupationTimeDTO> selectAllParkingSpotsAndCountOccupiedTime();

    //Adnotacja @Query wysyła zapytanie w języku hql do bazy dancyh
    //Metoda ta wyciąga obiekty typu OccupationTimeDto, gdzie parametry w tych obiektach to:
    //nazwa miejsca parkingowego, licznik ile razy było zajęte i numer piętra
    //na poddstawiefaktu czy dane miejsce było zajęte i okresu czasu
    @Query(
            "SELECT new com.myprojects.carpark.domain.dto.OccupationTimeDTO(s.name, count(s), s.floorNumber) FROM Occupation o " +
            "LEFT JOIN Slot s ON s.id = o.slot.id " +
            "LEFT JOIN TimeUnit t ON t.id = o.timeUnit.id " +
            "WHERE o.occupied = true " +
            "AND t.dateTime > :start " +
            "AND t.dateTime <= :end " +
            "GROUP BY s.name"
    )
    List<OccupationTimeDTO> selectAllParkingSpotsAndCountOccupiedTime(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}
