package com.model.persistence;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bean.Schedule;
import com.model.persistence.helper.ScheduleRowMapper;


@Repository
public class ScheduleDaoImpl implements ScheduleDao {
	
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public Schedule getDoctorSchedule(String doctorId) {
		
		Schedule schedule = null;
		try{
			
			String query = "SELECT * FROM regular_schedule where doctor_id=?";
			schedule = jdbcTemplate.queryForObject(query,new ScheduleRowMapper(),doctorId);

			
		} catch (EmptyResultDataAccessException e) {
//			e.printStackTrace();
			return schedule;
		}

		return schedule;
	}

	@Override
	public boolean addDoctorSchedule(Schedule schedule) {
		int rows = 0;
		try{

			String query = "INSERT INTO regular_schedule values(?,?,?,?,?)";
			rows = jdbcTemplate.update(query,new ScheduleRowMapper(),
					schedule.getDoctor_id(),
					schedule.getName_of_doctor(),
					schedule.getAvailable_day(),
					schedule.getSlot_start(),
					schedule.getSlot_end()
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
	public boolean removeDoctorSchedule(String doctorId) {
		int rows = 0;
		try{
			
			String query = "DELETE FROM REGULAR_SCHEDULE where doctor_id=?";
			rows = jdbcTemplate.update(query,new ScheduleRowMapper(),doctorId);
			

		} catch (EmptyResultDataAccessException e) {
//			e.printStackTrace();
			return false;
		}
		if (rows>0){
			return true;
		}
		return false;
	}

}
