package com.example.demo.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.entities.User;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Scope("prototype")
public class ZooFilter extends OncePerRequestFilter { /* custom filter */

	@Autowired
	JwtUtil jwtutil;

	@Autowired
	UserRepository repository;

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String authorizationHeader = request.getHeader("Authorization");

		if (authorizationHeader != null) {
			String token_frontend = authorizationHeader.substring(7);
			if (ObjectUtils.isEmpty(token_frontend) || token_frontend.equals("null")) {
				filterChain.doFilter(request, response);
				return;
			}

			String username = jwtutil.extractUsername(token_frontend);

			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				User userDetails = repository.findByEmail(username);

				boolean isValid = jwtutil.validateToken(token_frontend, userDetails);
				if (isValid) {
					// Set the authentication in the context to mark the user as authenticated
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
							null, userDetails.getAuthorities());

					SecurityContextHolder.getContext().setAuthentication(authToken);
				} else {
					System.out.println("Invalid or expired token");
				}
			}

		}

		filterChain.doFilter(request, response);
	}

}
