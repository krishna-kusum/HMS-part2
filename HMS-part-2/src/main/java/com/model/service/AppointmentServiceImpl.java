package com.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bean.PrevSlots;
import com.model.persistence.AppointmentDaoImpl;

@Service
public class AppointmentServiceImpl implements AppointmentService {
	AppointmentDaoImpl appointmentDaoImpl;
	
	@Autowired
	public void setAppointmentDaoImpl(AppointmentDaoImpl appointmentDaoImpl) {
		this.appointmentDaoImpl = appointmentDaoImpl;
	}
	@Override
	public List<PrevSlots> prevSlots(String dId) {
		// TODO Auto-generated method stub
		return appointmentDaoImpl.prevAppointments(dId);
	}

}
