package com.model.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.bean.Appointment;
import com.bean.Patient;
import com.bean.Schedule;

public interface DoctorService {
	
	boolean updatePatientProfile(String doctorId, String patientId, Map<String,String> editList);
	
	List<Schedule> getAvailableDoctors(Date date);

	Schedule getDoctorSchedule(String doctorId);

	Patient getPatientProfile(String patientId);

	void displayAvailableDoctors(Date date);

	boolean updateDoctorSchedule(String doctorId, Schedule schedule);

	List<Appointment> getMyAppointments(String pid, int choice);
}
