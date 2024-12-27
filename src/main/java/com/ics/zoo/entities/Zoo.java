package com.ics.zoo.entities;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
/**
 * Zoo Entity
 * 
 * @author Vyom Gangwar
 * 
 ***/
@Entity
public class Zoo extends Audit {

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
