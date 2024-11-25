package com.example.demo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.dto.LoginUserDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entities.Roles;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginUserDTO userInput) {
		return userService.login(userInput);
	}

	@PostMapping("/registration")
	public ResponseEntity<String> registration(@Valid @RequestBody UserDTO userInput) {
		return userService.registration(userInput);
	}

	@GetMapping("/fetchroles")
	public ResponseEntity<List<Roles>> roles() {
		return userService.roles();
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody UserDTO userDetails) {
		return userService.update(id, userDetails);
	}

	@GetMapping("/userinfo")
	public ResponseEntity<?> userInfo(@RequestHeader("Authorization") String tokenHeader) {
		return userService.userInfo(tokenHeader);
	}

	@PostMapping("/forgetpassword")
	public ResponseEntity<String> forgetPassword(@RequestBody String email) {
		return userService.forgetPassword(email);
	}

	@PostMapping("/setnewpassword")
	public ResponseEntity<String> setNewPassword(@RequestHeader("Authorization") String tokenHeader,
			@Valid @RequestBody String newpassword) {

		return userService.newPassowrd(tokenHeader, newpassword);
	}

 
}
