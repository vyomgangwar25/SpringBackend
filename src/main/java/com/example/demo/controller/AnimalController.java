package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.example.demo.dto.AnimalTransferDTO;
import com.example.demo.dto.AnimalTransferDataDTO;
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
		updateanimal.setId(id);
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
		List<Zoo> zoolistexceptid = zooRepository.findAllByIdNot(zooId);
		HashMap<String, Object> response = new HashMap<>();
		response.put("filteredZoos", zoolistexceptid);
		return ResponseEntity.ok(response);

	}

	@PutMapping("/transferanimal")
	public ResponseEntity<String> animaltransfer(@RequestParam Integer animalid, @RequestParam Integer zooid) 
	{
		User user =  (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Animal animal = animalRepository.findById(animalid).get();
		Integer fromZooId = animal.getZoo().getId();
		if(zooRepository.existsById(zooid))
		{
			Zoo newZoo = zooRepository.findById(zooid).get();
			animal.setZoo(newZoo);
			animalRepository.save(animal);
			Zoo oldZoo = zooRepository.findById(fromZooId).get();
			AnimalTransferHistory transferhistroy=new AnimalTransferHistory(oldZoo, newZoo,user,animal,new Date());
			historyRepository.save(transferhistroy);	
			return ResponseEntity.ok("animal Transfered successfully");
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).body("zoo not found");
	}

	
	@GetMapping(value = "/history/{animalId}")
	public ResponseEntity<?>animalhistory(@PathVariable Integer animalId)
	{
		List<AnimalTransferHistory> historyList = historyRepository.findByAnimalId(animalId);
		System.out.print(historyList.size());
		AnimalTransferDataDTO animalTransferdata = new AnimalTransferDataDTO();
		  if(historyList.size()<=0)
		  {
			  return ResponseEntity.status(HttpStatus.NO_CONTENT).body("no history found");
		  }
		Animal animal = historyList.get(0).getAnimal(); 
		animalTransferdata.setAnimalData(new AnimalDTO(animal.getName(), animal.getGender(), animal.getDob(), null));
		List<AnimalTransferDTO> listTransferList = new ArrayList<>();
		for(AnimalTransferHistory history : historyList)
			listTransferList.add(
					new AnimalTransferDTO(
							history.getFromZoo().getName(), 
							history.getToZoo().getName(),
							history.getAnimal().getName(),
							history.getUser().getUsername())
					);
		
		animalTransferdata.setTransferHistoryList(listTransferList);
		return ResponseEntity.ok(animalTransferdata);
	}
}
