package com.model.service;

import java.sql.Date;
import java.util.List;

public interface PatientService {

	void getPatientProfile(String id);

	int getLastPatientId();

	boolean rescheduleAppointment(int aid, Date newDate);

	void requestAppointment(String id, String doc_id, Date date);

	boolean cancelAppointmentRequest(int nextInt);

	List<String> getMyAppointments(String pid, int choice);
}
