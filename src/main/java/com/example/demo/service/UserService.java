package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dto.ForgotPasswordRequestDTO;
import com.example.demo.dto.LoginResponseDTO;
import com.example.demo.dto.LoginUserDTO;
import com.example.demo.dto.Newpassword;
import com.example.demo.dto.UserDTO;
import com.example.demo.entities.User;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository repository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	JwtUtil jwtutil;

	@Autowired
	private ModelMapper modelMapper;

	public ResponseEntity<?> loginUser(LoginUserDTO userInput) {
		List<LoginResponseDTO> response = new ArrayList<>();
		User existingUser = repository.findByEmail(userInput.getEmail());
		if (existingUser != null) {
			if (passwordEncoder.matches(userInput.getPassword(), existingUser.getPassword())) {
				String generated_token = jwtutil.generateToken(existingUser);
				response.add(new LoginResponseDTO(generated_token, existingUser.getRole(), existingUser.getEmail(),
						existingUser.getUsername(), existingUser.getId()));
				return ResponseEntity.ok(response);
			}
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect Password");
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect email");
	}

	public ResponseEntity<?> registrationUser(UserDTO userInput) {
		if (repository.findByEmail(userInput.email) == null) {
			User user = modelMapper.map(userInput, User.class);
			userInput.setPassword(passwordEncoder.encode(userInput.getPassword()));
			repository.save(user);
			return ResponseEntity.ok("User Registered Successfully");
		}
		return ResponseEntity.status(409).body("User already exists!");
	}

	public ResponseEntity<?> updateUser(Integer id, UserDTO userDetails) {
		User user = repository.findById(id).get();
		user.setUsername(userDetails.getUsername());
		user.setEmail(userDetails.getEmail());
		repository.save(user);
		return ResponseEntity.ok("Updated");
	}
	
	public ResponseEntity<?>roleStore(String tokenHeader){
		if (tokenHeader != null) {
			String extractToken = tokenHeader.substring(7);
			String userEmail = jwtutil.extractUsername(extractToken);
			User details = repository.findByEmail(userEmail);
			List<LoginResponseDTO> response = new ArrayList<>();
			response.add(new LoginResponseDTO(null, details.getRole(), userEmail, details.getUsername(), details.getId()));
			return ResponseEntity.ok(response);
		}
		throw new ResponseStatusException(HttpStatus.CONFLICT, "token not received!");
		 
	}
	
	public ResponseEntity<String>forgetPasswordUser(ForgotPasswordRequestDTO email)
	{
		User existUser = repository.findByEmail(email.getEmail());
		if (existUser == null) {
			return ResponseEntity.status(404).body("user not found");
		} else {
			String forgetpassToken = jwtutil.generateToken(existUser);
			String url = "http://localhost:3000/setpass?token=" + forgetpassToken;
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(email.getEmail());
			message.setSubject("password Reset Request");
			message.setText(url);
			mailSender.send(message);
			return ResponseEntity.ok(url);
		}
	}
	
	public ResponseEntity<String>newPassowrd(String tokenHeader,Newpassword newpassword)
	{
		String extractToken = tokenHeader.substring(7); /* extract token from headers */
		String userEmail = jwtutil.extractUsername(extractToken);
		User user = repository.findByEmail(userEmail);
		String newPassword = newpassword.getnewPassword();
		String encodedPassword = passwordEncoder.encode(newPassword);
		user.setpassword(encodedPassword);
		repository.save(user);
		return ResponseEntity.ok("password change successfully!!");
	}
}