package com.example.demo.entities;

import jakarta.persistence.Entity;

@Entity
public class Privileges extends CommonEntity  {
	String privileges;
	public Privileges() {
		 
	}
	public Privileges(String privileges) {
		this.privileges=privileges;
	}
	public String getPrivileges() {
		return privileges;
	}
	public void setPrivileges(String privileges) {
		this.privileges = privileges;
	}
	
	

}
