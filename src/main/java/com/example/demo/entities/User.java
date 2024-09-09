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
	public Integer id;

	public String username;

	public String email;
	
	public String password;

	public User() {}

	public User(String username,String email, String password) {
		this.username=username;
		this.email = email;
		this.password = password;
	}


}
