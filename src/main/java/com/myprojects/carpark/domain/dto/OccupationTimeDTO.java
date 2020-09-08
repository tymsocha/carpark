package com.myprojects.carpark.domain.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OccupationTimeDTO {
    String slotName;
    Long occupiedTime;
    int floor;

    public OccupationTimeDTO(String slotName, Long occupiedTime, int floor) {
        this.slotName = slotName;
        this.occupiedTime = occupiedTime;
        this.floor = floor;
    }
}
