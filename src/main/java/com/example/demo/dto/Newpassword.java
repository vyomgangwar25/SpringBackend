package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Newpassword {
	@NotBlank(message="Password is required")
	 @Size(min = 6, message = "Password must be at least 6 characters")
	private String newPassword;
	public Newpassword() {
		
	}
	public void  setnewpassword(String newPassword)
	{
		this.newPassword=newPassword;
	}
	public String getnewPassword()
	{
		return newPassword;
	}
	

}
