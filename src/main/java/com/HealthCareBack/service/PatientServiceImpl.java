package com.HealthCareBack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.HealthCareBack.dto.Patient;
import com.HealthCareBack.repo.PatientRepo;

@Service
public class PatientServiceImpl implements PatientService {
	
	@Autowired
	private PatientRepo patientRepo;

	@Override
	public Patient createPatient(Patient patient) {
		return patientRepo.save(patient);
	}
	
	

}
