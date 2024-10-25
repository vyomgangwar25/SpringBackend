package com.example.demo.entities;

import jakarta.persistence.Entity;
 
import jakarta.validation.constraints.NotBlank;

@Entity
public class Zoo extends CommonEntity {
	
	@NotBlank(message = "Name is required")
	private String name;

	@NotBlank(message = "location is required")
	private String location;

 
	private Integer size;

	public Zoo() {
	}

	public Zoo(Integer id) {
		setId(id);
	}

	public Zoo(String name, String location, Integer size) {
		this.name = name;
		this.location = location;
		this.size = size;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

}
