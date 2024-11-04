package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.midi.SysexMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dto.ExtractedUserDTO;
import com.example.demo.dto.ForgotPasswordRequestDTO;
import com.example.demo.dto.Newpassword;
import com.example.demo.dto.UpdatePasswordDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entities.User;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.ZooRepository;

@RestController
public class UserController {
	@Autowired
	UserRepository repository;

	@Autowired
	JwtUtil jwtutil;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	ZooRepository zoorepository;
	
	@Autowired
	JavaMailSender mailSender;

	@PostMapping("/login")
	public ResponseEntity<?> handleLogin( @RequestBody UserDTO userInput) {
		Map<String, Object> response = new HashMap<>();
		User existingUser = repository.findByEmail(userInput.email);
		if (existingUser != null) {
			if (passwordEncoder.matches(userInput.password, existingUser.getPassword())) {
				String generated_token = jwtutil.generateToken(existingUser);
				response.put("token", generated_token);
				response.put("role", existingUser.getRole());
				response.put("email", existingUser.getEmail());
				response.put("name", existingUser.getUsername());
				response.put("id", existingUser.getId());

				return ResponseEntity.ok(response);
			}
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect Password");
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect email");
	}

	@PostMapping("/registration")
	public ResponseEntity<String> handleRegistration(@RequestBody UserDTO userInput) {
		if (repository.findByEmail(userInput.email) == null) {
   User user = new User(userInput.username, userInput.email, passwordEncoder.encode(userInput.password),userInput.role);
			repository.save(user);
			return ResponseEntity.ok("User Registered Successfully");
		}
		return ResponseEntity.status(409).body("User already exists!");
	}
   
	
	@PutMapping("/updateuser/{id}")
	public ResponseEntity<?>userUpdate(@PathVariable Integer id,@RequestBody UserDTO userDetails)
	{
	User user=repository.findById(id).get();
	 user.setUsername(userDetails.getUsername());
	 user.setEmail(userDetails.getEmail());
	 repository.save(user);
		return ResponseEntity.ok("Updated");
	}
	
	@GetMapping("/validate_token")
	public ResponseEntity<HashMap<String, Object>> validateToken(@RequestHeader("Authorization") String tokenHeader) {
		if (tokenHeader != null) {
			String extractToken = tokenHeader.substring(7);
			String userEmail = jwtutil.extractUsername(extractToken);
			User details = repository.findByEmail(userEmail);
			String role = details.getRole();
			Integer userId=details.getId();

			HashMap<String, Object> response = new HashMap<>();
			response.put("name", details.getUsername());
			response.put("userEmail",  userEmail);
			response.put("role", role);
			response.put("id", userId);
			return ResponseEntity.ok(response);
		}

		throw new ResponseStatusException(HttpStatus.CONFLICT, "token not received!");
	}

	@GetMapping("/extractuser")
	public ResponseEntity<Map<String, Object>> extractAllUsers(@RequestParam Integer page, @RequestParam Integer pagesize) {
		PageRequest pageable = PageRequest.of(page, pagesize);
		Page<User> pageuser = repository.findAll(pageable);
		Long totalUsers = repository.count();
		List<ExtractedUserDTO> userDTOs = new ArrayList<>();
		for (User user : pageuser) {
			userDTOs.add(new ExtractedUserDTO(user.getUsername(), user.getEmail(), user.getId()));
		}

		Map<String, Object> response = new HashMap<>();
		response.put("users", userDTOs);
		response.put("totalUsers", totalUsers);

		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasRole('admin')")
	@DeleteMapping("deleteUser/{id}")
	public ResponseEntity<String> deleteUser(@RequestHeader("Authorization") String tokenHeader,@PathVariable Integer id) {

		if (tokenHeader != null) {
			if (repository.existsById(id)) {
				repository.deleteById(id);
				return ResponseEntity.ok("User  deleted successfully");
			}
		}
		return ResponseEntity.status(404).body("User not found");
	}
	

	@PostMapping("/forgetpassword")
	public ResponseEntity<String> forgetPassword( @RequestBody ForgotPasswordRequestDTO email) {

		User existUser = repository.findByEmail(email.getEmail());
		if (existUser == null) {
			return ResponseEntity.status(404).body("user not found");
		} else {
			String forgetpassToken = jwtutil.generateToken(existUser);
			String url = "http://localhost:3000/setpass?token=" + forgetpassToken;
			SimpleMailMessage message=new SimpleMailMessage();
			message.setTo(email.getEmail());
			message.setSubject("password Reset Request");
			message.setText(url);
			mailSender.send(message);
			return ResponseEntity.ok(url);
		}
	}

	@PostMapping("/setnewpassword")
	public ResponseEntity<String> setNewPassword(@RequestHeader("Authorization") String tokenHeader,@RequestBody Newpassword newpassword) {
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
