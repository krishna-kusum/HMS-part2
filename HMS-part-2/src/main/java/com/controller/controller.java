package com.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bean.Appointment;
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
	@Autowired
	private PatientServiceImpl patientService;
	
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
	public ModelAndView validationController(HttpServletRequest request,HttpSession session) {
		ModelAndView modelAndView=new ModelAndView();
		
		String userName = request.getParameter("user");
		String password = request.getParameter("pass");

		session.setAttribute("userName", userName);
		
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
	
	
//  Registering Patient--------------------------------------------------------------------------------------------------------------------------------------
	
	@RequestMapping("/savePatient")
	public ModelAndView savePatientController(HttpServletRequest request,HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		
		Patient patient = new Patient();
		patient.setCounter(patientService.getLastPatientId());
		
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
		
//		doctor.setCounter();
		
//		doctor.setPersonId("D" + patient.getCounter());
		
		doctor.setName(request.getParameter("dName"));
		doctor.setAge(Integer.parseInt(request.getParameter("dAge")));
		doctor.setGender(request.getParameter("dGender"));
		doctor.setExperienceInYears(Integer.parseInt(request.getParameter("dExperience")));
		doctor.setContactNumber(request.getParameter("dContact"));
		doctor.setAddress(request.getParameter("dAddress"));
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
	public ModelAndView viewPatientController(HttpServletRequest request,HttpSession session) {
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
	
	
	
//	 Patient Functionalities --------------------------------------------------------------------------------------------------------------------
	

//	1. view patient profile
	@RequestMapping("/showPatient")
	public ModelAndView showPatientController(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		

		Patient patient = doctorService.getPatientProfile((String)session.getAttribute("userName"));
		if (patient != null) {
			modelAndView.addObject("patient", patient);
			modelAndView.setViewName("ShowPatient");
		}
		else {
			String message="Patient with ID "+(String)session.getAttribute("userName")+" does not exist!";
			modelAndView.addObject("message", message);
			modelAndView.setViewName("Output");
		}
		return modelAndView;
	}
	
//	2. request appointment
	@RequestMapping("/requestAppointment")
	public ModelAndView requestAppointmentController(HttpServletRequest request) {
		
		return new ModelAndView("requestAppointmentPage");
	}
	
//	2.1 generate appointment 
	@RequestMapping("/requestAppointment")
	public ModelAndView generateAppointmentController(HttpServletRequest request) {
		
		return new ModelAndView("requestAppointmentPage");
	}
//	3. cancel appointment
	@RequestMapping("/cancelAppointment")
	public ModelAndView cancelAppointmentController(HttpServletRequest request, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		
		String id = (String)session.getAttribute("userName");
		List<Appointment> appointments = patientService.getMyAppointments(id, 1);
		
		
		if(!appointments.isEmpty()) {
			modelAndView.addObject("appointmentList", appointments);
			modelAndView.setViewName("cancelAppointment");
		}
		else {
			String message="No appointments to delete";
			modelAndView.addObject("message", message);
			modelAndView.setViewName("Output");
		}
		
		return modelAndView;
	}
	
//	3.1
	@RequestMapping("/deleteAppointment")
	public ModelAndView deleteAppointmentController(HttpServletRequest request, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		int id = Integer.parseInt(request.getParameter("appointmentId"));

		String message = "";

		if(patientService.cancelAppointmentRequest(id)) {
			message="Appointment with ID "+id+" deleted successfully!";
			
		}else {
			message="Appointment with ID "+id+" does not exist!";
		}
		
		modelAndView.addObject("message", message);
		modelAndView.setViewName("Output");
		return modelAndView;
	}
	
//	4. view all appointments
	@RequestMapping("/viewAllAppointments")
	public ModelAndView viewAllAppointmentsController(HttpServletRequest request, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		
		String id = (String) session.getAttribute("userName");
		List<Appointment> appointments = patientService.getMyAppointments(id, 1);
		
		
		if(!appointments.isEmpty()) {
			modelAndView.addObject( "myAppointmentList", appointments);
			modelAndView.setViewName("ShowMyAppointments");
		}
		else {
			String message="No appointments to display";
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
