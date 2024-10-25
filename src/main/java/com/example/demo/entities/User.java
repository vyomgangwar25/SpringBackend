package com.example.demo.entities;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Entity
public class User extends CommonEntity implements UserDetails
{
	private static final long serialVersionUID = 1L;
  
 
	private String username;
	 
 
	private String email;
    
 
	 
	private String password;
	
 
	private String role;
	
	public User() {}

	public User(String username,String email, String password,String role) {
		this.username=username;
		this.email = email;
		this.password = password;
		this.role=role;
	}
	public void setpassword(String password)
	{
		this.password=password;
	}

	public String getRole()
	{
		return role;
	}

	public String getUsername()
	{
		return username;
	}
	
	public void setUsername(String username)
	{
		this.username=username;
	}

	public String getEmail() {
		return email;
	}
   public void setEmail(String email)
   {
	   this.email=email;
   }

	public String getPassword() {
		return password;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+role));	
	}
}