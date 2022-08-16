package com.bean;

import java.sql.Date;
import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {
	private int appointmentId;
	private String patientId;
	private String patientName;
	private Time slot;
	private Date date;
	private String doctorId;
	private String doctorName;
	private String department;
	
}
