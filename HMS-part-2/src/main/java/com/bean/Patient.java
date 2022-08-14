package com.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient extends Person{

	private String address;
	private int counter;
	private String symptoms;

	public Patient(String personId, String Name, int age, String gender, String contactNumber, String symptoms,
		String address) {
		super(personId, Name, age, gender, contactNumber, null);
		this.address = address;
		this.symptoms = symptoms;
	}

	public void setCounter(int counter) {
		this.counter = counter + 1;
	}
	
	public int getCounter() {
		return counter;
	}

	@Override
	public String toString() {
		return "Patient [PersonId=" + getPersonId() + ", Name=" + getName()
				+ ", Age=" + getAge() + ", Gender=" + getGender() + ", ContactNumber="
				+ getContactNumber() + ", Symptoms=" + getSymptoms() + ", address=" + getAddress() +"]";
	}

}