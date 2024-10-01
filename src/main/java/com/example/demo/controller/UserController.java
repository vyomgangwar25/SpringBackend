package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dto.ExtractedUserDTO;
import com.example.demo.dto.ForgotPasswordRequestDTO;
import com.example.demo.dto.Newpassword;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.ZooDTO;
import com.example.demo.entities.User;
import com.example.demo.entities.Zoo;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.ZooRepository;

@RestController
public class UserController {
	@Autowired
	UserRepository repository;

	@Autowired
	JwtUtil jwtutil;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	ZooRepository zoorepository;
	 

	@PostMapping("/login")
	public ResponseEntity<String> handleLogin(@RequestBody UserDTO userInput) {
		 System.out.println("in login method");
		User existingUser = repository.findByEmail(userInput.email);
		if (existingUser != null) {
			// System.out.println(false)

			if (passwordEncoder.matches(userInput.password, existingUser.getPassword())) {
				String generated_token = jwtutil.generateToken(existingUser);
				System.out.println("token is generated and login successfully" + generated_token);
				return ResponseEntity.ok(generated_token);
			}
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect Password");
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect email");
	}

	@PostMapping("/registration")
	public ResponseEntity<?> handleRegistration(@RequestBody UserDTO userInput) {
		if (repository.findByEmail(userInput.email) == null) {
			System.out.println(userInput.username);
			User user = new User(userInput.username, userInput.email, passwordEncoder.encode(userInput.password),
					userInput.role);

			repository.save(user);

			return ResponseEntity.ok("User Registered Successfully");
		}

		return  ResponseEntity.status(409).body("User already exists!");
	}

	@PostMapping("/validate_token")
	public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String tokenHeader) {
		if (tokenHeader != null) {
			 System.out.println("in validateToken");
			return ResponseEntity.ok("token validation done");
		}

		throw new ResponseStatusException(HttpStatus.CONFLICT, "token not received!");
	}

	@GetMapping("/extractuser")

	public ResponseEntity<Map<String, Object>> extractAllUsers(@RequestParam Integer page, @RequestParam Integer pagesize) {
	    PageRequest pageable = PageRequest.of(page, pagesize);
	    //System.out.println("hello" + pageable);
	    
	    
	    Page<User> pageuser = repository.findAll(pageable);
	    
	     
	    Long totalUsers = repository.count();
	    System.out.println("Total users in database are=" + totalUsers);
	    
	   
	    List<ExtractedUserDTO> userDTOs = new ArrayList<>();
	    for (User user : pageuser) {
	        userDTOs.add(new ExtractedUserDTO(user.getUsername(), user.getEmail(), user.getId())); 
	    }

	   
	    Map<String, Object> response = new HashMap<>();
	    response.put("users", userDTOs);
	    response.put("totalUsers", totalUsers);
	    

	    return ResponseEntity.ok(response);
	}


	@PreAuthorize("hasRole('admin')")
	@DeleteMapping("deleteUser/{id}")
	public ResponseEntity<String> deleteUser(@RequestHeader("Authorization") String token, @PathVariable Integer id) {
		System.out.println("token in delete api is"+token);
	   jwtutil.extractRole(token);
		if (repository.existsById(id)) {
			repository.deleteById(id);
			// System.out.println("in delete user controller");
			return ResponseEntity.ok("User with given id is deleted successfully");
		}

		else {
			return ResponseEntity.status(404).body("User not found");
		}

	}

	@PostMapping("/forgetpassword")
	public ResponseEntity<String> forgetPassword(@RequestBody ForgotPasswordRequestDTO email) {
		System.out.println("in forget passowrd block");

		User existUser = repository.findByEmail(email.getEmail());
		
		if (existUser == null) {
			return ResponseEntity.status(404).body("user not found");
		} else {
			System.out.println(existUser.getEmail() + existUser.getPassword() + existUser.getUsername());
			String forgetpassToken = jwtutil.generateToken(existUser);

			String url = "http://localhost:3000/setpass?token=" + forgetpassToken;

			return ResponseEntity.ok(url);
		}

	}

	@PostMapping("/setnewpassword")
	public ResponseEntity<String> setNewPassword(@RequestHeader("Authorization") String tokenHeader,
			@RequestBody Newpassword newpassword) {

		String extractToken = tokenHeader.substring(7);    /* extract token from headers */

		String userEmail = jwtutil.extractUsername(extractToken);

		User user = repository.findByEmail(userEmail);
		String newPassword = newpassword.getnewPassword();
		String encodedPassword = passwordEncoder.encode(newPassword);   

		user.setpassword(encodedPassword);
		repository.save(user);

		return ResponseEntity.ok("password change successfully!!");
	}
    
	
	

	@PostMapping("/zoo")
	public ResponseEntity<?> zooCreation(@RequestBody ZooDTO zooInput)
	{
		//System.out.print("in zoo controller");
	   Zoo newzoo=new Zoo(zooInput.getName(), zooInput.getLocation(), zooInput.getSize());
	   zoorepository.save(newzoo);
		return ResponseEntity.ok(newzoo);
	}
	
	
	@GetMapping("/extractzoo")
	public ResponseEntity<HashMap<String,Object>>Extractzoo(@RequestParam Integer page, @RequestParam Integer pagesize)
	{
        System.out.print("in extractZoo controller");
		    PageRequest pageable = PageRequest.of(page, pagesize);
		    Page<Zoo> pagezoo = zoorepository.findAll(pageable);
		    Long totalzoo = zoorepository.count();
		    System.out.print(totalzoo);
		    
		    List<ZooDTO>zoodata=new ArrayList<>();
		    for(Zoo abc:pagezoo)
		    {
		    	
		    zoodata.add(new ZooDTO(abc.getName(),abc.getLocation(),abc.getSize()));
		    }
		    HashMap<String, Object> response = new HashMap<>();
		    response.put("zoodata",zoodata );
		    response.put("totalzoo", totalzoo);
		    return ResponseEntity.ok(response);
 
		 
	}
	
	
	@PreAuthorize("hasRole('admin')")
	@DeleteMapping("/deletezoo/{id}")
	public ResponseEntity<?>Detelezoo(@PathVariable Integer id)
	{  
		 
		
		if(zoorepository.existsById(id))
		{
			//System.out.println("in delete zoo controller");
			zoorepository.deleteById(id);
			return ResponseEntity.ok("Zoo with a particular id deleted successfully");
		 
		}
		return ResponseEntity.status(404).body("not found");
		 
	}
}
