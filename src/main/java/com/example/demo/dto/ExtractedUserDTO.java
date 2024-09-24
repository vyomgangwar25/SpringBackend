package com.example.demo.dto;

public class ExtractedUserDTO {
	private String username;
	private String email;
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
