package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class LoginUserDTO {

	@Email(message = "email address not valid!!")
	public String email;

	@Size(min = 6, message = "Password must be at least 6 characters")
	@NotEmpty(message = "Password must not be empty")
	public String password;

	public LoginUserDTO() {

	}

	public LoginUserDTO(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
