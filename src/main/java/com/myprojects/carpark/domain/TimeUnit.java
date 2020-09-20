package com.myprojects.carpark.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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
class TimeUnit {
    //@ID - adnotacja, która określa dane pole jako ID w bazie danych
    //@GeneratedValue - adnotacja, która nadaje automatycznie wartość pola ID za daną kolejnością
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    LocalDateTime dateTime;

    //@OneToMany - adnotacja świadcząca o relacji z obiektem typu Occupation
    @OneToMany(mappedBy = "timeUnit")
    List<Occupation> occupations;

}
