package com.ics.zoo.dto;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AnimalDTO {
	@NotBlank(message = "name is required")
	private String name;

	@NotBlank(message = "gender is required")
	private String gender;

	@NotBlank(message = "dob is required")
	private Date dob;

	private Integer zooid;
}
