package com.ics.zoo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
  

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy =UniqueUserNameValidator.class )
/**
 * this  is used to verify the unique name in database
 * 
 * @author Vyom Gangwar 
 * 
 * 
 */
public @interface UniqueUserName {
	String message() default "user with this name is already exists";
	Class<?>[] groups() default {}; 
	Class<? extends Payload>[] payload() default {};
}
