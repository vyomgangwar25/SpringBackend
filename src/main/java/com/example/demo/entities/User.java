package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String username;

	private String email;
	
	private String password;
	
	public User() {}

	public User(String username,String email, String password) {
		this.username=username;
		this.email = email;
		this.password = password;
	}
	
	public void setUserName(String username)
	{
		this.username=username;
	}

	public String getUsername() {
		return username;
	}
	
	public void setEmail(String email)
	{
		this.email=email;
	}

	public String getEmail() {
		return email;
	}
	
	public void setPassowrd(String password)
	{
		this.password=password;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
}