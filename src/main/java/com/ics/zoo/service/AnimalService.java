package com.ics.zoo.service;

import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.ics.zoo.dto.AnimalDTO;
import com.ics.zoo.dto.AnimalTransferDTO;
import com.ics.zoo.dto.AnimalTransferDataDTO;
import com.ics.zoo.dto.AnimalUpdateDTO;
import com.ics.zoo.dto.ExtractAnimalDTO;
import com.ics.zoo.entities.Animal;
import com.ics.zoo.entities.AnimalTransferHistory;
import com.ics.zoo.entities.User;
import com.ics.zoo.entities.Zoo;
import com.ics.zoo.enums.ResponseEnum;
import com.ics.zoo.repository.AnimalRepository;
import com.ics.zoo.repository.AnimalTransferHistoryRepository;
import com.ics.zoo.repository.ZooRepository;

@Service
public class AnimalService extends AbstractService<AnimalRepository> {
	@Autowired
	private ZooRepository zooRepository;

	@Autowired
	private AnimalTransferHistoryRepository historyRepository;

	public ResponseEntity<String> registration(AnimalDTO animalinput) {
		Animal newAnimal = modelMapper.map(animalinput, Animal.class);
		newAnimal.setZoo(zooRepository.findById(animalinput.getZooid()).get());
		getRepository().save(newAnimal);
		return ResponseEntity.ok(ResponseEnum.REGISTRATION.getMessage());
	}

	public ResponseEntity<HashMap<String, Object>> extract(Integer id, Integer page, Integer pagesize) {
		Page<Animal> pageAnimal = getRepository().findByZooId(id, PageRequest.of(page, pagesize));
		ArrayList<ExtractAnimalDTO> animaldata = new ArrayList<>();
		for (Animal animal : pageAnimal) {
			ExtractAnimalDTO mappedanimalobj = modelMapper.map(animal, ExtractAnimalDTO.class);
			mappedanimalobj.setZoo_id(id);
			mappedanimalobj.setAnimal_id(animal.getId());
			animaldata.add(mappedanimalobj);
		}
		HashMap<String, Object> response = new HashMap<>();
		response.put("animaldata", animaldata);
		response.put("animalcount", getRepository().countByZooId(id));
		return ResponseEntity.ok(response);
	}

	public ResponseEntity<String> update(Integer id, AnimalUpdateDTO updateanimal) {
		// updateanimal.setId(id);
		Animal animaldata = getRepository().findById(id).get();
		animaldata.setName(updateanimal.getName());
		animaldata.setGender(updateanimal.getGender());
		getRepository().save(animaldata);
		return ResponseEntity.ok(ResponseEnum.UPDATE.getMessage());
	}

	public ResponseEntity<String> delete(Integer id) {
		if (getRepository().existsById(id)) {
			historyRepository.findByAnimalId(id).forEach(history -> {
				historyRepository.deleteById(history.getId());
			});
			getRepository().deleteById(id);
			return ResponseEntity.ok(ResponseEnum.DELETE.getMessage());
		}
		return ResponseEntity.status(404).body(ResponseEnum.NOT_FOUND.getMessage());
	}

	public ResponseEntity<List<Zoo>> transferableZooList(Integer zooId) {
		return ResponseEntity.ok(zooRepository.findAllByIdNot(zooId));
	}

	public ResponseEntity<String> transfer(Integer animalid, Integer zooid) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Animal animal = getRepository().findById(animalid).get();
		Zoo oldZoo = modelMapper.map(animal.getZoo(), Zoo.class);
		if (zooRepository.existsById(zooid)) {
			Zoo newZoo = zooRepository.findById(zooid).get();
			animal.setZoo(newZoo);
			getRepository().save(animal);
			AnimalTransferHistory transferhistroy = new AnimalTransferHistory(oldZoo, newZoo, user, animal, new Date());
			historyRepository.save(transferhistroy);
			return ResponseEntity.ok(ResponseEnum.ANIMAL_TRANSFER.getMessage());
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResponseEnum.NOT_FOUND.getMessage());
	}

	public ResponseEntity<?> history(Integer animalId) {
		List<AnimalTransferHistory> historyList = historyRepository.findByAnimalId(animalId);
		if (historyList.size() <= 0) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResponseEnum.NOT_FOUND.getMessage());
		}
		AnimalTransferDataDTO animalTransferdata = new AnimalTransferDataDTO();
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
