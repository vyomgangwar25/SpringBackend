package com.example.demo.filter;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;

public class CustomClass implements Customizer<ExceptionHandlingConfigurer<HttpSecurity>>
{

	@Override
	public void customize(ExceptionHandlingConfigurer<HttpSecurity> t) {
		System.out.println("test");
		
	}


}
