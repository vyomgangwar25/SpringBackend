
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
	private Zoo fromZoo;
	
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="to_zoo_id", referencedColumnName = "id")
	private Zoo toZoo;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="user_name",referencedColumnName = "id")
	private User user;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="animal_id", referencedColumnName="id")
	private Animal animal;
	
	private Date date;
	
	public AnimalTransferHistory() {
	}
	
	public AnimalTransferHistory(Zoo fromZoo, Zoo toZoo, User user, Animal animalId, Date date) {
		this.fromZoo=fromZoo;
		this.toZoo=toZoo;
		this.user=user;
		this.animal=animalId;
		this.date=date;	
	}

	public Zoo getFromZoo() {
		return fromZoo;
	}

	public void setFromZoo(Zoo fromZoo) {
		this.fromZoo = fromZoo;
	}

	public Zoo getToZoo() {
		return toZoo;
	}

	public void setToZoo(Zoo toZoo) {
		this.toZoo = toZoo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}  
}
