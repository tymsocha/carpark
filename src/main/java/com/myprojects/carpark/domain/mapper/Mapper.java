package com.myprojects.carpark.domain.mapper;

import com.myprojects.carpark.domain.dto.*;
import com.myprojects.carpark.domain.entity.ClosedFloor;
import com.myprojects.carpark.domain.entity.Slot;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//Klasa mapująca obiekty na inne
//@Component - adnotacja mówiąca springowi, aby utworzył beana typu danej klasy
@Component
public class Mapper {
    //Metoda mapująca obiekt typu Slot na obiekt SlotDto
    private SlotDto mapToSlotDto(Slot slot) {
        return SlotDto.builder()
                .id(slot.getId())
                .name(slot.getName())
                .floorNumber(slot.getFloorNumber())
                .build();
    }

    //Metoda mapująca Listę obiektów typu Slot na Listę obiektów typu SlotDto
    private List<SlotDto> mapToSlotDtoList(List<Slot> slotList) {
        return slotList.stream()
                .map(this::mapToSlotDto)
                .collect(Collectors.toList());
    }

    //Metoda mapująca Listę obiektów typu slot i czas w milisekundach na obiekt typu GenerationTimeDto
    public GenerationTimeDto mapToGenerationTimeDto(List<Slot> slotList, Long time) {
        return GenerationTimeDto.builder()
                .slotDtos(mapToSlotDtoList(slotList))
                .timeOfGeneration(time)
                .build();
    }

    //Metoda mapująca obiekty: Liste obiektów typu OccupationTimeDto, wartość energii i jej kosztu jednostkowego na listę obiektów EnergyConsumptionDto
    public List<EnergyConsumptionDto> mapToEnergyConsumptionListDto(List<OccupationTimeDTO> timeDTOS, Double energyConsumption, Double energyCost) {
        List<EnergyConsumptionDto> energyConsumptionDtos = new ArrayList<>();

        for (OccupationTimeDTO timeDTO : timeDTOS) {
            energyConsumptionDtos.add(
                    EnergyConsumptionDto.builder()
                    .spot(timeDTO.getSlotName())
                    .occupiedTime(timeDTO.getOccupiedTime().doubleValue())
                    .energyConsumption(roundDouble(timeDTO.getOccupiedTime() * energyConsumption))
                    .energyCost(roundDouble(timeDTO.getOccupiedTime() * energyConsumption * energyCost))
                    .build()
            );
        }

        return energyConsumptionDtos;
    }

    public static Double roundDouble(Double number) {
        BigDecimal bigDecimal = new BigDecimal(Double.toString(number));
        bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

    public ClosedFloorDto mapClosedFloorToClosedFloorDto(ClosedFloor closedFloor) {
        String startDate = closedFloor.getStartDate().toLocalDate().toString();
        String startTime = closedFloor.getStartDate().toLocalTime().toString();
        String endDate = closedFloor.getEndDate().toLocalDate().toString();
        String endTime = closedFloor.getEndDate().toLocalTime().toString();

        return ClosedFloorDto.builder()
                .floor(closedFloor.getFloor())
                .startDateString(startDate)
                .startHourString(startTime)
                .endDateString(endDate)
                .endHourString(endTime)
                .build();
    }
}
