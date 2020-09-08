package com.myprojects.carpark.domain.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarParkEnergyDto {
    Long totalEnergyConsumption;
    Long totalEnergyCost;
    List<FloorEnergyDto> floors;
}
