package com.example.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AnimalDTO;
import com.example.demo.dto.AnimalTransferDTO;
import com.example.demo.dto.AnimalTransferDataDTO;
import com.example.demo.dto.AnimalUpdateDTO;
import com.example.demo.dto.ExtractAnimalDTO;
import com.example.demo.entities.Animal;
import com.example.demo.entities.AnimalTransferHistory;
import com.example.demo.entities.User;
import com.example.demo.entities.Zoo;
import com.example.demo.repository.AnimalRepository;
import com.example.demo.repository.AnimalTransferHistoryRepository;
import com.example.demo.repository.ZooRepository;

@Service
public class AnimalService {
	
	@Autowired
	AnimalRepository animalRepository;

	@Autowired
	ZooRepository zooRepository;

	@Autowired
	AnimalTransferHistoryRepository historyRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	public ResponseEntity<String> animalRegistration(AnimalDTO animalinput)
	{
		Animal newAnimal=modelMapper.map(animalinput,Animal.class);
		newAnimal.setZoo(zooRepository.findById(animalinput.getZooid()).get());
		animalRepository.save(newAnimal);
		return ResponseEntity.ok("animal registration successful");
	}
	
	public ResponseEntity<HashMap<String, Object>>extractAnimal(Integer id,Integer page,Integer pagesize)
	{
		PageRequest pageable = PageRequest.of(page, pagesize);
		Zoo zoodetails = zooRepository.findById(id).get();
		String zooname = zoodetails.getName();
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
	
	public ResponseEntity<String>updateAnimalData(Integer id, AnimalUpdateDTO updateanimal)
	{
		//pdateanimal.setId(id);
				Animal animaldata = animalRepository.findById(id).get();
				animaldata.setName(updateanimal.getName());
				animaldata.setGender(updateanimal.getGender());
				animalRepository.save(animaldata);
				return ResponseEntity.ok("animal data updated");
	}
	
	public ResponseEntity<String>deleteAnimalData(Integer id){
		if (animalRepository.existsById(id)) {
			animalRepository.deleteById(id);
			return ResponseEntity.ok("animal deleted");
		}
		return ResponseEntity.status(404).body("not found");
	}
	
	public ResponseEntity<HashMap<String, Object>>zooList(Integer zooId)
	{
		List<Zoo> zoolistexceptid = zooRepository.findAllByIdNot(zooId);
		HashMap<String, Object> response = new HashMap<>();
		response.put("filteredZoos", zoolistexceptid);
		return ResponseEntity.ok(response);

	}
	
	public ResponseEntity<String>transferAnimal(Integer animalid,Integer zooid)
	{
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Animal animal = animalRepository.findById(animalid).get();
		Integer fromZooId = animal.getZoo().getId();
		if (zooRepository.existsById(zooid)) {
			Zoo newZoo = zooRepository.findById(zooid).get();
			animal.setZoo(newZoo);
			animalRepository.save(animal);
			Zoo oldZoo = zooRepository.findById(fromZooId).get();
			AnimalTransferHistory transferhistroy = new AnimalTransferHistory(oldZoo, newZoo, user, animal, new Date());
			historyRepository.save(transferhistroy);
			return ResponseEntity.ok("animal Transfered successfully");
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).body("zoo not found");
	}
	
	public ResponseEntity<?>historyOfAnimal(Integer animalId){
		List<AnimalTransferHistory> historyList = historyRepository.findByAnimalId(animalId);
		//System.out.print(historyList.size());
		AnimalTransferDataDTO animalTransferdata = new AnimalTransferDataDTO();
		if (historyList.size() <= 0) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("no history found");
		}
		Animal animal = historyList.get(0).getAnimal();
		animalTransferdata.setAnimalData(new AnimalDTO(animal.getName(), animal.getGender(), animal.getDob(), null));
		List<AnimalTransferDTO> listTransferList = new ArrayList<>();
		for (AnimalTransferHistory history : historyList)
			listTransferList.add(new AnimalTransferDTO(history.getFromZoo().getName(), history.getToZoo().getName(),
					history.getAnimal().getName(), history.getUser().getUsername()));

		animalTransferdata.setTransferHistoryList(listTransferList);
		return ResponseEntity.ok(animalTransferdata);
	}

}
