package com.myprojects.carpark.domain.repository;

import com.myprojects.carpark.domain.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Interfejs służący do połączenia z bazą danych i dzięki temu, że dziedziczy po Interfejsie JpaRepository, dziedziczy wszystkie jego metody
//@Repository - adnotacja mówiąca springowi, aby stworzył beana służącego do połączenia z bazą danych, na podstawie tego interfejsu.
@Repository
public interface SlotRepository extends JpaRepository<Slot, Long> {
}
