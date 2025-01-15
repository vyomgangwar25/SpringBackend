package com.ics.zoo.service;

import java.time.LocalTime;
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
import com.ics.zoo.dto.TokenDTO;
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

/**
 * User Service
 * 
 * @author Vyom Gangwar
 */

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

	@Autowired
	private RefreshTokenService refreshTokenService;

	/**
	 * this method is used for login it first check if user is found in database or
	 * not.If not found then return "user not found" else generate the token and
	 * save the token, user object in tokenCheck table
	 * 
	 * @param userInput
	 * @return information of user like name,role etc
	 * @author Vyom Gangwar
	 */
	public ResponseEntity<?> login(LoginUserDTO userInput) {
		try {
			User existingUser = getRepository().findByEmail(userInput.getEmail());
			if (existingUser != null) {
				if (passwordEncoder.matches(userInput.getPassword(), existingUser.getPassword())) {
					String generated_token = jwtutil.generateToken(existingUser);
					LoginResponseDTO response = modelMapper.map(existingUser, LoginResponseDTO.class);
					response.setToken(generated_token);
					String refreshToken = refreshTokenService.generateToken(existingUser);
					response.setRefreshToken(refreshToken);
					TokenCheck tokencheck = TokenCheck.builder().token(generated_token).isvalid(true).user(existingUser)
							.rtoken(refreshToken).build();
					tokenRepository.save(tokencheck);
					return ResponseEntity.ok(response);
				}
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
						.body(ResponseEnum.INCORRECT_PASSWORD.getMessage());
			}
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseEnum.INCORRECT_EMAIL.getMessage());
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}

	}

	/**
	 * this is used to logout the user it checks the token in table and if the token
	 * found then set the bit to 0
	 * 
	 * @param token
	 * @return ResponseEntity<String>
	 * @author Vyom Gangwar
	 */

	public ResponseEntity<String> logout(String tokenHeader) {
		try {
			if (tokenHeader != null) {
				String token = tokenHeader.substring(7);
				TokenCheck tokenCheck = tokenRepository.findByToken(token);
				tokenCheck.setIsvalid(false);
				tokenRepository.save(tokenCheck);
				return ResponseEntity.ok(ResponseEnum.TOKEN_BIT_CHANGE.getMessage());
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseEnum.NOT_FOUND.getMessage());
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}

	}

	/**
	 * this is used to check the validity of token and refreshToken. if token is
	 * expired and refreshToken is not expired then generate new token. if
	 * both(token and refreshToken) are expired then throw error.
	 * 
	 * @param refreshToken
	 * @return ResponseEntity<String>
	 * @author Vyom Gangwar
	 */

	public ResponseEntity<String> refreshtoken(TokenDTO refreshToken) {
		if (refreshToken == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "refresh token not present");
		}
		TokenCheck tokenCheck = tokenRepository.findByRtoken(refreshToken.getToken());
		boolean isValid = refreshTokenService.validateToken(refreshToken.getToken());
		if (isValid) {
			String newToken = jwtutil.generateToken(tokenCheck.getUser());
			tokenCheck.setToken(newToken);
			tokenCheck.setCreatedAt(LocalTime.now().withNano(0));
			tokenRepository.save(tokenCheck);
			return ResponseEntity.ok(newToken);
		} else {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh Token expired");
		}

	}

	/**
	 * this method is used to create new user. it first check if the user with given
	 * email already exist or not. if the user is not found then only register the
	 * user else return "user already exist".
	 * 
	 * @param userInput
	 * @return ResponseEntity<String>
	 * @author Vyom Gangwar
	 */

	public ResponseEntity<String> register(UserDTO userInput) {
		try {
			if (getRepository().findByEmail(userInput.email) == null) {
				User user = modelMapper.map(userInput, User.class);
				user.setPassword(passwordEncoder.encode(userInput.getPassword()));
				user.setRoleId(userInput.getRoleId());
				getRepository().save(user);
				return ResponseEntity.ok(ResponseEnum.REGISTRATION.getMessage());
			}
			return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseEnum.ALREADY_EXIST.getMessage());
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}

	/**
	 * this method is used to fetch the list of role.
	 * 
	 * @return list of role
	 * @author Vyom Gangwar
	 */
	public ResponseEntity<List<Roles>> roles() {
		List<Roles> allroles = roleRepository.findAll();
		return ResponseEntity.ok(allroles);
	}

	/**
	 * this method update the user. it first find the user object using Id and then
	 * update the info .
	 * 
	 * @param id,userDetails
	 * @author Vyom Gangwar
	 */

	public ResponseEntity<String> updateUser(Integer id, UserInfoDTO userDetails) {
		try {
			User user = getRepository().findById(id).get();
			user.setUsername(userDetails.getName());
			user.setEmail(userDetails.getEmail());
			getRepository().save(user);
			return ResponseEntity.ok(ResponseEnum.UPDATE.getMessage());
		} catch (DataIntegrityViolationException e) {
			throw e;
		}
	}

	/**
	 * this method is used to find the user info. it takes the token as param and
	 * finds the user object from context then return the details
	 * 
	 * @param token
	 * @author Vyom Gangwar
	 */

	public ResponseEntity<?> userInfo(String tokenHeader) {
		if (tokenHeader != null) {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			LoginResponseDTO userdata = modelMapper.map(user, LoginResponseDTO.class);
			userdata.setToken(null);
			return ResponseEntity.ok(userdata);
		}
		throw new ResponseStatusException(HttpStatus.CONFLICT, ResponseEnum.TOKEN_NOT_RECEIVED.getMessage());
	}

	/**
	 * this method firstly check the user with the particular email and if user is
	 * not found then return "not found". if user is present then generate token and
	 * set the token in hashmap with random key and return key as response
	 * 
	 * @param email
	 * @return key
	 * @author Vyom Gangwar
	 */
	public ResponseEntity<?> forgetPassword(String email) {
		try {
			User existUser = getRepository().findByEmail(email);
			if (existUser == null) {
				return ResponseEntity.status(404).body(ResponseEnum.USER_NOT_FOUND.getMessage());
			} else {
				String forgetpassToken = jwtutil.generateToken(existUser);
				Integer key = urlConstant.urlKey(forgetpassToken);
				String value = urlConstant.getUrlValue(key);
				String subject = ResponseEnum.UPDATE_PASSWORD_REQUEST.getMessage();
				emailService.sendMail(email, subject, value);
				return ResponseEntity.ok(key);
			}
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}

	/**
	 * this method is used to update the password . it checks the current password .
	 * if current password match then update with new password
	 * 
	 * @param token,password
	 * @return ResponseEntity<String>
	 * @author Vyom Gangwar
	 */

	public ResponseEntity<String> updatePassword(PasswordDTO password) {
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (!passwordEncoder.matches(password.getOldpassword(), user.getPassword())) {
				return ResponseEntity.status(401).body(ResponseEnum.INCORRECT_OLD_PASSWORD.getMessage());
			}
			String encodedPassword = passwordEncoder.encode(password.getNewpassword());
			user.setPassword(encodedPassword);
			getRepository().save(user);
			return ResponseEntity.ok(ResponseEnum.CHANGE_PASSWORD.getMessage());
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}

	/**
	 * this is used to set new password. it receive key as @param,then find the
	 * vlaue(token) for that key and set the new password
	 * 
	 * @param newPassword,token(key)
	 * @return ResponseEntity<String>
	 * @author Vyom Gangwar
	 */
	public ResponseEntity<String> setpassword(Integer key, PasswordDTO password) {
		try {
			String value = urlConstant.getUrlValue(key);
			if (value==null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseEnum.EXPIRED_TOKEN.getMessage());
			}
			String userEmail = jwtutil.extractUsername(value);
			User user = getRepository().findByEmail(userEmail);
			String encodedPassword = passwordEncoder.encode(password.getNewpassword());
			user.setPassword(encodedPassword);
			getRepository().save(user);
			urlConstant.removeKey(key);

			return ResponseEntity.ok(ResponseEnum.CHANGE_PASSWORD.getMessage());
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}
}