package com.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bean.Doctor;
import com.bean.Patient;
import com.model.persistence.DoctorDaoImpl;
import com.model.persistence.LoginDaoImpl;
import com.model.persistence.PatientDaoImpl;

@Service
public class ValidateUserServiceImpl implements ValidateUserService {

	private LoginDaoImpl loginDaoImpl;
	private PatientDaoImpl patientDaoImpl;
	private DoctorDaoImpl doctorDaoImpl ;
	
	@Autowired
	public void setLoginDaoImpl(LoginDaoImpl loginDaoImpl) {
		this.loginDaoImpl = loginDaoImpl;
	}

	@Autowired
	public void setPatientDaoImpl(PatientDaoImpl patientDaoImpl) {
		this.patientDaoImpl = patientDaoImpl;
	}

	@Autowired
	public void setDoctorDaoImpl(DoctorDaoImpl doctorDaoImpl) {
		this.doctorDaoImpl = doctorDaoImpl;
	}

	
	
	@Override
	public boolean registerPatient(Patient patient) {
		return patientDaoImpl.addPatient(patient);
	}

	@Override
	public boolean registerDoctor(Doctor doctor) {
		return doctorDaoImpl.addDoctor(doctor);
	}

	@Override
	public boolean isPatient(String id, String Password) {
		return loginDaoImpl.validate(id, Password);
	}

	@Override
	public boolean isDoctor(String id, String Password) {
		return loginDaoImpl.validate(id, Password);
	}

	@Override
	public boolean isAdmin(String id, String Password) {
		return loginDaoImpl.validate(id, Password);
	}

	@Override
	public boolean registerUser(String id, String Password) {
		return loginDaoImpl.registerUser(id, Password);
	}
}
