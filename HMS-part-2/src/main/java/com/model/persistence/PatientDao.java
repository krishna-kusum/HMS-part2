package com.model.persistence;

import java.util.List;

import com.bean.Patient;

public interface PatientDao {


	List<Patient> getPatientList();
	
	boolean addPatient(Patient patient);

	boolean removePatient(String patientId);

	Patient getPatientById(String patientId);

	int getLastPId();
		
}