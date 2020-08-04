package com.myprojects.carpark.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarFloor {
    private String name;
    private List<CarSpot> spots;
}
