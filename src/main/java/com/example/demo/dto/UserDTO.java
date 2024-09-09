package com.example.demo.dto;

public class UserDTO 
{
	public String username;
	public String email;
	public String password;

	public UserDTO() {}

	public UserDTO(String username,String email, String password) {
		this.username=username;
		this.email = email;
		this.password = password;
	}
}
