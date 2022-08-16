package com.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bean.Appointment;
import com.model.service.DoctorService;

@Controller
public class DoctorController {
	@Autowired
	private DoctorService doctorService;
	
	@RequestMapping("/showAppointment")
	public ModelAndView showAppointmentControllerForDoctor(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		String id = (String) session.getAttribute("userName");
		String message ;
		List<Appointment> appointmentDoc = doctorService.getMyAppointments(id, 2);
		if(appointmentDoc.isEmpty()) {
			message = "No appointments requested.";
			modelAndView.addObject("message", message);
			modelAndView.setViewName("Output");
			return modelAndView;
			
		}
		else {
			
			return new ModelAndView("ShowMyAppointments", "myAppointmentList", appointmentDoc);
		}
	}
}
