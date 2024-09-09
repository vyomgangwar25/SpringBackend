package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dto.UserDTO;
import com.example.demo.entities.User;
import com.example.demo.repository.UserRepository;

@RestController
 
public class UserController 
{
	@Autowired
	UserRepository repository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@GetMapping("/")
	public String hello(@RequestBody UserDTO userInput) {
		String st=userInput.username;
		String hashedpassword=passwordEncoder.encode(st);
		return "hello";
	}
	@GetMapping("/getusers/{email}")
	public ResponseEntity<UserDTO> getUserData(@PathVariable String email) 
	{
	User data=repository.findByEmail(email);
	return null;
	}

	@PostMapping("/login")
	public String handleLogin(@RequestBody UserDTO userInput)
	{
		User existingUser = repository.findByEmail(userInput.email);
		if (existingUser != null){
			//System.out.println(false)
			if(existingUser.password.equals(userInput.password))
				return "login successfully";
			return "Incorrect Password";
		}
		return "User with this email not exists! please signup first";   

	}

	@PostMapping("/registration")
	public ResponseEntity<UserDTO> handleRegistration(@RequestBody UserDTO userInput) 
	{
		if (repository.findByEmail(userInput.email) == null)
		{
			User user = new User(userInput.username, userInput.email, userInput.password);
			repository.save(user);
			
			return ResponseEntity.ok(userInput);
		}
		throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists!");
	}

}
