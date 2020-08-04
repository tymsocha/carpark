package com.myprojects.carpark.domain;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeUnit {
    LocalDate date;
    LocalTime time;
}
