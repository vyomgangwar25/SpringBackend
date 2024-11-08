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
import com.example.demo.dto.AnimalDTO;
import com.example.demo.dto.AnimalUpdateDTO;
import com.example.demo.service.AnimalService;


@RestController
public class AnimalController {

	@Autowired
	private AnimalService animalService;

	@PostMapping("/animalregistration")
	public ResponseEntity<String> animalCreation(@RequestBody AnimalDTO animalinput) {
		return animalService.animalRegistration(animalinput);	
	}

	@GetMapping("/extractanimal/{id}")
	public ResponseEntity<HashMap<String, Object>> extractanimalData(@PathVariable Integer id,
			@RequestParam Integer page, @RequestParam Integer pagesize) {
		return animalService.extractAnimal(id, page, pagesize);	
	}

	@PutMapping("/updateanimal/{id}")
	public ResponseEntity<String> animalUpdate(@PathVariable Integer id, @RequestBody AnimalUpdateDTO updateanimal) {
		return animalService.updateAnimalData(id, updateanimal);
	}

	@PreAuthorize("hasRole('admin')")
	@DeleteMapping("/deleteanimal/{id}")
	public ResponseEntity<String> deleteanimal(@PathVariable Integer id) {
		return animalService.deleteAnimalData(id);
	}

	@GetMapping("/getdropdowndata")
	public ResponseEntity<HashMap<String, Object>> extractzoolist(@RequestParam Integer zooId) {
		return animalService.zooList(zooId);	
	}

	@PutMapping("/transferanimal")
	public ResponseEntity<String> animaltransfer(@RequestParam Integer animalid, @RequestParam Integer zooid) {
		return animalService.transferAnimal(animalid, zooid);
	}

	@GetMapping(value = "/history/{animalId}")
	public ResponseEntity<?> animalhistory(@PathVariable Integer animalId) {
		return animalService.historyOfAnimal(animalId);	
	}
}
