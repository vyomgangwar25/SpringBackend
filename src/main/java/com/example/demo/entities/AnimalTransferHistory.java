package com.example.demo.entities;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class AnimalTransferHistory extends CommonEntity {
   
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "from_zoo_id", referencedColumnName = "id")
	private Zoo from_zoo;
	
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="to_zoo_id", referencedColumnName = "id")
	private Zoo to_zoo;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="user_name",referencedColumnName = "id")
	private User user;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="animal_id", referencedColumnName="id")
	private Animal animalid;
	
	private Date date;
	
	public AnimalTransferHistory() {
	}
	
	public AnimalTransferHistory(Zoo from_Zoo,Zoo toZoo,User user,Animal animalid,Date date) {
		this.from_zoo=from_Zoo;
		this.to_zoo=toZoo;
		this.user=user;
		this.animalid=animalid;
		this.date=date;
		
	}
	
	public Zoo getFrom_zoo() {
		return from_zoo;
	}

	public void setFrom_zoo(Zoo from_zoo) {
		this.from_zoo = from_zoo;
	}

	public Zoo getTo_zoo() {
		return to_zoo;
	}

	public void setTo_zoo(Zoo to_zoo) {
		this.to_zoo = to_zoo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Animal getAnimalid() {
		return animalid;
	}

	public void setAnimalid(Animal animalid) {
		this.animalid = animalid;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	
}
