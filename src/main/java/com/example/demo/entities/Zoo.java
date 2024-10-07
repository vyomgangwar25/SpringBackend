package com.example.demo.entities; 
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
 

@Entity
public class Zoo extends CommonEntity
{
	private String name;
	private String location;
    private Integer size;
	
	
	public Zoo() {}
	
	public Zoo(String name,String location,Integer size)
	{
		this.name=name;
		this.location=location;
		this.size=size;
		 
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

}
