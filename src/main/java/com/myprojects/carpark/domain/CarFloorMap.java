package com.myprojects.carpark.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarFloorMap {
    private TimeUnit dateTime;
    private List<CarFloor> carFloors;
}
