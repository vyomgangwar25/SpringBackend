package com.example.demo.util;

public  class UrlConstant {
  public static  String generateUrl(String token)
    {
    	String url = "http://localhost:3000/setpass?token="+token;
    	return  url;
    }
}
