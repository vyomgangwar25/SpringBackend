package com.ics.zoo.filter;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import com.ics.zoo.entities.User;
import com.ics.zoo.service.RolePrivilageService;
import com.ics.zoo.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Custom filter
 * 
 * @author Vyom Gangwar
 */
@Component
public class ZooFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtutil;

	@Autowired
	private RolePrivilageService rolePrivilageService;

	/**
	 * this method checks the token validation and set the user information and Authority in the context
	 * @param request,response,filterChain
	 * @throws ServletException,IOException
	 * **/
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
				User userDetails = jwtutil.validateToken(token_frontend, username);
				if (userDetails != null) {

					User user = (User) rolePrivilageService.loadUserByUsername(userDetails);

					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
							null, user.getAuthority());
					SecurityContextHolder.getContext().setAuthentication(authToken);
				} else {
					System.out.println("Invalid or expired token");
				}
			}
		}

		filterChain.doFilter(request, response);
	}

}
