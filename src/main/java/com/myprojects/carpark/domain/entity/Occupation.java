package com.myprojects.carpark.domain.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

//Encja reprezentująca tabelę w bazie danych
//@Entity - Adnotacja mówiąca aplikacji że jest to encja na podstawie tabeli w bazie danych
//@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
//Adnotacje biblioteki Lombok tworzące gettery, settery, wzorzec projektowy budowniczy, bezargumentowy konstruktor i konstruktor ze wszystkimi polami klasy
//@FieldDefaults(level = AccessLevel.PRIVATE) - nadaje każdemu polu w klasie prywatny modyfikator dostępu
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Occupation {
    //@ID - adnotacja, która określa dane pole jako ID w bazie danych
    //@GeneratedValue - adnotacja, która nadaje automatycznie wartość pola ID za daną kolejnością
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    boolean occupied;

    boolean isClosed;

    //@ManyToOne - adnotacja świadcząca o relacji z obiektem typu Slot
    //@JoinColumn - adnotacje mówiąca o nazwie kolumny, w której są przechowywane informacje na temat relacji między encjami
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "slot_id", nullable = false)
    Slot slot;

    //@ManyToOne - adnotacja świadcząca o relacji z obiektem typu TimeUnit
    //@JoinColumn - adnotacje mówiąca o nazwie kolumny, w której są przechowywane informacje na temat relacji między encjami
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time_unit_id", nullable = false)
    TimeUnit timeUnit;
}
