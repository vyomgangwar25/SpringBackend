package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ExtractedUserDTO {
	@NotBlank(message = "username is mandatory")
	private String username;
	
	@NotBlank(message = "email is mandatory")
	private String email;
	
	@NotNull
    private Integer id;
	
	public ExtractedUserDTO() {}
	
	public ExtractedUserDTO(String username,String email,Integer id){
		this.username=username;
		this.email=email;
		this.id=id;
	}
	
	public String getUserName()
	{
		return username;
	}
	 
	public String getEmail() {
		return email;
	}
	 
	public Integer getId()
	{
		return id;
	}

}
