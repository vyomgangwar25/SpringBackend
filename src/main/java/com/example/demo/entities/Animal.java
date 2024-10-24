package com.example.demo.entities;

import java.util.Date;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Animal extends CommonEntity {

	private String name;
	private String gender;
	private Date dob;
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "zoo_id", referencedColumnName = "id")
	private Zoo zoo;

	public Animal() {
	}

	public Animal(String name, String gender, Date dob, Zoo zoo) {
		this.name = name;
		this.gender = gender;
		this.dob = dob;
		this.zoo = zoo;
	}

	public Animal(String name, String gender, Date dob, Integer zooId) {
		this.name = name;
		this.gender = gender;
		this.dob = dob;
		this.zoo = new Zoo(zooId);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Zoo getZoo() {
		return zoo;
	}

	public void setZoo(Zoo zoo) {
		this.zoo = zoo;
	}
}
