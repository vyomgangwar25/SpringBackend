package com.ics.zoo.util;

public  class UrlConstant {
  public static  String generateUrl(String token)
    {
    	String url = "/SetPassword?token="+token;
    	return  url;
    }
}
