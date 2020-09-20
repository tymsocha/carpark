package com.myprojects.carpark.domain.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

//Klasa typu Data Transfer Object (DTO) czyli obiekt transferu danych
//Jest ona przekazywana i wysyłana w kontrolerze do aplikacji frontendowej
//@Builder @Getter @Setter - Są to adnotacje biblioteki Lombok
//Tworzą one gettery, settery i wzorzec projektowy budowniczy, bez pisania kodu
//@FieldDefaults(level = AccessLevel.PRIVATE) - nadaje każdemu polu w klasie prywatny modyfikator dostępu
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OccupationTimeDTO {
    String slotName;
    Long occupiedTime;
    int floor;

    public OccupationTimeDTO(String slotName, Long occupiedTime, int floor) {
        this.slotName = slotName;
        this.occupiedTime = occupiedTime;
        this.floor = floor;
    }
}
