package com.myprojects.carpark.domain.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeesNumberDto {
    Map<Integer, Integer> mapOfFloorsAndEmployees;
    Integer numberOfAllEmployees;
    Long totalPriceToPay;
}
