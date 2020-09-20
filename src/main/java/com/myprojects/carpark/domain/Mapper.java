package com.myprojects.carpark.domain;

import com.myprojects.carpark.domain.dto.EnergyConsumptionDto;
import com.myprojects.carpark.domain.dto.GenerationTimeDto;
import com.myprojects.carpark.domain.dto.OccupationTimeDTO;
import com.myprojects.carpark.domain.dto.SlotDto;
import org.springframework.stereotype.Component;

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
    List<EnergyConsumptionDto> mapToEnergyConsumptionListDto(List<OccupationTimeDTO> timeDTOS, Long energyConsumption, Long energyCost) {
        List<EnergyConsumptionDto> energyConsumptionDtos = new ArrayList<>();

        for (OccupationTimeDTO timeDTO : timeDTOS) {
            energyConsumptionDtos.add(
                    EnergyConsumptionDto.builder()
                    .spot(timeDTO.getSlotName())
                    .occupiedTime(timeDTO.getOccupiedTime())
                    .energyConsumption(timeDTO.getOccupiedTime() * energyConsumption)
                    .energyCost(timeDTO.getOccupiedTime() * energyConsumption * energyCost)
                    .build()
            );
        }

        return energyConsumptionDtos;
    }
}
