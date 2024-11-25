package com.ics.zoo.dto;

import jakarta.validation.constraints.NotBlank;

public class AnimalUpdateDTO {
	@NotBlank(message="name is required")
	private String name;
	
	@NotBlank(message="gender is required")
	private String gender;
	
	public AnimalUpdateDTO() {
		
	}
	public AnimalUpdateDTO(String name,String gender) {
		this.name=name;
		this.gender=gender;
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
	 

}
