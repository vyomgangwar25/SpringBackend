package com.ics.zoo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnimalUpdateDTO {
	@NotBlank(message = "name is required")
	private String name;

	@NotBlank(message = "gender is required")
	private String gender;

}
