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
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginUserDTO userInput) {
		return getService().login(userInput);
	}

	@PostMapping("/registration")
	public ResponseEntity<String> registration(@Valid @RequestBody UserDTO userInput) {
		return getService().register(userInput);
	}

	@PostMapping("/logout")
	public ResponseEntity<String> logout(@RequestHeader("Authorization") String tokenHeader) {
		return getService().logout(tokenHeader);
	}

	@GetMapping("/fetchroles")
	public ResponseEntity<List<Roles>> roles() {
		return getService().roles();
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody UserInfoDTO userDetails) {
		return getService().update(id, userDetails);
	}

	@GetMapping("/userinfo")
	public ResponseEntity<?> userInfo(@RequestHeader("Authorization") String tokenHeader) {
		return getService().userInfo(tokenHeader);
	}

	@PostMapping("/forgetpassword")
	public ResponseEntity<?> forgetPassword(@RequestBody String email) {
		return getService().forgetPassword(email);
	}

	@PostMapping("/updatepassword") 
	public ResponseEntity<String>  updatepassword(@RequestHeader("Authorization") String tokenHeader,
			@Valid @RequestBody PasswordDTO password) {
		return getService().updatepassword(tokenHeader, password);
	}

	@PostMapping("/setnewpassword")
	public ResponseEntity<String> setpassword(@RequestHeader("Authorization2") String tokenHeader,
			  @RequestBody PasswordDTO password) {
		return getService().setpassword(tokenHeader, password);
	}
}
