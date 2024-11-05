package com.example.demo.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

   
@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex)
{
    final Map<String, String> errors = new HashMap<>();

    // Iterate through all errors and extract field names and error messages
    ex.getBindingResult().getAllErrors().forEach(error-> {
        final String fieldName = ((FieldError) error).getField();
        final String errorMessage = error.getDefaultMessage();
        errors.put(fieldName, errorMessage);
    });

    // Return a ResponseEntity with the map of errors and a 400 status code
    return ResponseEntity.badRequest().body(errors);
}
}