package com.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bean.Doctor;
import com.bean.Patient;
import com.model.persistence.DoctorDao;
import com.model.persistence.PatientDao;
import com.model.service.AdminService;
import com.model.service.DoctorService;
import com.model.service.PatientService;
import com.model.service.PatientServiceImpl;
import com.model.service.ValidateUserService;



@Controller
public class controller {
	
	@Autowired
	private ValidateUserService validate;
	
	@Autowired
	private PatientDao patientDao;
	@Autowired
	private DoctorDao doctorDao;
	@Autowired
	private AdminService adminService;
	@Autowired
	private DoctorService doctorService;
	
	@RequestMapping("/")
	public ModelAndView homePageController() {
		return new ModelAndView("Home");
	}
	@RequestMapping("/register")
	public ModelAndView registerPageController() {
		return new ModelAndView("patientRegister");
	}
	
	@RequestMapping("/beforeLogin")
	public ModelAndView beforeLoginController() {
		return new ModelAndView("beforeLogin");
	}
	@RequestMapping("/login")
	public ModelAndView loginPageController() {
		return new ModelAndView("login");
	}
	
	
	
	
//  Validation--------------------------------------------------------------------------------------------------------------------------------------
	
	
	
	
	@RequestMapping("/validate")
	public ModelAndView validationController(HttpServletRequest request) {
		ModelAndView modelAndView=new ModelAndView();
		String userName = request.getParameter("user");
		String password = request.getParameter("pass");
		
		 if(validate.isAdmin(userName, password)) {
			modelAndView.setViewName("adminPostLogin");
		}
		else if(validate.isDoctor(userName, password)) {
			modelAndView.setViewName("doctorPostLogin");
		}else if(validate.isPatient(userName, password)) {
			modelAndView.setViewName("patientPostLogin");
		} 
		 
		
//		if(userName.equalsIgnoreCase("Admin") && password.equalsIgnoreCase("123")) {
//			modelAndView.setViewName("adminPostLogin");
//		}
		
		
		
		return modelAndView;
		
	}
	
	
//  Adding Patient--------------------------------------------------------------------------------------------------------------------------------------
	@Autowired
	private PatientServiceImpl impl;
	@RequestMapping("/savePatient")
	public ModelAndView savePatientController(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		
		Patient patient = new Patient();
		patient.setCounter(impl.getLastPatientId());
		
		patient.setPersonId("P" + patient.getCounter());
		patient.setName(request.getParameter("pName"));
		patient.setAge(Integer.parseInt(request.getParameter("pAge")));
		patient.setGender(request.getParameter("pGender"));
		patient.setContactNumber(request.getParameter("pContact"));
		patient.setAddress(request.getParameter("pAddress"));
		patient.setSymptoms(request.getParameter("pSymptom"));
		
		String message = null;
		if (patientDao.addPatient(patient))
			message = "Patient Addded Successfully";
		else
			message = "Patient Addition Failed";

		modelAndView.addObject("message", message);
		modelAndView.setViewName("Output");

		return modelAndView;
	}
	
//  Admin Functionalities Start--------------------------------------------------------------------------------------------------------------------------

	
//  Add Doctor
	@RequestMapping("/addDoctor")
	public ModelAndView addDoctorController() {
		return new ModelAndView("addDoctor");
	}
	@RequestMapping("/saveDoctor")
	public ModelAndView saveDoctorController(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		Doctor doctor = new Doctor();
		
		doctor.setName(request.getParameter("dName"));
		doctor.setAge(Integer.parseInt(request.getParameter("dAge")));
		doctor.setGender(request.getParameter("dGender"));
		doctor.setExperienceInYears(Integer.parseInt(request.getParameter("dExperience")));
		doctor.setContactNumber(request.getParameter("pContact"));
		doctor.setAddress(request.getParameter("pAddress"));
		doctor.setDepartment(request.getParameter("dDepartment"));
		
		String message = null;
		if (adminService.registerDoctorToDatabase(doctor))
			message = "Patient Addded Successfully";
		else
			message = "Patient Addition Failed";

		modelAndView.addObject("message", message);
		modelAndView.setViewName("Output");
		return modelAndView;
	}
	
//  Remove Doctor

	@RequestMapping("/removeDoctorByID")
	public ModelAndView removeDoctorByIdController() {
		return new ModelAndView("DoctorIdAccepter");
	}
	@RequestMapping("/removeDoctor")
public ModelAndView removeDoctorController(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		
		String id = request.getParameter("dId");
		String message = null;
		if (adminService.removeDoctorFromDatabase(id))
			message = "Doctor Removed Successfully";
		else
			message = "Remove Failed";

		modelAndView.addObject("message", message);
		modelAndView.setViewName("Output");
		return modelAndView;
	}
	
//  Admin Functionalities End ------------------------------------------------------------------------------------------------------------------------
	
	
//  Doctor Functionalities Start ---------------------------------------------------------------------------------------------------------------------
	
	@RequestMapping("/viewPatientEnterId")
	public ModelAndView viewPatientEnterIdController() {
		return new ModelAndView("PatientEnterId");
	}
	@RequestMapping("/viewPatient")
	public ModelAndView viewPatientController(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();

		Patient patient = doctorService.getPatientProfile(request.getParameter("pId"));
		if (patient != null) {
			modelAndView.addObject("patient", patient);
			modelAndView.setViewName("ShowPatient");
		}
		else {
			String message="Patient with ID "+request.getParameter("pId")+" does not exist!";
			modelAndView.addObject("message", message);
			modelAndView.setViewName("Output");
		}
		return modelAndView;
	}
//	@RequestMapping("/showAppointment")
//	public ModelAndView showAppointmentController() {
//		List<String> appointmentDoc = doctorService.getMyAppointments(id, 2);
//		if(appointmentDoc.isEmpty()) {
//			System.out.println("No appointments requested.");
//			break;
//		}
//		else {
//			System.out.println("Displaying all appointments: ");
//			for(String appointment: appointmentDoc)
//				System.out.println(appointment);
//		}
//	}

}
