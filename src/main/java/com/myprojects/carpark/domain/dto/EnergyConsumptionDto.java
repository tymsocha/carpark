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
public class EnergyConsumptionDto {
    String spot;
    Long occupiedTime;
    Long energyConsumption;
    Long energyCost;
}
