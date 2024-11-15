package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.example.demo.dto.LoginResponseDTO;
import com.example.demo.dto.LoginUserDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entities.Roles;
import com.example.demo.entities.User;
import com.example.demo.enums.ResponseEnum;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.UrlConstant;

import jakarta.validation.Valid;

@Service
public class UserService {
	@Autowired
	private UserRepository repository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private Email emailSevice;

	@Autowired
	private JwtUtil jwtutil;

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
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseEnum.User_Password.getMessage());
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseEnum.User_Email.getMessage());
	}

	public ResponseEntity<String> registrationUser(UserDTO userInput) {
		if (repository.findByEmail(userInput.email) == null) {
			User user = modelMapper.map(userInput, User.class);
			user.setpassword(passwordEncoder.encode(userInput.getPassword()));
			repository.save(user);
			return ResponseEntity.ok(ResponseEnum.Registration.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseEnum.user_Already_exist.getMessage());
	}

	public ResponseEntity<List<Roles>> fetchRoles() {
		List<Roles> allroles = roleRepository.findAll();
		return ResponseEntity.ok(allroles);
	}

	public ResponseEntity<String> updateUser(Integer id, UserDTO userDetails) {
		User user = repository.findById(id).get();
		user.setUsername(userDetails.getUsername());
		user.setEmail(userDetails.getEmail());
		repository.save(user);
		return ResponseEntity.ok(ResponseEnum.Update.getMessage());
	}

	public ResponseEntity<?> roleStore(String tokenHeader) {
		if (tokenHeader != null) {
			String extractToken = tokenHeader.substring(7);
			String userEmail = jwtutil.extractUsername(extractToken);
			User details = repository.findByEmail(userEmail);
			List<LoginResponseDTO> response = new ArrayList<>();
			response.add(
					new LoginResponseDTO(null, details.getRole(), userEmail, details.getUsername(), details.getId()));
			return ResponseEntity.ok(response);
		}
		throw new ResponseStatusException(HttpStatus.CONFLICT, "token not received!");
	}

	public ResponseEntity<String> forgetPasswordUser(@Valid String email) {
		User existUser = repository.findByEmail(email);
		if (existUser == null) {
			return ResponseEntity.status(404).body("user not found");
		} else {
			String forgetpassToken = jwtutil.generateToken(existUser);
			String url = UrlConstant.generateUrl(forgetpassToken);
			String subject = "Update Password Request";
			emailSevice.sendMail(email, subject, url);
			return ResponseEntity.ok(url);
		}
	}

	public ResponseEntity<String> newPassowrd(String tokenHeader, String newpassword) {
		String extractToken = tokenHeader.substring(7); /* extract token from headers */
		String userEmail = jwtutil.extractUsername(extractToken);
		User user = repository.findByEmail(userEmail);
		String encodedPassword = passwordEncoder.encode(newpassword);
		user.setpassword(encodedPassword);
		repository.save(user);
		return ResponseEntity.ok(ResponseEnum.Chnage_Password.getMessage());
	}
}