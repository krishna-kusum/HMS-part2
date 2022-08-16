package com.model.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bean.Doctor;
import com.bean.Schedule;
import com.model.persistence.helper.DoctorRowMapper;
import com.model.persistence.helper.ScheduleRowMapper;


@Repository
public class DoctorDaoImpl implements DoctorDao {

//	private Connection connection;
//	private PreparedStatement preparedStatement;
//	private Statement statement;
//	private ResultSet resultSet;

	private LoginDaoImpl loginDaoImpl;
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setLoginDaoImpl(LoginDaoImpl loginDaoImpl) {
		this.loginDaoImpl = loginDaoImpl;
	}

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
//	Connection connectDB() throws SQLException {
//		return DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "wiley");
//	}
	
	@Override
	public String searchDoctorId(String doctorName) {
		
		
		String id = null;
		
		try {
//			
			String query="select doctor_id from DOCTOR where name_of_doctor=?";
			Doctor doctor= jdbcTemplate.queryForObject(query,new DoctorRowMapper(),doctorName);
			
			id = doctor.getPersonId();
		} catch (EmptyResultDataAccessException e) {
//			e.printStackTrace();
			return id;
		}
		return id;
	
	}
	

	@Override
	public Doctor getDoctorDetails(String doctorId) {
		
		Doctor doctorDetails = null;
		
		try{
			String query = "select * from DOCTOR where doctor_id=?";
			doctorDetails = jdbcTemplate.queryForObject(query,new DoctorRowMapper(),doctorId);

		} catch (EmptyResultDataAccessException e) {
//			e.printStackTrace();
		}
		return doctorDetails;	
	}

	@Override
	public List<Doctor> getDoctorList() {

		List<Doctor> doctorList = new ArrayList<>();
		
		try{
			String query = "SELECT * FROM PATIENT";
			doctorList =  jdbcTemplate.query(query,new DoctorRowMapper());
			
			
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();

			return null;
		}
		return doctorList;
	}

	
	
//	@Override
//	public String getEmergencyContact(String doctorId) {
//
//		String contact = null;
//		
//		try{
//			
//			this.connection = connectDB();		
//			preparedStatement = connection.prepareStatement("select contact_number from DOCTOR where doctor_id=?");
//			preparedStatement.setString(1, doctorId);
//			resultSet = preparedStatement.executeQuery();
//			
//			while(resultSet.next()) {
//				resultSet.getString(contact);
//			}
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return contact;
//		
//	}

	@Override 
	public List<Schedule> getAvailableDoctors(Date date) { 

		List<Schedule> getAvailableDoctors = new ArrayList<>();
		
		try{
			
			String query = "select * from regular_schedule where available_day = ?";
			Format f = new SimpleDateFormat("EEEE");  
			String day = f.format(date);
			
//			preparedStatement.setString(1, day);
			getAvailableDoctors =  jdbcTemplate.query(query,new ScheduleRowMapper(),day);


		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
		return getAvailableDoctors;
	}
	
	@Override
	public boolean addDoctor(Doctor doctor) {
		
		int rows = 0;
		try{

			doctor.setCounter(getLastDId());
			doctor.setPersonId("D" + (doctor.getCounter()));

			String query = "INSERT INTO DOCTOR values(?,?,?,?,?,?,?,?)";
			rows = jdbcTemplate.update(query,
					doctor.getPersonId(),
					doctor.getName(),
					doctor.getDepartment(),
					doctor.getExperienceInYears(),
					doctor.getGender(),
					doctor.getAge(),
					doctor.getContactNumber(),
					doctor.getAddress());

			
		} catch (EmptyResultDataAccessException e) {
//			e.printStackTrace();
			return false;
		}
		if(rows < 1)
			return false;
		
		if(loginDaoImpl.registerUser(doctor.getPersonId(), doctor.getPersonId(),2))
			System.out.println("Id & Password Created successfully");
		else
			System.out.println("Credentials not generated");
		
		return true;
	}

	@Override
	public boolean removeDoctor(String doctorId) {
		int rows = 0;
		
		try{
			
			String query = "DELETE FROM DOCTOR where doctor_id=?";

			rows = jdbcTemplate.update(query, doctorId);
			
		} catch (EmptyResultDataAccessException e) {
//			e.printStackTrace();
			return false;
		}
		if(rows < 1) 
			return false;
		return true;
	}
	
	@Override
	public int getLastDId() {
		int counter = 0;
		try{
			
			String query = "SELECT * FROM DOCTOR order by doctor_id desc limit 1";
			Doctor doctor =  jdbcTemplate.queryForObject(query,new DoctorRowMapper());

			String id = doctor.getPersonId();
			if (!id.isEmpty()) {
//				System.out.println(id);
				counter = Integer.parseInt(id.substring(1));
			}
			else {
				counter = 1000;
			}

		} catch (EmptyResultDataAccessException e) {
//			e.printStackTrace();
			return 0;
		}
		return counter;
	}

	

}
