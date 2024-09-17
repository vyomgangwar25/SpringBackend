package com.example.demo.security;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.example.demo.entities.User;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ZooFilter extends HttpFilter
{
	private static final long serialVersionUID = 1L;
	  @Autowired
	  JwtUtil  jwtutil;
	  
	  @Autowired
	  UserRepository repository;
	
	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		final String authorizationHeader = request.getHeader("Authorization");
		
		if(authorizationHeader != null)
		{
			String token_frontend=authorizationHeader.substring(7);
			//System.out.print(token_frontend);
			 String username = jwtutil.extractUsername(token_frontend);
			 //System.out.println(username);
			 
			 if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				 User userDetails = repository.findByEmail(username);
				 System.out.print("in fetched userdetails");
				 
				 boolean isValid = jwtutil.validateToken(token_frontend, userDetails);
				 if (isValid) {
		                // Set the authentication in the context to mark the user as authenticated
		                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
		                        userDetails, null, new ArrayList<>());  

		                // Set the authentication context for Spring Security
		               System.out.println(SecurityContextHolder.getContext());
		                SecurityContextHolder.getContext().setAuthentication(authToken);
		                
		                System.out.println("Token is valid for user: " + username);
		            } else {
		                System.out.println("Invalid or expired token");
		            }
			 }
			
		}
		
		chain.doFilter(request, response);
	}

}
