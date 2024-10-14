package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

public class ForgotPasswordRequestDTO {
	@NotBlank(message="Email is required")
	private String email;
	
	public ForgotPasswordRequestDTO() {
		 
	}
	public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
