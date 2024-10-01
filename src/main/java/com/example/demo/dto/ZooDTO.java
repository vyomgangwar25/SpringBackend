package com.example.demo.dto;

public class ZooDTO {
  private String name;
  private String location;
  private Integer size;
  
  public ZooDTO() {}
  public ZooDTO(String name,String location,Integer size)
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
	this.size=size;
}
 
  
  
}
