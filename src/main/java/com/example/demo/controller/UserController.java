package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dto.ExtractedUserDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entities.User;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.repository.UserRepository;

@RestController
public class UserController 
{
	@Autowired
	UserRepository repository;

	@Autowired
	JwtUtil jwtutil;
	
	 @Autowired
	private  BCryptPasswordEncoder passwordEncoder;

	@PostMapping("/login")
	public ResponseEntity<String> handleLogin(@RequestBody UserDTO userInput)
	{
		User existingUser = repository.findByEmail(userInput.email);
		if (existingUser != null){
			//System.out.println(false)
			
			if(passwordEncoder.matches(userInput.password, existingUser.getPassword()))
			{
				//System.out.println(existingUser.getUsername());
				String generated_token=jwtutil.generateToken(existingUser);
				//System.out.println("token is "+generated_token);
				return ResponseEntity.ok(generated_token);
			}
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect Password");
		}
		throw new ResponseStatusException(HttpStatus.CONFLICT, " User doesnot exist!!");
	}

	@PostMapping("/registration")
	public ResponseEntity<UserDTO> handleRegistration(@RequestBody UserDTO userInput) 
	{
		if (repository.findByEmail(userInput.email) == null)
		{
			User user = new User(userInput.username, userInput.email, userInput.password);
			//System.out.println(user.getEmail());
			 
			String hashedPassword=passwordEncoder.encode(user.getPassword());
			//System.out.println("encoded pasword is "+hashedPassword);
			user.setPassowrd(hashedPassword);
			repository.save(user);

			return ResponseEntity.ok(userInput);
		}
		throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists!");
	}

	@PostMapping("/validate_token")
	public ResponseEntity<?>validateToken(@RequestHeader("Authorization") String tokenHeader)
	{
		if(tokenHeader !=null)
		{
			//System.out.println("in validateToken");
			return ResponseEntity.ok().build();
		}

		throw new ResponseStatusException(HttpStatus.CONFLICT, "token not received!");
	}
	
	@GetMapping("/ExtractUser")
	
	public ResponseEntity<List<ExtractedUserDTO>>extractAllUsers(){ 
		List<User> users = repository.findAll();   
	    List<ExtractedUserDTO> userDTOs = new ArrayList<>();    
        //System.out.print("in ExtractedUser controllers");
	    for (User user : users) {
	         
	        ExtractedUserDTO dto = new ExtractedUserDTO(user.getUsername(), user.getEmail());
	          userDTOs.add(dto);
	    }
	    return ResponseEntity.ok(userDTOs);
   
}
}


