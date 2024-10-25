package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDTO 
{
	
 
	public String username;
	
	 
	public String email;
	 
 
	public String password;
	 
 
	public String role;

	public UserDTO() {}

	public UserDTO(String username,String email, String password,String role) {
		this.username=username;
		this.email = email;
		this.password = password;
		this.role=role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
