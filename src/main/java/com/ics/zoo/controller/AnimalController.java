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

import com.ics.zoo.dto.AnimalDTO;
import com.ics.zoo.dto.AnimalUpdateDTO;
import com.ics.zoo.service.AnimalService;

@RestController
@RequestMapping("/animal")
public class AnimalController extends AbstractController<AnimalService>
{
	@PostMapping("/create")
	public ResponseEntity<String> create(@RequestBody AnimalDTO animalinput) {
		return getService().registration(animalinput);
	}

	@GetMapping("/list/{id}")
	public ResponseEntity<HashMap<String, Object>> list(@PathVariable Integer id,
			@RequestParam Integer page, @RequestParam Integer pagesize) {
		return getService().extract(id, page, pagesize);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody AnimalUpdateDTO updateanimal) {
		return getService().update(id, updateanimal);
	}

	@PreAuthorize("hasAuthority('AUTHORITY_3')")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable Integer id) {
		return getService().delete(id);
	}

	@GetMapping("/zoolist")
	public ResponseEntity<HashMap<String, Object>> dropDownList(@RequestParam Integer zooId) {
		return getService().zoolist(zooId);
	}

	@PutMapping("/transfer")
	public ResponseEntity<String> transfer(@RequestParam Integer animalid, @RequestParam Integer zooid) {
		return getService().transfer(animalid, zooid);
	}

	@GetMapping("/history/{animalId}")
	public ResponseEntity<?> history(@PathVariable Integer animalId) {
		return getService().history(animalId);
	}
}
