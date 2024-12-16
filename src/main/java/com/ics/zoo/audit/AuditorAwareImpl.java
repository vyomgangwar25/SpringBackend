package com.ics.zoo.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ics.zoo.entities.User;

public class AuditorAwareImpl implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		try 
		{
			return Optional.of(((User) authentication.getPrincipal()).getUsername().toString());
		} catch (ClassCastException e) 
		{
			return Optional.of("System");
		}
	}

}
