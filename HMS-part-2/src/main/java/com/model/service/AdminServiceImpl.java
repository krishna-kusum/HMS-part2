package com.model.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bean.Doctor;
import com.bean.Schedule;
import com.model.persistence.DoctorDaoImpl;
import com.model.persistence.PatientDaoImpl;
@Service
public class AdminServiceImpl implements AdminService {

	DoctorDaoImpl doctorDaoImpl;
	PatientDaoImpl patientDaoImpl;

	@Autowired
	public void setDoctorDaoImpl(DoctorDaoImpl doctorDaoImpl) {
		this.doctorDaoImpl = doctorDaoImpl;
	}

	@Autowired
	public void setPatientDaoImpl(PatientDaoImpl patientDaoImpl) {
		this.patientDaoImpl = patientDaoImpl;
	}

	
	@Override
	public boolean generateAppointment(String patientId) {
		return false;
	}

	@Override
	public boolean cancelAppointment(String patientId) {
		return false;
	}

	@Override
	public boolean registerDoctorToDatabase(Doctor doctor) {
		return doctorDaoImpl.addDoctor(doctor);
	}

	@Override
	public boolean removeDoctorFromDatabase(String doctorID) {
		return doctorDaoImpl.removeDoctor(doctorID);
	}

	@Override
	public List<Schedule> getAvailableDoctors(Date date) {
		return doctorDaoImpl.getAvailableDoctors(date);
	}

	
}
