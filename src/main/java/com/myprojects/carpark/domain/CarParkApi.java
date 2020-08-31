package com.myprojects.carpark.domain;

import com.myprojects.carpark.domain.dto.SlotDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarParkApi {
    CarParkService carParkService;

    @Transactional
    public List<SlotDto> getGeneratedSlots(Integer numberOfFloors, Integer numberOfSlots) {
        return carParkService.generateSlots(numberOfFloors, numberOfSlots);
    }
}
