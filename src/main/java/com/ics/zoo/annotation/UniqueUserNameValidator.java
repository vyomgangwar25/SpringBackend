package com.ics.zoo.annotation;

import org.springframework.beans.factory.annotation.Autowired;

import com.ics.zoo.repository.UserRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * UniqueUserNameValidator
 * 
 * @author Vyom Gangwar
 **/

public class UniqueUserNameValidator implements ConstraintValidator<UniqueUserName, String> {
	@Autowired
	private UserRepository repository;

	@Override
	public boolean isValid(String username, ConstraintValidatorContext context) {
		return !repository.existsByUsername(username);
	}
}
