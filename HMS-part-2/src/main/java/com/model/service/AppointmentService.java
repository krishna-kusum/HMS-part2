package com.model.service;

import java.util.List;

import com.bean.PrevSlots;

public interface AppointmentService {
	List<PrevSlots> prevSlots(String dId);
}
