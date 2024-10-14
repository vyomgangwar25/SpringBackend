package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

public class ZooDTO {
	@NotBlank(message = "name is required")
	private String name;

	@NotBlank(message = "Location is required")
	private String location;

	@NotBlank(message = "size is required")
	private Integer size;
	private Integer id;

	public ZooDTO(String name, String location, Integer size, Integer id) {
		this.name = name;
		this.location = location;
		this.size = size;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
