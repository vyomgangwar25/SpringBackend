package com.ics.zoo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class ZooRegistrationDTO {
	@NotBlank(message = "name must not be blank")
	public String name;

	@NotBlank(message = "location must not be blank")
	public String location;

	@NotNull(message = "size cannot be zero")
	public Integer size;

	@NotNull(message = "description cannot be null")
	public String description;

}
