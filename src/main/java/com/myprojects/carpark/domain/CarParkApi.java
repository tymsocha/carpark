package com.myprojects.carpark.domain;

import com.myprojects.carpark.domain.dto.OccupationTimeDTO;
import com.myprojects.carpark.domain.dto.SlotDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarParkApi {

    CarParkService carParkService;

    @Transactional
    public List<SlotDto> generateSlots(Integer numberOfFloors, Integer numberOfSlots) {
        return carParkService.generateSlots(numberOfFloors, numberOfSlots);
    }

    @Transactional(readOnly = true)
    public List<String> getOccupiedSlots(String timeString, Integer floor) {
        return carParkService.getOccupiedSlots(timeString, floor);
    }

    public List<OccupationTimeDTO> getAmountOfOccupationTimeForSlot(Integer floor, String startDate, String endDate) {
        return carParkService.getAmountOfOccupationTimePerSlot(floor, startDate, endDate);
    }
}
