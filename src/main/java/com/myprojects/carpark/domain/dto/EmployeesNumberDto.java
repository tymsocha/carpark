package com.myprojects.carpark.domain.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeesNumberDto {
    Integer numberOfEmployeesPerFloor;
    Long salaryOfOneEmployeePerDay;
    Integer numberOfAllEmployees;
    Long totalPriceToPayPerDay;
}
