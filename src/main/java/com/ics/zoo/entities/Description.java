package com.ics.zoo.entities;

import jakarta.persistence.Entity;

@Entity
public class Description extends CommonEntity {
	private String description;
	public Description(){
		 
	 }
	public Description(String description) {
		this.description=description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	

}
