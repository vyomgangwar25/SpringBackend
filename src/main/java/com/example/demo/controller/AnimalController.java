package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.example.demo.entities.AnimalTransferHistory;
import com.example.demo.entities.User;
import com.example.demo.entities.Zoo;
import com.example.demo.repository.AnimalRepository;
import com.example.demo.repository.AnimalTransferHistoryRepository;
import com.example.demo.repository.ZooRepository;

@RestController
public class AnimalController {

	@Autowired
	AnimalRepository animalRepository;

	@Autowired
	ZooRepository zooRepository;
	
	@Autowired
	AnimalTransferHistoryRepository historyRepository;

	@PostMapping("/animalregistration")
	public ResponseEntity<String> animalCreation( @RequestBody AnimalDTO animalinput) {
		
		Animal animaldata = new Animal(animalinput.getName(), animalinput.getGender(), animalinput.getDob(),
				animalinput.getZooid());
		animalRepository.save(animaldata);
		return ResponseEntity.ok("animal registration successful");
	}

	@PutMapping("/updateanimal/{id}")
	public ResponseEntity<String> animalUpdate(@PathVariable Integer id, @RequestBody Animal updateanimal) {
	 
		Animal animaldata = animalRepository.findById(id).get();
		animaldata.setName(updateanimal.getName());
		animaldata.setGender(updateanimal.getGender());
		animalRepository.save(animaldata);

		return ResponseEntity.ok("animal data updated");

	}

	@GetMapping("/extractanimal/{id}")
	public ResponseEntity<HashMap<String,Object>> extractanimalData(@PathVariable Integer id, @RequestParam Integer page,
			@RequestParam Integer pagesize) {

		PageRequest pageable = PageRequest.of(page, pagesize);
		Zoo zooid = zooRepository.findById(id).get();
		String zooname = zooid.getName();
		Page<Animal> pageAnimal = animalRepository.findByZooId(id, pageable);
		long animalcount = animalRepository.countByZooId(id);
		ArrayList<ExtractAnimalDTO> animaldata = new ArrayList<>();

		for (Animal abc : pageAnimal) {
			animaldata.add(new ExtractAnimalDTO(abc.getName(), abc.getGender(), abc.getDob(), id, abc.getId()));
		}
		HashMap<String, Object> response = new HashMap<>();
		response.put("animaldata", animaldata);
		response.put("animalcount", animalcount);
		response.put("zooname", zooname);

		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasRole('admin')")
	@DeleteMapping("/deleteanimal/{id}")
	public ResponseEntity<String> deleteanimal(@PathVariable Integer id) {
		if (animalRepository.existsById(id)) {
			animalRepository.deleteById(id);
			return ResponseEntity.ok("animal deleted");
		}
		return ResponseEntity.status(404).body("not found");

	}

	@GetMapping("/getdropdowndata")
	public ResponseEntity<HashMap<String,Object>> extractzoolist(@RequestParam Integer zooId) {
		List<Zoo> zoolistexceptid = zooRepository.getZooListById(zooId);

		HashMap<String, Object> response = new HashMap<>();

		response.put("filteredZoos", zoolistexceptid);
		 
		return ResponseEntity.ok(response);

	}

	@PutMapping("/transferanimal")
	public ResponseEntity<String> animaltransfer(@RequestParam Integer animalid, @RequestParam Integer zooid) {
       User user =  (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Animal animal = animalRepository.findById(animalid).orElseThrow(() -> new RuntimeException("Animal not found"));
		Zoo zoo = zooRepository.findById(zooid).get();
		AnimalTransferHistory transferhistroy=new AnimalTransferHistory();
		transferhistroy.setUser(user);
		transferhistroy.setFromZoo(animal.getZoo());
		animal.setZoo(zoo);
		transferhistroy.setToZoo(zoo);
		transferhistroy.setAnimalId(animal);
		transferhistroy.setDate(new Date());
		animalRepository.save(animal);
		 historyRepository.save(transferhistroy);	
		return ResponseEntity.ok("animal Transfered successfully");
	}
	
	@GetMapping("/history/{animalId}")
	public ResponseEntity<?>animalhistory(@PathVariable Integer animalId)
	{
		 Animal animal = animalRepository.findById(animalId).get();
		 List<AnimalTransferHistory>List=historyRepository.findByanimalId(animal);
		 System.out.println("List--->"+List.size());
		 
		 
			List<HashMap<String, String>>filteredList=new ArrayList<>();
		 for(AnimalTransferHistory history:List)
		 {
			 HashMap<String,String>data=new HashMap<>();
			String fromzoo=history.getFromZoo().getName();
			String tooZoo=history.getToZoo().getName();
			String animalName=history.getAnimalId().getName() ;
			String userName=history.getUser().getUsername();
			data.put("fromzoo", fromzoo);
			data.put("tooZoo", tooZoo);
			data.put("animalName", animalName);
			data.put("userName", userName);
			filteredList.add(data);
		 }
		 
		return ResponseEntity.ok(filteredList);
	}
	
	
 

}
