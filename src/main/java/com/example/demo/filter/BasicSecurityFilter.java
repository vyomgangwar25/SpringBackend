package com.example.demo.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dto.UserNamePasswordToken;
import com.example.demo.entities.User;
import com.example.demo.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class BasicSecurityFilter extends HttpFilter
{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	  private UserRepository repository;
	
	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException 
	{
		//System.out.println(request.getHeader("email")+" "+request.getHeader("password"));
		//System.out.println("hello");
		 UserNamePasswordToken token = extractUsernameAndPasswordFrom(request);
		 
		 if (notAuthenticated(token)) {  // (2)
	            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // HTTP 401.
	            return;
	        }
		super.doFilter(request, response, chain);
	}
	
	UserNamePasswordToken extractUsernameAndPasswordFrom(HttpServletRequest request)
	{
		 UserNamePasswordToken userDet = new UserNamePasswordToken(request.getHeader("email"), request.getHeader("password"));
		 //System.out.println(userDet.getEmail()+ " "+userDet.getPassword());
		return userDet;
	}
	
	 private boolean notAuthenticated( UserNamePasswordToken token) {
		User user = repository.findByEmail(token.getEmail());
		if(user!=null)
		{
			if(user.getPassword().equals(token.getPassword()))
			{
			System.out.println("Authenticate successfully");
			}
		}
		System.out.println("in auth");
	        return false;
	    }
}
