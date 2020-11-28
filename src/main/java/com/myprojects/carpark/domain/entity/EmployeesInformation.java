package com.myprojects.carpark.domain.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeesInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Integer numberOfEmployeesPerFloor;

    Double salaryOfOneEmployeePerDay;

    Integer numberOfAllEmployees;

    Double totalPriceToPayPerDay;

    Integer spotsToService;

    Double salaryPerHour;
}
