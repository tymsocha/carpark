package com.myprojects.carpark.domain.dto.exceldtos;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DatabaseDto {
    Long slotId;
    String slotName;
    int slotFloorNumber;

    Long timeUnitId;
    LocalDateTime dateTime;

    Long occupationId;
    boolean occupied;
    boolean isClosed;

    public DatabaseDto(Long slotId, String slotName, int slotFloorNumber, Long timeUnitId,
                       LocalDateTime dateTime, Long occupationId, boolean occupied, boolean isClosed) {
        this.slotId = slotId;
        this.slotName = slotName;
        this.slotFloorNumber = slotFloorNumber;
        this.timeUnitId = timeUnitId;
        this.dateTime = dateTime;
        this.occupationId = occupationId;
        this.occupied = occupied;
        this.isClosed = isClosed;
    }
}
