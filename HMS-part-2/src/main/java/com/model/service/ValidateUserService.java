package com.model.service;


import com.bean.Doctor;
import com.bean.Patient;



public interface ValidateUserService {
	boolean registerUser(String id, String Password);
	
	boolean isPatient(String id, String Password);
	
	boolean isDoctor(String id, String Password);
	
	boolean isAdmin(String id, String Password);
	
	boolean registerPatient(Patient patient);
	
	boolean registerDoctor(Doctor doctor); // called internally only by admin 
	
}
