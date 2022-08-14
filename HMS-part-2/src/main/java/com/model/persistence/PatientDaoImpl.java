package com.model.persistence;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bean.Patient;
import com.model.persistence.helper.PatientRowMapper;

@Repository
public class PatientDaoImpl implements PatientDao {

//	private Connection connection;
//	private PreparedStatement preparedStatement;
//	private Statement statement;
//	private ResultSet resultSet;
//	
//	Connection connectDB() throws SQLException {
//		return DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "wiley");
//	}
	
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public List<Patient> getPatientList() {
		
		List<Patient> patientList = new ArrayList<Patient>();
		
		try{
			
			String query = "SELECT * FROM PATIENT";
			patientList = jdbcTemplate.query(query, new PatientRowMapper());

			return patientList;

		} catch (EmptyResultDataAccessException e) {
//			e.printStackTrace();
			return patientList;
		}
	}



	@Override
	public boolean addPatient(Patient patient) {
		int rows = 0;
		try{
			
			String query = "INSERT INTO PATIENT values(?,?,?,?,?,?,?)";
			rows = jdbcTemplate.update(query, new PatientRowMapper(),
					patient.getPersonId(),
					patient.getName(),
					patient.getGender(),
					patient.getAge(),
					patient.getContactNumber(),
					patient.getAddress(),
					patient.getDepartment()
					);
			

		} catch (EmptyResultDataAccessException e) {
//			e.printStackTrace();
			return false;
		}
		if (rows>0)
			return true;
		return false;
	}

	@Override
	public boolean removePatient(String patientId) {
		// TODO Auto-generated method stub
		int rows;
		try{

			String query = "DELETE FROM PATIENT where patient_id=?";
			rows = jdbcTemplate.update(query, new PatientRowMapper(), patientId);
			if(rows>0)
			return true;

		} catch (EmptyResultDataAccessException e) {
//			e.printStackTrace();
			return false;
		}
		return false;
	}


	@Override
	public Patient getPatientById(String patientId) {
		Patient patient = null;
		try{
			
			String query = "SELECT * FROM PATIENT where patient_id=?";
			patient = jdbcTemplate.queryForObject(query, new PatientRowMapper(),patientId);
			

		} catch (EmptyResultDataAccessException e) {
//			e.printStackTrace();
			return patient;
		}

		return patient;
	}
	
	@Override
	public int getLastPId() {
		int counter = 0;
		try{
			
			String query = "SELECT patient_id FROM PATIENT order by patient_id desc limit 1";
			Patient patient =  jdbcTemplate.queryForObject(query,new PatientRowMapper());

			String id = patient.getPersonId();
			if (id.isEmpty()) {
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
