package com.model.persistence;

import java.sql.Date;
import java.util.List;

import com.bean.Doctor;
import com.bean.Schedule;

public interface DoctorDao {

	
	String searchDoctorId(String doctorName);
	
	List<Doctor> getDoctorList();
	
	boolean addDoctor(Doctor doctor); //return type changed
	
	boolean removeDoctor(String doctorId);
	
	List<Schedule> getAvailableDoctors(Date date);

//	String getEmergencyContact(String doctorId);

	Doctor getDoctorDetails(String doctorId);

	int getLastDId();
}
