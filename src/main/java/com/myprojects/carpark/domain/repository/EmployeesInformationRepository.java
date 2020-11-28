package com.myprojects.carpark.domain.repository;

import com.myprojects.carpark.domain.entity.EmployeesInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeesInformationRepository extends JpaRepository<EmployeesInformation, Long> {
}
