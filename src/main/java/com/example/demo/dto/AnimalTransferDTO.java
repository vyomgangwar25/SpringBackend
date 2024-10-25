package com.example.demo.dto;

public class AnimalTransferDTO 
{
	String fromzoo;
	String tooZoo;
	String animalName;
	String userName;
	
	public AnimalTransferDTO()
	{
		
	}
			
	public AnimalTransferDTO(String fromzoo, String tooZoo, String animalName, String userName) {
		super();
		this.fromzoo = fromzoo;
		this.tooZoo = tooZoo;
		this.animalName = animalName;
		this.userName = userName;
	}

	public String getFromzoo() {
		return fromzoo;
	}

	public void setFromzoo(String fromzoo) {
		this.fromzoo = fromzoo;
	}

	public String getTooZoo() {
		return tooZoo;
	}

	public void setTooZoo(String tooZoo) {
		this.tooZoo = tooZoo;
	}

	public String getAnimalName() {
		return animalName;
	}

	public void setAnimalName(String animalName) {
		this.animalName = animalName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
