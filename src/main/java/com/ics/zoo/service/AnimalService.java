package com.ics.zoo.service;

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
public class AnimalService extends AbstractService<AnimalRepository>
{
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

	public ResponseEntity<HashMap<String, Object>> extract(Integer id, Integer page, Integer pagesize)
	{
		Zoo zoodetails = zooRepository.findById(id).get();
		Page<Animal> pageAnimal = getRepository().findByZooId(id, PageRequest.of(page, pagesize));
		ArrayList<ExtractAnimalDTO> animaldata = new ArrayList<>();

		for (Animal abc : pageAnimal) {
			animaldata.add(new ExtractAnimalDTO(abc.getName(), abc.getGender(), abc.getDob(), id, abc.getId()));
		}
		HashMap<String, Object> response = new HashMap<>();
		response.put("animaldata", animaldata);
		response.put("animalcount", getRepository().countByZooId(id));
		response.put("zooname", zoodetails.getName());
		
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
		historyRepository.findById(id).get();
		if (getRepository().existsById(id)) {
			getRepository().deleteById(id);
			return ResponseEntity.ok(ResponseEnum.DELETE.getMessage());
		}
		return ResponseEntity.status(404).body(ResponseEnum.NOT_FOUND.getMessage());
	}

	public ResponseEntity<HashMap<String, Object>> zoolist(Integer zooId) {
		List<Zoo> zoolistexceptid = zooRepository.findAllByIdNot(zooId);
		HashMap<String, Object> response = new HashMap<>();
		response.put("filteredZoos", zoolistexceptid);
		return ResponseEntity.ok(response);
	}

	public ResponseEntity<String> transfer(Integer animalid, Integer zooid) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Animal animal = getRepository().findById(animalid).get();
		Integer fromZooId = animal.getZoo().getId();
		if (zooRepository.existsById(zooid)) {
			Zoo newZoo = zooRepository.findById(zooid).get();
			animal.setZoo(newZoo);
			getRepository().save(animal);
			Zoo oldZoo = zooRepository.findById(fromZooId).get();
			AnimalTransferHistory transferhistroy = new AnimalTransferHistory(oldZoo, newZoo, user, animal, new Date());
			historyRepository.save(transferhistroy);
			return ResponseEntity.ok(ResponseEnum.ANIMAL_TRANSFER.getMessage());
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResponseEnum.NOT_FOUND.getMessage());
	}

	public ResponseEntity<?> history(Integer animalId) {
		List<AnimalTransferHistory> historyList = historyRepository.findByAnimalId(animalId);
		AnimalTransferDataDTO animalTransferdata = new AnimalTransferDataDTO();
		if (historyList.size() <= 0) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResponseEnum.NOT_FOUND.getMessage());
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
