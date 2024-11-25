package com.example.demo.dto;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;

public class AnimalDTO
{
	@NotBlank(message="name is required")
	private String name;
	
	@NotBlank(message="gender is required")
	private String gender;
	
	@NotBlank(message="dob is required")
	private Date dob;
	
	private Integer zooid;
	 
	
	public AnimalDTO(String name,String gender,Date dob,Integer zooid) {
		this.name=name;
		this.gender=gender;
		this.dob=dob;
		this.zooid=zooid;
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
	public Integer getZooid()
	{
		return zooid;
	}
	public void setZooid(Integer zooid) {
		this.zooid=zooid;
	}
}
