package com.example.demo.enums;

public enum ResponseEnum
{
	INCORRECT_EMAIL("Incorrect email address"),
	INCORRECT_PASSWORD("Incorrect Password"),
	REGISTRATION(" Registered Successfully"),
   ALREADY_EXIST("User already exists!"),
	CHANGE_PASSWORD("password change successfully!!"),
	UPDATE("Data updated Successfully"),
	DELETE("Data deleted Successfully"),
	NOT_FOUND("Data not Found"),
	ANIMAL_TRANSFER("animal Transfered successfully"),
	USER_NOT_FOUND("user not found"),
	TOKEN_NOT_RECEIVED("token not received!"),
	UPDATE_PASSWORD_REQUEST("Update Password Request");
 
	
private String message;

private ResponseEnum(String message) {
	this.message = message;
}

public String getMessage() {
	return message;
}
	
}
