package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dto.ExtractedUserDTO;
import com.example.demo.dto.ForgotPasswordRequestDTO;
import com.example.demo.dto.Newpassword;
import com.example.demo.dto.UserDTO;
import com.example.demo.entities.User;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.repository.UserRepository;

@RestController
public class UserController {
	@Autowired
	UserRepository repository;

	@Autowired
	JwtUtil jwtutil;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	 

	@PostMapping("/login")
	public ResponseEntity<String> handleLogin(@RequestBody UserDTO userInput) {
		// System.out.println("in login method");
		User existingUser = repository.findByEmail(userInput.email);
		if (existingUser != null) {
			// System.out.println(false)

			if (passwordEncoder.matches(userInput.password, existingUser.getPassword())) {
				String generated_token = jwtutil.generateToken(existingUser);
				System.out.println("token is generated and login successfully" + generated_token);
				return ResponseEntity.ok(generated_token);
			}
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect Password");
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect email");
	}

	@PostMapping("/registration")
	public ResponseEntity<UserDTO> handleRegistration(@RequestBody UserDTO userInput) {
		if (repository.findByEmail(userInput.email) == null) {
			User user = new User(userInput.username, userInput.email, passwordEncoder.encode(userInput.password),
					userInput.role);

			repository.save(user);

			return ResponseEntity.ok(userInput);
		}

		throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists!");
	}

	@PostMapping("/validate_token")
	public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String tokenHeader) {
		if (tokenHeader != null) {
			// System.out.println("in validateToken");
			return ResponseEntity.ok("token validation done");
		}

		throw new ResponseStatusException(HttpStatus.CONFLICT, "token not received!");
	}

	@GetMapping("/extractuser")

	public ResponseEntity<List<ExtractedUserDTO>> extractAllUsers() {
		// System.out.print("extract user method");
		List<User> users = repository.findAll();
		List<ExtractedUserDTO> userDTOs = new ArrayList<>();
		// System.out.print("in ExtractedUser controllers");
		for (User user : users) {

			ExtractedUserDTO dto = new ExtractedUserDTO(user.getUsername(), user.getEmail(), user.getId());
			userDTOs.add(dto);
		}
		return ResponseEntity.ok(userDTOs);

	}

	@PreAuthorize("hasRole('admin')")
	@DeleteMapping("deleteUser/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
		if (repository.existsById(id)) {
			repository.deleteById(id);
			// System.out.println("in delete user controller");
			return ResponseEntity.ok("User with given id is deleted successfully");
		}

		else {
			return ResponseEntity.status(404).body("User not found");
		}

	}

	@PostMapping("/forget_password")
	public ResponseEntity<String> forgetPassword(@RequestBody ForgotPasswordRequestDTO email) {
		System.out.println("in forget passowrd block");

		User existUser = repository.findByEmail(email.getEmail());
		System.out.println(existUser.getEmail() + existUser.getPassword() + existUser.getUsername());
		if (existUser == null) {
			return ResponseEntity.status(404).body("user not found");
		} else {

			String forgetpassToken = jwtutil.generateToken(existUser);

			String url = "http://localhost:3000/setpass?token=" + forgetpassToken;

			return ResponseEntity.ok(url);
		}

	}

	@PostMapping("/setnewpassword")
	public ResponseEntity<String> setNewPassword(@RequestHeader("Authorization") String tokenHeader,
			@RequestBody Newpassword newpassword) {

		String extractToken = tokenHeader.substring(7);    /* extract token from headers */

		String userEmail = jwtutil.extractUsername(extractToken);

		User user = repository.findByEmail(userEmail);
		String newPassword = newpassword.getnewPassword();
		String encodedPassword = passwordEncoder.encode(newPassword);   

		user.setpassword(encodedPassword);
		repository.save(user);

		return ResponseEntity.ok("password change successfully!!");
	}

}
