package com.model.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bean.Appointment;
import com.bean.PrevSlots;
import com.model.persistence.helper.AppointmentRowMapper;


@Repository
public class AppointmentDaoImpl implements AppointmentDao {
	
	ArrayList<String> list = new ArrayList<>();
	List<PrevSlots> prevSlots = new ArrayList<>();
	LoginDaoImpl loginDaoImpl;
	private JdbcTemplate jdbcTemplate;
	
	private Connection connection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	private CallableStatement callableStatement;


	@Autowired
	public void setLoginDaoImpl(LoginDaoImpl loginDaoImpl) {
		this.loginDaoImpl = loginDaoImpl;
	}


	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	Connection connectDB() throws SQLException {
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "wiley");
	}
	
	public void appointment(String patient_id, String doc_id, Date date2) {

		try{
			String query = "call book_appointment(?,?)";
			this.connection = connectDB();
			callableStatement=connection.prepareCall("{call book_appointment(?,?)}");
						
			callableStatement.setString(1, patient_id);
			callableStatement.setString(2, doc_id);

			resultSet = callableStatement.executeQuery();

			if (resultSet.next()) {
				String p_id = resultSet.getString("Patient Id");
				String p_name = resultSet.getString("Patient Name");
				String slot = resultSet.getString("Starting Time");
				String slot_end = resultSet.getString("Ending Time");
				String d_id = resultSet.getString("Doctor Id");
				String d_name = resultSet.getString("Appointed Doctor");
				String dept = resultSet.getString("Department");

				Date date = date2;
				String new_slot = checkSlot(slot, d_id, date);
//				System.out.println(new_slot+ " " +slot_end);
		
//				int a = new_slot.compareTo(LocalTime.parse(slot_end).toString());
				int a = new_slot.compareTo(slot_end);
				
				if(a != 0) {
					storeAppointment(p_id, p_name, new_slot, date, d_id, d_name, dept);
					System.out.println(p_id+" "+p_name+" "+new_slot+" "+date+" "+d_id+" "+d_name+" "+dept);
				}
				else
					System.out.println("No slot present for the date :"+date);
				
			}		

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public String checkSlot(String slot_to_check, String d_id, Date date) {
		LocalTime new_slot = null;
		try {
			this.connection = connectDB();
			preparedStatement = connection.prepareStatement("select slot from appointments where doctor_id = ? AND date_of_appointment = ?");

			preparedStatement.setString(1, d_id);
			preparedStatement.setDate(2, date);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String slot_present = resultSet.getString("slot");
				list.add(slot_present);
				
				
			}if(list.isEmpty()) {
				return slot_to_check;}
			
			String last_slot = list.get(list.size()-1);
			new_slot = LocalTime.parse(last_slot);
			new_slot = new_slot.plusMinutes(20);
			
			
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new_slot.toString();
		
	}
	
	
	public void storeAppointment(String p_id, String p_name, String new_slot, Date date, String d_id, String d_name, String dept) {
		try{
			this.connection = connectDB();
			preparedStatement = connection.prepareStatement("INSERT INTO Appointments(patient_id,"
					+ "name_of_patient,"
					+ "slot,"
					+ "date_of_appointment,"
					+ "doctor_id,"
					+ "name_of_doctor,"
					+ "department"
					+ ") values(?,?,?,?,?,?,?)");

			preparedStatement.setString(1, p_id);
			preparedStatement.setString(2, p_name);
			preparedStatement.setString(3, new_slot);
			preparedStatement.setString(4, date.toString());
			preparedStatement.setString(5, d_id);
			preparedStatement.setString(6, d_name);
			preparedStatement.setString(7, dept);
			

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		

	}
	
	public List<PrevSlots> prevAppointments(String dId) {
		
		try{ 
			this.connection = connectDB();
			preparedStatement = connection.prepareStatement("select slot, date_of_appointment from appointments where doctor_id = ?");

			preparedStatement.setString(1, dId);

			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				Time slot = resultSet.getTime("slot");
				Date date = resultSet.getDate("date_of_appointment");
				prevSlots.add(new PrevSlots(slot, date));
			}			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return prevSlots;
		
	}
	
	@Override
	public List<Appointment> getAllAppointments(String id, int choice) {
		List<Appointment> appointmentList = null;
		try{
			this.connection = connectDB();
			
			if(choice == 1) {
				String query = "select * from Appointments where patient_id=?";
				appointmentList=jdbcTemplate.query(query, new AppointmentRowMapper(),id);
				
				
			}
			else if(choice == 2) {
				String query = "select * from Appointments where doctor_id=?";
				appointmentList=jdbcTemplate.query(query, new AppointmentRowMapper(),id);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return appointmentList;
		
	}

	@Override
	public boolean cancelAppointment(int aid) {
		int rows = 0;
		try {
			
			this.connection = connectDB();
			preparedStatement = connection.prepareStatement("delete from Appointments where appointment_id=?");

			preparedStatement.setInt(1, aid);

			rows = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(rows > 0)
			return true;
		return false;
	}

	public boolean reschedule(int aid, Date newDate) {
		int rows = 0;
		try {
			
			this.connection = connectDB();
			preparedStatement = connection.prepareStatement("select patient_id,"
					+ "doctor_id,"
					+ "department from Appointments where appointment_id=?");

			preparedStatement.setInt(1, aid);

			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				if(cancelAppointment(aid)) {
					appointment(resultSet.getString("patient_id"), resultSet.getString("doctor_id"), newDate);
					rows = 1;
				} 
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(rows > 0)
			return true;
		return false;
	}
	
}
