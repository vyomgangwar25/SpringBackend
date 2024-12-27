package com.ics.zoo.entities;

import java.util.Date;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

/**
 * Animal Entity
 * @author Vyom Gangwar
 * */

@Entity
public class Animal extends CommonEntity {

	private String name;
	private String gender;
	private Date dob;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "zoo_id", referencedColumnName = "id")
	private Zoo zoo;

	public Animal(String name, String gender, Date dob, Integer zooId) {
		this.name = name;
		this.gender = gender;
		this.dob = dob;
		this.zoo = new Zoo(zooId);
	}

}
