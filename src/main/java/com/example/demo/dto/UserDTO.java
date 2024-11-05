package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserDTO extends LoginUserDTO {

	@NotNull(message = "name must not be empty")
	@NotBlank(message = "name not be empty")
	public String username;

	@NotNull(message = "enter role")
	public String role;

	public UserDTO() {
	}

	public UserDTO(String username, String role) {
		this.username = username;

		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
