package com.model.persistence.helper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import org.springframework.jdbc.core.RowMapper;

import com.bean.Schedule;

public class ScheduleRowMapper implements RowMapper<Schedule> {

	@Override
	public Schedule mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		
		String id = resultSet.getString("doctor_id");
		String name = resultSet.getString("name_of_doctor");
		String available_day = resultSet.getString("available_day");
		Time slot_start = resultSet.getTime("slot_start");
		Time slot_end = resultSet.getTime("slot_end");
		
		Schedule schedule = new Schedule(id,name,available_day,slot_start,slot_end);
		
		return schedule;
	}

}
