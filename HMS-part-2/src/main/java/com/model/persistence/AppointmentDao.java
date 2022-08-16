package com.model.persistence;

import java.sql.Date;
import java.util.List;

import com.bean.Appointment;

public interface AppointmentDao {

	void appointment(String patient_id, String doc_id, Date new_date);
	
	String checkSlot(String slot_to_check, String d_Id, Date date);
	
	void storeAppointment(String p_id, String p_name, String new_slot, Date date, String d_id, String d_name, String dept);

	boolean cancelAppointment(int aid);

	List<Appointment> getAllAppointments(String id, int choice);
}
