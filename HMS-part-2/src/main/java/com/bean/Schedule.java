package com.bean;

import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Schedule {
	
	private String doctor_id;  
	private String name_of_doctor; 
	private String available_day; 
	private Time slot_start; 
	private Time slot_end;

}
