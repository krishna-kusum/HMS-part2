package com.model.persistence;


public interface LoginDao {
	
	boolean registerUser(String id, String password,int priority);
	
	boolean validate(String id, String password,int priority);
	
}