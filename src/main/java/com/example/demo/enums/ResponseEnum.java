package com.example.demo.enums;

public enum ResponseEnum
{
	User_Email("Incorrect email address"),
	User_Password("Incorrect Password"),
	 Registration(" Registered Successfully"),
	user_Already_exist("User already exists!"),
	Chnage_Password("password change successfully!!"),
	Update("Data updated Successfully"),
	Delete("Data deleted Successfully"),
	NotFound("Data not Found"),
	Animal_transfer("animal Transfered successfully");
	
  private String message;

private ResponseEnum(String message) {
	this.message = message;
}

public String getMessage() {
	return message;
}

public void setMessage(String message) {
	this.message = message;
}
	
}
