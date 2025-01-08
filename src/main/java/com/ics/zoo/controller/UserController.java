package com.ics.zoo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ics.zoo.dto.LoginUserDTO;
import com.ics.zoo.dto.PasswordDTO;
import com.ics.zoo.dto.TokenDTO;
import com.ics.zoo.dto.UserDTO;
import com.ics.zoo.dto.UserInfoDTO;
import com.ics.zoo.entities.Roles;
import com.ics.zoo.service.UserService;

import jakarta.validation.Valid;

/**
 * user controller
 * 
 * @author Vyom Gangwar
 * @since 5-Oct-2024
 */

@RestController
@RequestMapping("/user")
public class UserController extends AbstractController<UserService> {
	/**
	 * this method is used for login
	 * 
	 * @param userInput
	 * @return information of user like name,role etc
	 * @author Vyom Gangwar
	 */

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginUserDTO userInput) {
		return getService().login(userInput);
	}

	/**
	 * this method is used to create new user
	 * 
	 * @param userInput
	 * @return ResponseEntity<String>
	 * @author Vyom Gangwar
	 */

	@PostMapping("/registration")
	public ResponseEntity<String> registration(@Valid @RequestBody UserDTO userInput) {
		return getService().register(userInput);
	}

	/**
	 * this is used to logout the user
	 * 
	 * @param token
	 * @return ResponseEntity<String>
	 * @author Vyom Gangwar
	 */

	@PostMapping("/logout")
	public ResponseEntity<String> logout(@RequestHeader("Authorization") String tokenHeader) {
		return getService().logout(tokenHeader);
	}

	
	/**
	 * this function is used to gerenate the new jwt token if refresh token is valid
	 * @param refreshToken
	 * @author Vyom Gangwar
	 * **/
	@PostMapping("/refreshtoken")
	public ResponseEntity<String>refreshtoken(@RequestBody TokenDTO refreshToken){
		return  getService().refreshtoken(refreshToken);
	}
	
	/**
	 * this is used to fetch role
	 * 
	 * @return ResponseEntity<List<Roles>>
	 * @author Vyom Gangwar
	 */
	@GetMapping("/fetchroles")
	public ResponseEntity<List<Roles>> roles() {
		return getService().roles();
	}

	/**
	 * this is used to update the user info
	 * 
	 * @param id,userDetails
	 * @return ResponseEntity<String>
	 * @author Vyom gangwar
	 **/
	@PutMapping("/update/{id}")
	public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody UserInfoDTO userDetails) {
		return getService().updateUser(id, userDetails);
	}

	/**
	 * this return the user info
	 * 
	 * @param token
	 * @author Vyom Gangwar
	 */

	@GetMapping("/userinfo")
	public ResponseEntity<?> userInfo(@RequestHeader("Authorization") String tokenHeader) {
		return getService().userInfo(tokenHeader);
	}

	/**
	 * this method send the reset password link on user email and also return key
	 * 
	 * @param email
	 * @return key
	 * @author Vyom Gangwar
	 */
	@PostMapping("/forgetpassword")
	public ResponseEntity<String> forgetPassword(@RequestBody String email) {
		return getService().forgetPassword(email);
	}

	/**
	 * this method is used to update the password
	 * 
	 * @param token,password
	 * @return ResponseEntity<String>
	 * @author Vyom Gangwar
	 */

	@PostMapping("/updatepassword")
	public ResponseEntity<String> updatepassword(@RequestHeader("Authorization") String tokenHeader,
			@Valid @RequestBody PasswordDTO password) {
		return getService().updatePassword(tokenHeader, password);
	}

	/**
	 * this is used to set new password
	 * 
	 * @param newPassword,token(key)
	 * @return ResponseEntity<String>
	 * @author Vyom Gangwar
	 * 
	 */
	@PostMapping("/setnewpassword")
	public ResponseEntity<String> setpassword(@RequestHeader("Authorization2") String tokenHeader,
			@RequestBody PasswordDTO password) {
		return getService().setpassword(tokenHeader, password);
	}
}
