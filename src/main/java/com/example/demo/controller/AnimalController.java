package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
 
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AnimalDTO;
import com.example.demo.dto.ExtractAnimalDTO;
import com.example.demo.entities.Animal;
import com.example.demo.entities.Zoo;
import com.example.demo.repository.AnimalRepository;



@RestController
public class AnimalController {

	@Autowired
	AnimalRepository animalRepository;

	
	@PostMapping("/animalregistration")
	public ResponseEntity<?>animalCreation(@RequestBody AnimalDTO animalinput)
	{
		Zoo zoo = new Zoo();	
		zoo.setId(animalinput.getZooid());
		Animal animaldata=new Animal(animalinput.getName(),animalinput.getGender(),animalinput.getDob(), zoo);
		animalRepository.save(animaldata);
		return null;
	}
	
	@GetMapping("/extractanimal/{id}")
	public ResponseEntity<?>extractanimalData(@PathVariable Integer id,@RequestParam Integer page,@RequestParam Integer pagesize){
		
		//List<Animal> animaldatabyzooid=animalRepository.getByZooId(id);
		
		
		 PageRequest pageable = PageRequest.of(page, pagesize);
		 Page<Animal> pageAnimal = animalRepository.findByZooId(id, pageable);
		 long animalcount=animalRepository.countByZooId(id);
		    
		System.out.print(animalcount);
		
		ArrayList<ExtractAnimalDTO> animaldata=new ArrayList<>();
		 
		
		for(Animal abc:pageAnimal)
		{
			animaldata.add(new ExtractAnimalDTO(abc.getName(),abc.getGender(),abc.getDob()));
		}
		HashMap<String, Object> response = new HashMap<>();
	    response.put("animaldata",animaldata);
	    response.put("animalcount", animalcount);
		
		return ResponseEntity.ok(response);
	}


}
