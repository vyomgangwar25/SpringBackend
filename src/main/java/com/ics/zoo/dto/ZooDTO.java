package com.ics.zoo.dto;
import jakarta.validation.constraints.NotNull;

public class ZooDTO extends ZooRegistrationDTO {
    
     
    @NotNull(message="Integer must not be blank")
	private Integer id;
    
    public ZooDTO(String name, String location, Integer size, String description,Integer id) {
    	this.location=location;
    	this.size=size;
    	this.name=name;
		this.id = id;
		this.description=description;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
