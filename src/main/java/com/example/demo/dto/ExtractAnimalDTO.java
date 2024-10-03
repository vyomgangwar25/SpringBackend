package com.example.demo.dto;

import java.util.Date;

public class ExtractAnimalDTO {
    private String name;
    private String gender;
    private  Date dob;
    public ExtractAnimalDTO() {
		 
	}
    public ExtractAnimalDTO(String name,String gender,Date dob) {
		  this.name=name;
		  this.gender=gender;
		  this.dob=dob;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
}
