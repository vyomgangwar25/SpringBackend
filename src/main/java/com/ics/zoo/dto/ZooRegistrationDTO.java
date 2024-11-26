package com.ics.zoo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ZooRegistrationDTO {
	@NotBlank(message="name must not be blank")
	public String name;
	
    @NotBlank(message="location must not be blank")
	public String location;
    
     @NotNull(message="size cannot be zero")
	public Integer size;
     
     @NotNull(message="description cannot be null")
     public String description;
   
	public ZooRegistrationDTO() {
		
	}
	public ZooRegistrationDTO(String name, String location, Integer size,String description ) {
		this.name = name;
		this.location = location;
		this.size = size;	
		this.description=description;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}


}
