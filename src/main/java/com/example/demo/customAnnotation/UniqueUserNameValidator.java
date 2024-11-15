package com.example.demo.customAnnotation;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.repository.UserRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
 

public class UniqueUserNameValidator implements ConstraintValidator<UniqueUserName, String>  {
	@Autowired
	UserRepository repository;
	@Override
	public boolean isValid(String username, ConstraintValidatorContext context) {
		if(username==null || username.trim().isEmpty())
		{
			return true;
		}
		return !repository.existsByUsername(username);  
	}
}
