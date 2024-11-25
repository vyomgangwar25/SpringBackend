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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.dto.ZooRegistrationDTO;
import com.example.demo.entities.Zoo;
import com.example.demo.service.ZooService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/zoo")
public class ZooController {	
	
	@Autowired
	private ZooService zooService;

	@PostMapping("/create")
	public ResponseEntity<Zoo> create(@Valid @RequestBody ZooRegistrationDTO zooInput) {
		return  zooService.registration(zooInput);
	}

	@GetMapping("/list")
	public ResponseEntity<HashMap<String, Object>> list(@RequestParam Integer page,@RequestParam Integer pagesize) {
		return zooService.extract(page, pagesize);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<String> update(@PathVariable Integer id,@Valid @RequestBody ZooRegistrationDTO updatezoo ){
		return zooService.update(id, updatezoo);
	}
	
	//@PreAuthorize("hasRole('admin')")
	@PreAuthorize("hasAuthority('AUTHORITY_3')")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable Integer id) {
		return zooService.delete(id);
}
}
