package com.myprojects.carpark.domain;

import com.myprojects.carpark.domain.dto.EnergyConsumptionDto;
import com.myprojects.carpark.domain.dto.GenerationTimeDto;
import com.myprojects.carpark.domain.dto.OccupationTimeDTO;
import com.myprojects.carpark.domain.dto.SlotDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
class Mapper {
    private SlotDto mapToSlotDto(Slot slot) {
        return SlotDto.builder()
                .id(slot.getId())
                .name(slot.getName())
                .floorNumber(slot.getFloorNumber())
                .build();
    }

    private List<SlotDto> mapToSlotDtoList(List<Slot> slotList) {
        return slotList.stream()
                .map(this::mapToSlotDto)
                .collect(Collectors.toList());
    }

    GenerationTimeDto mapToGenerationTimeDto(List<Slot> slotList, Long time) {
        return GenerationTimeDto.builder()
                .slotDtos(mapToSlotDtoList(slotList))
                .timeOfGeneration(time)
                .build();
    }

    List<EnergyConsumptionDto> mapToEnergyConsumptionListDto(List<OccupationTimeDTO> timeDTOS, Long energyConsumption, Long energyCost) {
        List<EnergyConsumptionDto> energyConsumptionDtos = new ArrayList<>();

        for (OccupationTimeDTO timeDTO : timeDTOS) {
            energyConsumptionDtos.add(
                    EnergyConsumptionDto.builder()
                    .spot(timeDTO.getSlotName())
                    .occupiedTime(timeDTO.getOccupiedTime() / 2)
                    .energyConsumption(timeDTO.getOccupiedTime() / 2 * energyConsumption)
                    .energyCost(timeDTO.getOccupiedTime() / 2 * energyConsumption * energyCost)
                    .build()
            );
        }

        return energyConsumptionDtos;
    }
}
