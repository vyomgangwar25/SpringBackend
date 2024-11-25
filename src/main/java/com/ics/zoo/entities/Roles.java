package com.ics.zoo.entities;

import jakarta.persistence.Entity;

@Entity
public class Roles extends CommonEntity {
	private String role;
	public Roles() {
		 
	}

	Roles(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
