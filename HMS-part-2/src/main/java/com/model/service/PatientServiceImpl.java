package com.model.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bean.Patient;
import com.model.persistence.AppointmentDaoImpl;
import com.model.persistence.PatientDaoImpl;


@Service
public class PatientServiceImpl implements PatientService {

	AppointmentDaoImpl appointmentDaoImpl;
	PatientDaoImpl patientDaoImpl;
	
	@Autowired
	public void setAppointmentDaoImpl(AppointmentDaoImpl appointmentDaoImpl) {
		this.appointmentDaoImpl = appointmentDaoImpl;
	}

	@Autowired
	public void setPatientDaoImpl(PatientDaoImpl patientDaoImpl) {
		this.patientDaoImpl = patientDaoImpl;
	}

	@Override
	public boolean rescheduleAppointment(int aid, Date newDate) {
		return appointmentDaoImpl.reschedule(aid, newDate);
	}

	@Override
	public void getPatientProfile(String id) {
		Patient patient = patientDaoImpl.getPatientById(id);
		System.out.println(patient.toString());
	}

	@Override
	public int getLastPatientId() {
		return patientDaoImpl.getLastPId();
	}
	
	@Override
	public List<String> getMyAppointments(String pid, int choice) {
		return appointmentDaoImpl.getAllAppointments(pid, choice);
	}

	@Override
	public void requestAppointment(String id, String doc_id, Date date) {
		appointmentDaoImpl.appointment(id, doc_id, date);
	}

	@Override
	public boolean cancelAppointmentRequest(int nextInt) {
		return appointmentDaoImpl.cancelAppointment(nextInt);
	}


}
