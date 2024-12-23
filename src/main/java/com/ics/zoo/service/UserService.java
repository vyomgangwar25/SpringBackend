package com.ics.zoo.service;

import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
import com.ics.zoo.entities.TokenCheck;
import com.ics.zoo.entities.User;
import com.ics.zoo.enums.ResponseEnum;
import com.ics.zoo.repository.RoleRepository;
import com.ics.zoo.repository.TokenRepository;
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

	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	private UrlConstant urlConstant;

	public ResponseEntity<?> login(LoginUserDTO userInput) {
		User existingUser = getRepository().findByEmail(userInput.getEmail());
		if (existingUser != null) {
			if (passwordEncoder.matches(userInput.getPassword(), existingUser.getPassword())) {
				String generated_token = jwtutil.generateToken(existingUser);
				LoginResponseDTO response = modelMapper.map(existingUser, LoginResponseDTO.class);
				response.setToken(generated_token);
				TokenCheck tokenCheck = new TokenCheck(generated_token, 1, existingUser);
				tokenRepository.save(tokenCheck);
				return ResponseEntity.ok(response);
			}
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseEnum.INCORRECT_PASSWORD.getMessage());
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseEnum.INCORRECT_EMAIL.getMessage());
	}

	public ResponseEntity<String> logout(String tokenHeader) {
		if (tokenHeader != null) {
			String token = tokenHeader.substring(7);
			TokenCheck tcObject = tokenRepository.findByToken(token);
			tcObject.setIsvalid(0);
			tokenRepository.save(tcObject);
			return ResponseEntity.ok(ResponseEnum.TOKEN_BIT_CHANGE.getMessage());
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseEnum.NOT_FOUND.getMessage());
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

	@Cacheable(value = "roles")
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

	public ResponseEntity<?> forgetPassword(String email) {
		User existUser = getRepository().findByEmail(email);
		if (existUser == null) {
			return ResponseEntity.status(404).body(ResponseEnum.USER_NOT_FOUND.getMessage());
		} else {
			String forgetpassToken = jwtutil.generateToken(existUser);
			 Set<String> url = urlConstant.generateUrl(forgetpassToken);
			String subject = ResponseEnum.UPDATE_PASSWORD_REQUEST.getMessage();
			emailService.sendMail(email, subject, url);
			return ResponseEntity.ok(url);
		}
	}

	public ResponseEntity<String> updatepassword(String tokenHeader, PasswordDTO password) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (!passwordEncoder.matches(password.getOldpassword(), user.getPassword())) {
			return ResponseEntity.status(401).body(ResponseEnum.INCORRECT_OLD_PASSWORD.getMessage());
		}
		String encodedPassword = passwordEncoder.encode(password.getNewpassword());
		user.setPassword(encodedPassword);
		user.setAuthority(null);
		getRepository().save(user);
		return ResponseEntity.ok(ResponseEnum.CHANGE_PASSWORD.getMessage());
	}

	public ResponseEntity<String> setpassword(String tokenHeader, PasswordDTO password) {
		if (tokenHeader != null) {
			String key = tokenHeader.substring(7);
			String value = urlConstant.getUrlValue(key);
			String useremail = jwtutil.extractUsername(value);
			User user = getRepository().findByEmail(useremail);
			String encodedPassword = passwordEncoder.encode(password.getNewpassword());
			user.setPassword(encodedPassword);
			user.setAuthority(null);
			getRepository().save(user);
			return ResponseEntity.ok(ResponseEnum.CHANGE_PASSWORD.getMessage());
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResponseEnum.NULL_TOKEN.getMessage());
	}
}