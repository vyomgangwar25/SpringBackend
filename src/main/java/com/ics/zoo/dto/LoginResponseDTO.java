package com.ics.zoo.dto;

public class LoginResponseDTO {
	private String token;
	private String role;
	private String email;
	private String name;
	private Integer id;

	LoginResponseDTO() {

	}

	public LoginResponseDTO(String token, String role, String email, String name, Integer id) {
		this.token = token;
		this.role = role;
		this.email = email;
		this.name = name;
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
