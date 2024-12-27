package com.ics.zoo.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
/**
 * CommonEntity
 * @author Vyom Gangwar
 * 
 * */
@MappedSuperclass
public class CommonEntity 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	 private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
