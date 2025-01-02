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

/**
 * zoo controller
 * 
 * @author Vyom Gangwar
 * @since 15-Oct-2024
 * 
 */
@RestController
@RequestMapping("/zoo")
public class ZooController extends AbstractController<ZooService> {

	/**
	 * this is used to create new zoo
	 * 
	 * @param zooinput
	 * @return ResponseEntity<String> create
	 * @author Vyom Gangwar
	 */
	@PostMapping("/create")
	public ResponseEntity<String> create(@Valid @RequestBody ZooRegistrationDTO zooInput) {
		return getService().register(zooInput);
	}

	/**
	 * this method return list of zoo
	 * 
	 * @param id,page and pagesize. page and pagesize is used in pagination
	 * @author Vyom Gangwar
	 */
	@GetMapping("/list")
	public ResponseEntity<HashMap<String, Object>> list(@RequestParam Integer page, @RequestParam Integer pagesize) {
		return getService().extract(page, pagesize);
	}
	
	@GetMapping("/search")
	public ResponseEntity<?>searchResult(@RequestBody String value ){
		return null;
	}

	/**
	 * this method is used to update the zoo data
	 * 
	 * @param id,updatezoo
	 * @return ResponseEntity<String> update
	 * @author Vyom Gangwar
	 */
	@PutMapping("/update/{id}")
	public ResponseEntity<String> update(@PathVariable Integer id, @Valid @RequestBody ZooRegistrationDTO updatezoo) {
		return getService().update(id, updatezoo);
	}

	/**
	 * this method is used to delete the zoo
	 * 
	 * @param id
	 * @return ResponseEntity<String> delete
	 * @author Vyom Gangwar
	 */

	@PreAuthorize("hasAuthority('AUTHORITY_3')")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable Integer id) {
		return getService().delete(id);
	}
}
