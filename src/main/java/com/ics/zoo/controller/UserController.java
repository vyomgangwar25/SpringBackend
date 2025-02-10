package com.ics.zoo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ics.zoo.dto.LoginUserDTO;
import com.ics.zoo.dto.PasswordDTO;
import com.ics.zoo.dto.TokenDTO;
import com.ics.zoo.dto.UserDTO;
import com.ics.zoo.dto.UserInfoDTO;
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
	 * this method is used to give the list of user
	 * 
	 * @return user list
	 * @author Vyom Gangwar
	 */

	@GetMapping("/list")
	public ResponseEntity<?> userList() {
		return getService().userList();
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
	 * 
	 * @param refreshToken
	 * @author Vyom Gangwar
	 **/
	@PostMapping("/refreshtoken")
	public ResponseEntity<String> refreshtoken(@Valid @RequestBody TokenDTO refreshToken) {
		return getService().refreshtoken(refreshToken);
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
	public ResponseEntity<?> forgetPassword(@RequestBody LoginUserDTO email) {
		return getService().forgetPassword(email.getEmail());
	}

	/**
	 * this method is used to update the user role
	 * 
	 * @param roleId
	 * @return ResponseEntity<String>
	 * @author Vyom Gangwar
	 **/

	@PutMapping("/updaterole/{roleId}")
	public ResponseEntity<String> updateRole(@RequestParam Integer userId, @PathVariable Integer roleId) {

		return getService().updateRole(userId, roleId);
	}

	/**
	 * this method is used to update the password
	 * 
	 * @param token,password
	 * @return ResponseEntity<String>
	 * @author Vyom Gangwar
	 */

	@PostMapping("/updatepassword")
	public ResponseEntity<String> updatepassword(@Valid @RequestBody PasswordDTO password) {
		return getService().updatePassword(password);
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
	public ResponseEntity<String> setpassword(@Valid @RequestParam Integer tokenKey,
			@RequestBody PasswordDTO password) {
		return getService().setpassword(tokenKey, password);
	}

}
