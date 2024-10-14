package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdatePasswordDTO {
	@NotBlank(message="Password is required")
	 @Size(min = 6, message = "Password must be at least 6 characters")
	
	private String Password;
	  UpdatePasswordDTO() {
	 
	}
   UpdatePasswordDTO(String password, String Password)
     {
    	 this.Password=Password;
     }
	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}
	

}
