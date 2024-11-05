package com.example.demo.dto; 
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Newpassword {
	@Size(min = 6, message = "Password must be at least 6 characters")
	@NotEmpty(message = "Password must not be empty")
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
