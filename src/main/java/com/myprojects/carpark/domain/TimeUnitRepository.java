package com.myprojects.carpark.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeUnitRepository extends JpaRepository<TimeUnit, Long> {
}
