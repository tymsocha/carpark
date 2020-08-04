package com.myprojects.carpark.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarSpot {
    private String name;
    private boolean isOccupied;
}
