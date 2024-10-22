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
}
