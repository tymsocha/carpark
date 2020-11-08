package com.myprojects.carpark.domain.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClosedFloor {
    //@ID - adnotacja, która określa dane pole jako ID w bazie danych
    //@GeneratedValue - adnotacja, która nadaje automatycznie wartość pola ID za daną kolejnością
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Integer floor;

    LocalDateTime startDate;

    LocalDateTime endDate;

    boolean active;
}
