package com.model.persistence.helper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import org.springframework.jdbc.core.RowMapper;

import com.bean.Appointment;

public class AppointmentRowMapper implements RowMapper<Appointment> {

	@Override
	public Appointment mapRow(ResultSet resultSet, int rowNum) throws SQLException {

		String p_id = resultSet.getString("patient_id");
		String p_name = resultSet.getString("name_of_patient");
		Time slot = resultSet.getTime("slot");
		Date date_of_appointment = resultSet.getDate("date_of_appointment");
		String d_id = resultSet.getString("doctor_id");
		String d_name = resultSet.getString("name_of_doctor");
		String dept = resultSet.getString("department");
		
		Appointment appointment = new Appointment(p_id,p_name,slot,date_of_appointment,d_id,d_name,dept);
		return appointment;
	}

}
