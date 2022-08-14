package com.model.persistence;

import com.bean.Schedule;

public interface ScheduleDao {
	
	boolean addDoctorSchedule(Schedule schedule);
	
	boolean removeDoctorSchedule(String doctorId);

	Schedule getDoctorSchedule(String doctorId);
	

}
