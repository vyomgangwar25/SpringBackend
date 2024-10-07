package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import com.example.demo.dto.ExtractAnimalDTO;
import com.example.demo.entities.Animal;
import com.example.demo.entities.Zoo;
import com.example.demo.repository.AnimalRepository;
import com.example.demo.repository.ZooRepository;

@RestController
public class AnimalController {

	@Autowired
	AnimalRepository animalRepository;

	@Autowired
	ZooRepository zooRepository;

	@PostMapping("/animalregistration")
	public ResponseEntity<?> animalCreation(@RequestBody AnimalDTO animalinput) {
		Zoo zoo = new Zoo();
		zoo.setId(animalinput.getZooid());
		Animal animaldata = new Animal(animalinput.getName(), animalinput.getGender(), animalinput.getDob(), zoo);
		animalRepository.save(animaldata);
		return null;
	}

	@GetMapping("/extractanimal/{id}")
	public ResponseEntity<?> extractanimalData(@PathVariable Integer id, @RequestParam Integer page,
			@RequestParam Integer pagesize) {

		// List<Animal> animaldatabyzooid=animalRepository.getByZooId(id);

		PageRequest pageable = PageRequest.of(page, pagesize);
		Page<Animal> pageAnimal = animalRepository.findByZooId(id, pageable);
		long animalcount = animalRepository.countByZooId(id);
		// System.out.println(id);
		System.out.print("animal count is" + animalcount);

		ArrayList<ExtractAnimalDTO> animaldata = new ArrayList<>();

		for (Animal abc : pageAnimal) {
			animaldata.add(new ExtractAnimalDTO(abc.getName(), abc.getGender(), abc.getDob(), id, abc.getId()));
		}
		HashMap<String, Object> response = new HashMap<>();
		response.put("animaldata", animaldata);
		response.put("animalcount", animalcount);

		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasRole('admin')")
	@DeleteMapping("/deleteanimal/{id}")
	public ResponseEntity<?> deleteanimal(@PathVariable Integer id) {
		if (animalRepository.existsById(id)) {
			animalRepository.deleteById(id);
			return ResponseEntity.ok("user deleted");
		}

		return ResponseEntity.status(404).body("not found");

	}

	@GetMapping("/getdropdowndata/{id}")
	public ResponseEntity<?> extractzoolist(@PathVariable Integer id, @RequestParam Integer animalid) {
		// System.out.println("get dropdown");
		Animal animaldata = animalRepository.findById(animalid)
				.orElseThrow(() -> new RuntimeException("Animal not found"));
		// System.out.print(animal.getName()+ animal.getGender()+"hello");

		HashMap<String, Object> response = new HashMap<>();
		List<Zoo> allZoos = zooRepository.findAll();
		List<Zoo> filteredZoos = new ArrayList<>();
		for (Zoo z : allZoos) {
			if (!z.getId().equals(id)) {
				filteredZoos.add(z);
			}
		}
		response.put("filteredZoos", filteredZoos);
		response.put("animaldata", animaldata);
		return ResponseEntity.ok(response);

	}

	@PutMapping("/transferanimal")
	public ResponseEntity<?> animaltransfer(@RequestParam Integer animalid, @RequestParam Integer zooid) {
		// System.out.print(animalid+" "+zooid);
		Animal animal = animalRepository.findById(animalid).orElseThrow(() -> new RuntimeException("Animal not found"));
		Zoo zoo = zooRepository.findById(zooid).get();
		animal.setZoo(zoo);
		animalRepository.save(animal);
		return ResponseEntity.ok("animal Transfered successfully");
	}

}
