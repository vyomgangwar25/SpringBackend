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
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.dto.LoginUserDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entities.Roles;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<?> handleLogin(@Valid @RequestBody LoginUserDTO userInput) {
		return userService.loginUser(userInput);
	}

	@PostMapping("/registration")
	public ResponseEntity<String> handleRegistration(@Valid @RequestBody UserDTO userInput) {
		return userService.registrationUser(userInput);
	}

	@GetMapping("/fetchroles")
	public ResponseEntity<List<Roles>> handleRoles() {
		return userService.fetchRoles();
	}

	@PutMapping("/updateuser/{id}")
	public ResponseEntity<String> userUpdate(@PathVariable Integer id, @RequestBody UserDTO userDetails) {
		return userService.updateUser(id, userDetails);
	}

	@GetMapping("/validate_token")
	public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String tokenHeader) {
		return userService.roleStore(tokenHeader);
	}

	@PostMapping("/forgetpassword")
	public ResponseEntity<String> forgetPassword(@Valid @RequestBody String email) {
		return userService.forgetPasswordUser(email);
	}

	@PostMapping("/setnewpassword")
	public ResponseEntity<String> setNewPassword(@RequestHeader("Authorization") String tokenHeader,
			@Valid @RequestBody String newpassword) {

		return userService.newPassowrd(tokenHeader, newpassword);
	}
	
	@PostMapping("/newpath")
	public String abc(@RequestBody String abc)
	{
		System.out.println("the data is "+ abc);
		return "hello";
	}
}
