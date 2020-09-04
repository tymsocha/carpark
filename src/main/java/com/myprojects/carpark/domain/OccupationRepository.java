package com.myprojects.carpark.domain;

import com.myprojects.carpark.domain.dto.OccupationTimeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OccupationRepository extends JpaRepository<Occupation, Long> {

    @Query(
            "SELECT DISTINCT slot.name FROM Slot slot " +
            "LEFT JOIN Occupation occupation ON occupation.slot.id = slot.id " +
            "LEFT JOIN TimeUnit time ON occupation.timeUnit.id = time.id " +
            "WHERE occupation.occupied = true " +
            "AND occupation.timeUnit.dateTime = :dateTime"
    )
    List<String> getOccupiedSlots(@Param("dateTime") LocalDateTime dateTime);

    @Query(
            "SELECT DISTINCT slot.name FROM Slot slot " +
            "LEFT JOIN Occupation occupation ON occupation.slot.id = slot.id " +
            "LEFT JOIN TimeUnit time ON occupation.timeUnit.id = time.id " +
            "WHERE occupation.timeUnit.dateTime = :dateTime " +
            "AND occupation.occupied = true " +
            "AND slot.floorNumber = :floor"
    )
    List<String> getOccupiedSlots(@Param("dateTime") LocalDateTime dateTime, @Param("floor") Integer floor);

    @Query(
            "SELECT new com.myprojects.carpark.domain.dto.OccupationTimeDTO(s.name, count(s)) FROM Occupation o " +
            "LEFT JOIN Slot s ON s.id = o.slot.id " +
            "WHERE s.floorNumber = :floor " +
            "AND o.occupied = true " +
            "GROUP BY s.name"
    )
    List<OccupationTimeDTO> selectSpotAndCountOccupiedCount(@Param("floor") Integer floor);

    @Query(
            "SELECT new com.myprojects.carpark.domain.dto.OccupationTimeDTO(s.name, count(s)) FROM Occupation o " +
            "LEFT JOIN Slot s ON s.id = o.slot.id " +
            "LEFT JOIN TimeUnit t ON t.id = o.timeUnit.id " +
            "WHERE s.floorNumber = :floor " +
            "AND o.occupied = true " +
            "AND t.dateTime > :start " +
            "AND t.dateTime <= :end " +
            "GROUP BY s.name"
    )
    List<OccupationTimeDTO> selectSpotAndCountOccupiedCount(
            @Param("floor") Integer floor, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end
    );
}
