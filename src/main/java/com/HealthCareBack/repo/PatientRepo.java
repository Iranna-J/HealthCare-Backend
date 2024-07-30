package com.HealthCareBack.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.HealthCareBack.dto.Patient;
@Repository
@EnableJpaRepositories
public interface PatientRepo extends JpaRepository<Patient, Long> {

}
