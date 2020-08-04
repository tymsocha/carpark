package com.myprojects.carpark.domain;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class CarParkMap {
    private List<CarFloorMap> carPark;
}
