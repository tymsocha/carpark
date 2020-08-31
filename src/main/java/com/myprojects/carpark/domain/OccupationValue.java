package com.myprojects.carpark.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
class OccupationValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    boolean occupied;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "slot_id", nullable = false)
    Slot slot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time_unit_id", nullable = false)
    TimeUnit timeUnit;
}
