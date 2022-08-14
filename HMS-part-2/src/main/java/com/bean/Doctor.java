package com.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor extends Person {

	private int experienceInYears;
	private String address;
	private int counter;

	public Doctor(String personId, String personName, String Specialization, int experience, String gender, int age, String Contact, String address){
		super(personId,personName,age,gender,Contact,Specialization);
		this.experienceInYears = experience;
		this.address = address;
				
	}

	@Override
	public String toString() {
		return "Doctor [experienceInYears=" + experienceInYears + ", address=" + address + ", getPersonId()="
				+ getPersonId() + ", getName()=" + getName() + ", getAge()=" + getAge() + ", getGender()=" + getGender()
				+ ", getContactNumber()=" + getContactNumber() + ", getDepartment()=" + getDepartment() + "]";
	}

	public void setCounter(int counter) {
		this.counter = counter + 1;
	}
	
	public int getCounter() {
		return counter;
	}
	
}
