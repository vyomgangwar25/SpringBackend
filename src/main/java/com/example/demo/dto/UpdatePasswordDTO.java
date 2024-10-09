package com.example.demo.dto;

public class UpdatePasswordDTO {
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
