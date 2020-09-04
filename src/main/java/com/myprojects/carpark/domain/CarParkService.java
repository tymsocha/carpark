package com.myprojects.carpark.domain;

import com.myprojects.carpark.domain.dto.OccupationTimeDTO;
import com.myprojects.carpark.domain.dto.SlotDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarParkService {

    CarSpotsGenerator carSpotsGenerator;
    SlotRepository slotRepository;
    TimeUnitRepository timeUnitRepository;
    OccupationRepository occupationRepository;
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

    List<String> getOccupiedSlots(String timeString, Integer floor) {
        List<String> occupiedSlots;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(timeString, formatter);

        if (floor == null) {
            occupiedSlots = occupationRepository.getOccupiedSlots(dateTime);
        } else {
            occupiedSlots = occupationRepository.getOccupiedSlots(dateTime, floor);
        }

        return occupiedSlots;
    }

    List<OccupationTimeDTO> getAmountOfOccupationTimePerSlot(Integer floor, String startDateString, String endDateString) {
        if (startDateString == null || endDateString == null) {
            List<OccupationTimeDTO> dtos = occupationRepository.selectSpotAndCountOccupiedCount(floor);
            dtos.forEach(dto -> dto.setOccupiedTime(dto.getOccupiedTime() / 2));
            return dtos;
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime startDate = LocalDateTime.parse(startDateString, formatter);
            LocalDateTime endDate = LocalDateTime.parse(endDateString, formatter);
            List<OccupationTimeDTO> dtos = occupationRepository.selectSpotAndCountOccupiedCount(floor, startDate, endDate);
            dtos.forEach(dto -> dto.setOccupiedTime(dto.getOccupiedTime() / 2));
            return dtos;
        }
    }
}
