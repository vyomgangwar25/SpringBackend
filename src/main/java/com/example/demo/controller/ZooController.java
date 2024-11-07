package com.example.demo.controller; 
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.dto.ZooRegistrationDTO;
import com.example.demo.service.ZooService;

import jakarta.validation.Valid;

@RestController
public class ZooController {	
	
	@Autowired
	private ZooService zooService;

	@PostMapping("/zoo")
	public ResponseEntity<?> zooCreation(@Valid @RequestBody ZooRegistrationDTO zooInput) {
		return  zooService.zooRegistration(zooInput);
	}

	@GetMapping("/extractzoo")
	public ResponseEntity<HashMap<String, Object>> Extractzoo(@RequestParam Integer page,@RequestParam Integer pagesize) {
		return zooService.extractZooData(page, pagesize);
	}

	@PutMapping("/updatezoo/{id}")
	public ResponseEntity<?>Updatezoo(@PathVariable Integer id,@Valid @RequestBody ZooRegistrationDTO updatezoo ){
		return zooService.updateZooData(id, updatezoo);
	}
	
	@PreAuthorize("hasRole('admin')")
	@DeleteMapping("/deletezoo/{id}")
	public ResponseEntity<String> Detelezoo(@PathVariable Integer id) {
		return zooService.deleteZooData(id);
}
}
