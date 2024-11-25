package com.ics.zoo.dto;

import java.util.Date;

public class ExtractAnimalDTO {
    private String name;
    private String gender;
    private  Date dob;
    private Integer zoo_id;
    private Integer animal_id;
    public ExtractAnimalDTO() {
		 
	}
    public ExtractAnimalDTO(String name,String gender,Date dob,Integer zoo_id,Integer animal_id) {
		  this.name=name;
		  this.gender=gender;
		  this.dob=dob;
		  this.zoo_id=zoo_id;
		  this.animal_id=animal_id;
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
	public Integer getZoo_id() {
		return zoo_id;
	}
	public void setZoo_id(Integer zoo_id) {
		this.zoo_id = zoo_id;
	}
	public Integer getAnimal_id() {
		return animal_id;
	}
	 
}
