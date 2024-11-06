package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ZooRegistrationDTO {
	@NotBlank(message="name must not be blank")
	public String name;
	
    @NotBlank(message="location must not be blank")
	public String location;
    
     @NotNull(message="size cannot be zero")
	public Integer size;
   
	public ZooRegistrationDTO() {
		
	}
	public ZooRegistrationDTO(String name, String location, Integer size ) {
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
