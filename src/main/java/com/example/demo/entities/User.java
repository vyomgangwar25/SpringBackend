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
  
	 @NotBlank(message = "UserName is required")
	private String username;
	 
    @NotBlank(message="Email is required")
	private String email;
    
	@NotBlank(message="Password is required")
	@Size(min = 6, message = "Password must be at least 6 characters")
	private String password;
	
	@NotBlank(message="Role is required")
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

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+role));	
	}
}