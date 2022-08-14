package com.model.persistence.helper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bean.Patient;

public class PatientRowMapper implements RowMapper<Patient> {

	@Override
	public Patient mapRow(ResultSet resultSet, int rowNum) throws SQLException {

		String id = resultSet.getString("patient_id");
		String name = resultSet.getString("name_of_patient");
		String gender = resultSet.getString("gender");
		int age = resultSet.getInt("age");
		String contact = resultSet.getString("Contact_number");
		String address = resultSet.getString("Address");
		String dept = resultSet.getString("department");
		
		Patient patient = new Patient(id,name,age,gender,contact,dept,address);
		
		return patient;
	}

}
