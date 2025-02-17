package com.ics.zoo.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * \ GlobalExceptionHandler
 * 
 * @author Vyom Gangwar
 * 
 * 
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
	/**
	 * this method is used to handle @MethodArgumentNotValidException type exception
	 * Iterate through all errors and extract field names and error messages
	 * 
	 * @param exception
	 * @return ResponseEntity<Map<String, String>>
	 **/
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		final Map<String, String> errors = new HashMap<>();
		// Iterate through all errors and extract field names and error messages
		ex.getBindingResult().getAllErrors().forEach(error -> {
			final String fieldName = ((FieldError) error).getField();
			final String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return ResponseEntity.badRequest().body(errors);
	}

	/**
	 * this method is used to handle @NoSuchElementException type exception
	 */
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<String> handleNullException(NoSuchElementException exception) {
		return ResponseEntity.badRequest().body(exception.getMessage());
	}

	/**
	 * this method is used to handle @DataIntegrityViolationException type exception
	 */

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<?> handleDuplicateEntryException(DataIntegrityViolationException exc) {
		return ResponseEntity.badRequest().body(new ResponseData("username already present"));
	}

}