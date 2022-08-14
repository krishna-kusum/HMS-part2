package com.model.persistence.helper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bean.Doctor;

public class DoctorRowMapper implements RowMapper<Doctor> {

	@Override
	public Doctor mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		String id = resultSet.getString("doctor_id");
		String name = resultSet.getString("name_of_doctor");
		String specialisation=resultSet.getString("specialisation");
		int experience =  resultSet.getInt("experience");
		String gender = resultSet.getString("gender");
		int age = resultSet.getInt("age");
		String contact_number = resultSet.getString("Contact_number");
		String address = resultSet.getString("Address");
		Doctor doctor = new Doctor(id,name,specialisation,experience,gender,age,contact_number,address);
		return doctor;
	}

}