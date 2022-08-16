package com.model.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bean.Appointment;
import com.bean.Patient;
import com.bean.Schedule;
import com.model.persistence.AppointmentDaoImpl;
import com.model.persistence.DoctorDaoImpl;
import com.model.persistence.PatientDaoImpl;
import com.model.persistence.ScheduleDaoImpl;

@Service
public class DoctorServiceImpl implements DoctorService {

	PatientDaoImpl patientDaoImpl;
	ScheduleDaoImpl scheduleDaoImpl;
	DoctorDaoImpl doctorDaoImpl;
	AppointmentDaoImpl appointmentDaoImpl;
	
	@Autowired
	public void setPatientDaoImpl(PatientDaoImpl patientDaoImpl) {
		this.patientDaoImpl = patientDaoImpl;
	}

	@Autowired
	public void setScheduleDaoImpl(ScheduleDaoImpl scheduleDaoImpl) {
		this.scheduleDaoImpl = scheduleDaoImpl;
	}

	@Autowired
	public void setDoctorDaoImpl(DoctorDaoImpl doctorDaoImpl) {
		this.doctorDaoImpl = doctorDaoImpl;
	}

	@Autowired
	public void setAppointmentDaoImpl(AppointmentDaoImpl appointmentDaoImpl) {
		this.appointmentDaoImpl = appointmentDaoImpl;
	}

	@Override
	public Patient getPatientProfile(String patientId) {
		return patientDaoImpl.getPatientById(patientId);		
	}

	@Override
	public boolean updatePatientProfile(String doctorId, String patientId, Map<String, String> editList) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Schedule getDoctorSchedule(String doctorId) {
		return scheduleDaoImpl.getDoctorSchedule(doctorId);
	}

	@Override
	public boolean updateDoctorSchedule(String doctorId, Schedule schedule) {
		if(scheduleDaoImpl.removeDoctorSchedule(doctorId))
			return scheduleDaoImpl.addDoctorSchedule(schedule);
		return false;
	}

	@Override
	public List<Schedule> getAvailableDoctors(Date date) {
		return doctorDaoImpl.getAvailableDoctors(date);
	}

	@Override
	public void displayAvailableDoctors(Date date) {
		List<Schedule> doctors = getAvailableDoctors(date);
		if (doctors == null){
			System.out.println("No Doctor Available");
		} 
		else {
			for(Schedule doc: doctors) {
				System.out.println(doc.toString());
			}
		}
	}
	
	@Override
	public List<Appointment> getMyAppointments(String id, int choice) {
		return appointmentDaoImpl.getAllAppointments(id, choice);
	}

}
