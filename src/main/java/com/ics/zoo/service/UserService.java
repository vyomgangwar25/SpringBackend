package com.ics.zoo.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ics.zoo.dto.LoginResponseDTO;
import com.ics.zoo.dto.LoginUserDTO;
import com.ics.zoo.dto.UserDTO;
import com.ics.zoo.entities.Roles;
import com.ics.zoo.entities.User;
import com.ics.zoo.enums.ResponseEnum;
import com.ics.zoo.repository.RoleRepository;
import com.ics.zoo.repository.UserRepository;
import com.ics.zoo.util.JwtUtil;
import com.ics.zoo.util.UrlConstant;

@Service
public class UserService extends AbstractService<UserRepository> {
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private EmailService emailService;

	@Autowired
	private JwtUtil jwtutil;

	public ResponseEntity<?> login(LoginUserDTO userInput) {
		List<LoginResponseDTO> response = new ArrayList<>();
		User existingUser = getRepository().findByEmail(userInput.getEmail());
		if (existingUser != null) {
			if (passwordEncoder.matches(userInput.getPassword(), existingUser.getPassword())) {
				String generated_token = jwtutil.generateToken(existingUser);
				response.add(new LoginResponseDTO(generated_token, existingUser.getRole(), existingUser.getEmail(),
						existingUser.getUsername(), existingUser.getId()));
				return ResponseEntity.ok(response);
			}
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseEnum.INCORRECT_PASSWORD.getMessage());
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseEnum.INCORRECT_EMAIL.getMessage());
	}

	public ResponseEntity<String> register(UserDTO userInput) {
		if (getRepository().findByEmail(userInput.email) == null) {
			User user = modelMapper.map(userInput, User.class);
			user.setpassword(passwordEncoder.encode(userInput.getPassword()));
			getRepository().save(user);
			return ResponseEntity.ok(ResponseEnum.REGISTRATION.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseEnum.ALREADY_EXIST.getMessage());
	}

	public ResponseEntity<List<Roles>> roles() {
		List<Roles> allroles = roleRepository.findAll();
		return ResponseEntity.ok(allroles);
	}

	public ResponseEntity<String> update(Integer id, UserDTO userDetails) {
		User user = getRepository().findById(id).get();
		user.setUsername(userDetails.getUsername());
		user.setEmail(userDetails.getEmail());
		getRepository().save(user);
		return ResponseEntity.ok(ResponseEnum.UPDATE.getMessage());
	}

	public ResponseEntity<?> userInfo(String tokenHeader) {
		if (tokenHeader != null) {
			String extractToken = tokenHeader.substring(7);
			String userEmail = jwtutil.extractUsername(extractToken);
			User details = getRepository().findByEmail(userEmail);
			List<LoginResponseDTO> response = new ArrayList<>();
			response.add(
					new LoginResponseDTO(null, details.getRole(), userEmail, details.getUsername(), details.getId()));
			return ResponseEntity.ok(response);
		}
		throw new ResponseStatusException(HttpStatus.CONFLICT, ResponseEnum.TOKEN_NOT_RECEIVED.getMessage());
	}

	public ResponseEntity<String> forgetPassword(String email) {
		User existUser = getRepository().findByEmail(email);
		if (existUser == null) {
			return ResponseEntity.status(404).body(ResponseEnum.USER_NOT_FOUND.getMessage());
		} else {
			String forgetpassToken = jwtutil.generateToken(existUser);
			String url = UrlConstant.generateUrl(forgetpassToken);
			String subject = ResponseEnum.UPDATE_PASSWORD_REQUEST.getMessage();
			emailService.sendMail(email, subject, url);
			return ResponseEntity.ok(url);
		}
	}

	public ResponseEntity<String> newPassowrd(String tokenHeader, String newpassword) {
		String extractToken = tokenHeader.substring(7); /* extract token from headers */
		String userEmail = jwtutil.extractUsername(extractToken);
		User user = getRepository().findByEmail(userEmail);
		String encodedPassword = passwordEncoder.encode(newpassword);
		user.setpassword(encodedPassword);
		getRepository().save(user);
		return ResponseEntity.ok(ResponseEnum.CHANGE_PASSWORD.getMessage());
	}
}