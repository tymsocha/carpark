package com.myprojects.carpark.domain;

import com.myprojects.carpark.domain.dto.SlotDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
class Mapper {
    SlotDto mapToSlotDto(Slot slot) {
        return SlotDto.builder()
                .id(slot.getId())
                .name(slot.getName())
                .floorNumber(slot.getFloorNumber())
                .build();
    }

    List<SlotDto> mapToSlotDtoList(List<Slot> slotList) {
        return slotList.stream()
                .map(this::mapToSlotDto)
                .collect(Collectors.toList());
    }
}
