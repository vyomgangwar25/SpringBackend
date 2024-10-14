package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDTO 
{
	
	@NotBlank(message = "UserName is required")
	public String username;
	
	 @NotBlank(message = "Email is required")
	public String email;
	 
	 @NotBlank(message = "Password is required")
	 @Size(min = 6, message = "Password must be at least 6 characters")
	public String password;
	 
	 @NotBlank(message = "Role is required")
	public String role;

	public UserDTO() {}

	public UserDTO(String username,String email, String password,String role) {
		this.username=username;
		this.email = email;
		this.password = password;
		this.role=role;
	}
}
