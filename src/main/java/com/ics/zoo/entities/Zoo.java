package com.ics.zoo.entities;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Zoo extends CommonEntity {

	@NotBlank(message = "Name is required")
	private String name;

	@NotBlank(message = "location is required")
	private String location;

	private Integer size;

	private String description;

	public Zoo(Integer id) {
		setId(id);
	}

}
