package com.myprojects.carpark.domain.repository;

import com.myprojects.carpark.domain.entity.ClosedFloor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClosedFloorRepository extends JpaRepository<ClosedFloor, Long> {

}
