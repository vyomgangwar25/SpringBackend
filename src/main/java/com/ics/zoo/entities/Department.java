package com.ics.zoo.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Department extends CommonEntity {

	private String departmentName;
	private String departmentAddress;
	private String departmentCode;

}
