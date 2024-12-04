package com.ics.zoo.controller; 
import java.util.HashMap;
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

import com.ics.zoo.dto.ZooRegistrationDTO;
import com.ics.zoo.service.ZooService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/zoo")
public class ZooController extends AbstractController<ZooService> {	
	
	@PostMapping("/create")
	public ResponseEntity<String> create(@Valid @RequestBody ZooRegistrationDTO zooInput) {
		return  getService().register(zooInput);
	}

	@GetMapping("/list")
	public ResponseEntity<HashMap<String, Object>> list(@RequestParam Integer page,@RequestParam Integer pagesize) {
		return getService().extract(page, pagesize);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<String> update(@PathVariable Integer id,@Valid @RequestBody ZooRegistrationDTO updatezoo ){
		return getService().update(id, updatezoo);
	}
	
	@PreAuthorize("hasAuthority('AUTHORITY_3')")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable Integer id) {
		return getService().delete(id);
}
}
