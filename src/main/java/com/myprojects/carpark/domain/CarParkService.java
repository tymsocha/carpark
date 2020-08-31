package com.myprojects.carpark.domain;

import com.myprojects.carpark.domain.dto.SlotDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarParkService {

    CarSpotsGenerator carSpotsGenerator;
    Mapper mapper;

    List<SlotDto> generateSlots(Integer floorsNumber, Integer spotsNumber) {
        List<Slot> generatedSlots = carSpotsGenerator.generateCarParkMap(floorsNumber, spotsNumber);
        return mapper.mapToSlotDtoList(generatedSlots);
    }
}
