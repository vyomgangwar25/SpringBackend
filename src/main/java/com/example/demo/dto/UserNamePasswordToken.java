package com.example.demo.dto;

public class UserNamePasswordToken {
	
	private String Email;
	private String password;

	public UserNamePasswordToken(String Email,String password)
	  {
		  this.Email=Email;
		  this.password=password;
	  }
	
	  public String getEmail()
	  {
		  return Email;
	  }
	  public String getPassword()
	  {
		  return password;
	  }
	  public void setEmail(String Email)
	  {
		  this.Email=Email;
	  }
	  public void setPassword(String password)
	  {
		  this.password=password;
	  }
}
