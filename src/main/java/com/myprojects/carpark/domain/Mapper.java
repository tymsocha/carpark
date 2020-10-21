package com.myprojects.carpark.domain;

import com.myprojects.carpark.domain.dto.EnergyConsumptionDto;
import com.myprojects.carpark.domain.dto.GenerationTimeDto;
import com.myprojects.carpark.domain.dto.OccupationTimeDTO;
import com.myprojects.carpark.domain.dto.SlotDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//Klasa mapująca obiekty na inne
//@Component - adnotacja mówiąca springowi, aby utworzył beana typu danej klasy
@Component
class Mapper {
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
    GenerationTimeDto mapToGenerationTimeDto(List<Slot> slotList, Long time) {
        return GenerationTimeDto.builder()
                .slotDtos(mapToSlotDtoList(slotList))
                .timeOfGeneration(time)
                .build();
    }

    //Metoda mapująca obiekty: Liste obiektów typu OccupationTimeDto, wartość energii i jej kosztu jednostkowego na listę obiektów EnergyConsumptionDto
    List<EnergyConsumptionDto> mapToEnergyConsumptionListDto(List<OccupationTimeDTO> timeDTOS, Double energyConsumption, Double energyCost) {
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
}
