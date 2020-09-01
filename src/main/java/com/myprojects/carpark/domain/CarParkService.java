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
        Long start = System.currentTimeMillis();
        List<Slot> generatedSlots = carSpotsGenerator.generateCarParkMap(floorsNumber, spotsNumber);
        carSpotsGenerator.generateTimeUnitsThrough30Days();
        carSpotsGenerator.randomlySetOccupiedSlotsThrough30Days();
        Long end = System.currentTimeMillis();
        System.out.println("*******************************" + (end - start));
        return mapper.mapToSlotDtoList(generatedSlots);
    }
}
