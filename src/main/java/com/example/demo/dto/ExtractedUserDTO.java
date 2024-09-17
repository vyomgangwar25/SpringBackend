package com.example.demo.dto;

public class ExtractedUserDTO {
	private String username;
	private String email;

	public ExtractedUserDTO() {}
	
	public ExtractedUserDTO(String username,String email){
		this.username=username;
		this.email=email;
	}
	public void userName(String username) {
		this.username=username;
	}
	public String getUserName()
	{
		return username;
	}
	public void setEmail(String email)
	{
		this.email=email;
	}
	public String getEmail() {
		return email;
	}
	

}
