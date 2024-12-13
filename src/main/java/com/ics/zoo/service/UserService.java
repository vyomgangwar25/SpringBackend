package com.ics.zoo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.ics.zoo.dto.LoginResponseDTO;
import com.ics.zoo.dto.LoginUserDTO;
import com.ics.zoo.dto.PasswordDTO;
import com.ics.zoo.dto.UserDTO;
import com.ics.zoo.dto.UserInfoDTO;
import com.ics.zoo.entities.Roles;
import com.ics.zoo.entities.User;
import com.ics.zoo.enums.ResponseEnum;
import com.ics.zoo.repository.RoleRepository;
import com.ics.zoo.repository.UserRepository;
import com.ics.zoo.util.JwtUtil;
import com.ics.zoo.util.UrlConstant;

import ch.qos.logback.core.util.StringUtil;

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
		User existingUser = getRepository().findByEmail(userInput.getEmail());
		if (existingUser != null) {
			if (passwordEncoder.matches(userInput.getPassword(), existingUser.getPassword())) {
				String generated_token = jwtutil.generateToken(existingUser);
				LoginResponseDTO response = modelMapper.map(existingUser, LoginResponseDTO.class);
				response.setToken(generated_token);
				return ResponseEntity.ok(response);
			}
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseEnum.INCORRECT_PASSWORD.getMessage());
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseEnum.INCORRECT_EMAIL.getMessage());
	}

	public ResponseEntity<String> register(UserDTO userInput) {
		if (getRepository().findByEmail(userInput.email) == null) {
			User user = modelMapper.map(userInput, User.class);
			user.setPassword(passwordEncoder.encode(userInput.getPassword()));
			getRepository().save(user);
			return ResponseEntity.ok(ResponseEnum.REGISTRATION.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseEnum.ALREADY_EXIST.getMessage());
	}

	public ResponseEntity<List<Roles>> roles() {
		List<Roles> allroles = roleRepository.findAll();
		return ResponseEntity.ok(allroles);
	}

	public ResponseEntity<String> update(Integer id, UserInfoDTO userDetails) {
		try {
			User user = getRepository().findById(id).get();
			user.setUsername(userDetails.getName());
			user.setEmail(userDetails.getEmail());
			getRepository().save(user);
		} catch (DataIntegrityViolationException e) {
			throw e;
		}
		return ResponseEntity.ok(ResponseEnum.UPDATE.getMessage());
	}

	public ResponseEntity<?> userInfo(String tokenHeader) {
		if (tokenHeader != null) {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			LoginResponseDTO userdata = modelMapper.map(user, LoginResponseDTO.class);
			userdata.setToken(null);
			return ResponseEntity.ok(userdata);
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

	public ResponseEntity<String> newPassowrd(String tokenHeader, PasswordDTO password) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		if (password.getOldpassword().trim().length()!= 0) {
//		if (!passwordEncoder.matches(password.getOldpassword(), user.getPassword())) {
//			return ResponseEntity.status(401).body(ResponseEnum.INCORRECT_OLD_PASSWORD.getMessage());
//		}
//	}
		if(!StringUtil.isNullOrEmpty(password.getOldpassword()))
				{
			if (!passwordEncoder.matches(password.getOldpassword(), user.getPassword())) {
				return ResponseEntity.status(401).body(ResponseEnum.INCORRECT_OLD_PASSWORD.getMessage());
			}
				}

		String encodedPassword = passwordEncoder.encode(password.getNewpassword());
		user.setPassword(encodedPassword);
		user.setAuthority(null);
		getRepository().save(user);
		return ResponseEntity.ok(ResponseEnum.CHANGE_PASSWORD.getMessage());
	}
}