package com.example.demo.jwt;

public class JwtTokenObject {
	
 private String token;
 
 public JwtTokenObject(String token)
 {
	 this.token=token;
 }
 public void setToken(String token)
 {
	   this.token=token; 
 }
 public String getToken()
 {
	 return token;
 }
}
