package com.ics.zoo.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExtractAnimalDTO {
	private String name;
	private String gender;
	private Date dob;
	private Integer zoo_id;
	private Integer animal_id;

	 

}
