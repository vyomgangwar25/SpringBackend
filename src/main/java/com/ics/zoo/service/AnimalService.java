package com.ics.zoo.service;

import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

/**
 * animal service
 * 
 * @author Vyom Gangwar
 * 
 */
@Service

/**
 * By default, Spring initializes all singleton beans eagerly at the application
 * startup. when we use @Lazy it postpones the creation of the object until it
 * is first requested.
 **/
@Lazy
public class AnimalService extends AbstractService<AnimalRepository> {
	@Autowired
	private ZooRepository zooRepository;

	@Autowired
	private AnimalTransferHistoryRepository historyRepository;

	/**
	 * this method is used to create a new animal
	 * 
	 * @param animalinput
	 * @return ResponseEntity<String>
	 * @author Vyom Gangwar
	 */

	public ResponseEntity<String> registration(AnimalDTO animalinput) {
		try {
			Animal newAnimal = modelMapper.map(animalinput, Animal.class);
			newAnimal.setZoo(zooRepository.findById(animalinput.getZooid()).get());
			getRepository().save(newAnimal);
			return ResponseEntity.ok(ResponseEnum.REGISTRATION.getMessage());
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}

	}

	/**
	 * this method returns the list of animal in zoo
	 * 
	 * @param id,page and pagesize. page and pagesize is used in pagination
	 * @author Vyom Gangwar
	 */

	public ResponseEntity<HashMap<String, Object>> extract(Integer id, Integer page, Integer pagesize) {
		try {
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
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	/**
	 * this method is used to update the animal information it first find the animal
	 * object using Id and then update the info of that animal
	 * 
	 * @param id,updateanimal
	 * @return ResponseEntity<String>
	 * @author Vyom Gangwar
	 */

	public ResponseEntity<String> update(Integer id, AnimalUpdateDTO updateanimal) {
		try {
			Animal animaldata = getRepository().findById(id).get();
			animaldata.setName(updateanimal.getName());
			animaldata.setGender(updateanimal.getGender());
			getRepository().save(animaldata);
			return ResponseEntity.ok(ResponseEnum.UPDATE.getMessage());
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}

	/**
	 * this method is used to delete the animal it first check it the animal is
	 * exist or not? if exist then first delete the history of that animal and then
	 * delete the animal
	 * 
	 * @param id
	 * @return ResponseEntity<String>
	 * @author Vyom Gangwar
	 **/

	public ResponseEntity<String> delete(Integer id) {
		try {
			if (getRepository().existsById(id)) {
				historyRepository.findByAnimalId(id).forEach(history -> {
					historyRepository.deleteById(history.getId());
				});
				getRepository().deleteById(id);
				return ResponseEntity.ok(ResponseEnum.DELETE.getMessage());
			}
			return ResponseEntity.status(404).body(ResponseEnum.NOT_FOUND.getMessage());
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}

	/**
	 * this method returns the list of zoo except the zoo whose ID we are passing
	 * 
	 * @param zooid
	 * @return ResponseEntity<List<Zoo>>
	 * @author Vyom Gangwar
	 */
	public ResponseEntity<List<Zoo>> transferableZooList(Integer zooId) {
		return ResponseEntity.ok(zooRepository.findAllByIdNot(zooId));
	}

	/***/
	public ResponseEntity<?> search(String text, Integer id) {
		try {
			List<Animal> list = getRepository().serachByNameOrGenderAndId(text, id);
			return ResponseEntity.ok(list);
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}

	}

	/**
	 * this method is used to transfer the animal from one zoo to another we use
	 * animalid to find the animal object and then extract the info of the zoo in
	 * which it currently exist then we use @param zooid to tranfer the animal to
	 * particular zoo
	 * 
	 * @param animalid,zooid
	 * @return ResponseEntity<String>
	 * @author Vyom Gangwar
	 */
	public ResponseEntity<String> transfer(Integer animalid, Integer zooid) {
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Animal animal = getRepository().findById(animalid).get();
			Zoo oldZoo = modelMapper.map(animal.getZoo(), Zoo.class);
			if (zooRepository.existsById(zooid)) {
				Zoo newZoo = zooRepository.findById(zooid).get();
				animal.setZoo(newZoo);
				getRepository().save(animal);
				AnimalTransferHistory transferhistroy = new AnimalTransferHistory(oldZoo, newZoo, user, animal,
						new Date());
				historyRepository.save(transferhistroy);
				return ResponseEntity.ok(ResponseEnum.ANIMAL_TRANSFER.getMessage());
			}
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResponseEnum.NOT_FOUND.getMessage());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}

	}

	/**
	 * this method return the history of animal
	 * 
	 * @param animalId
	 * @return history of animal
	 * @author Vyom Gangwar
	 */

	public ResponseEntity<?> history(Integer animalId) {
		try {
			List<AnimalTransferHistory> historyList = historyRepository.findByAnimalId(animalId);
			if (historyList.size() <= 0) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResponseEnum.NOT_FOUND.getMessage());
			}
			AnimalTransferDataDTO animalTransferdata = new AnimalTransferDataDTO();
			Animal animal = historyList.get(0).getAnimal();
			animalTransferdata
					.setAnimalData(new AnimalDTO(animal.getName(), animal.getGender(), animal.getDob(), null));
			List<AnimalTransferDTO> listTransferList = new ArrayList<>();
			for (AnimalTransferHistory history : historyList)
				listTransferList.add(new AnimalTransferDTO(history.getFromZoo().getName(), history.getToZoo().getName(),
						history.getAnimal().getName(), history.getUser().getUsername()));

			animalTransferdata.setTransferHistoryList(listTransferList);
			return ResponseEntity.ok(animalTransferdata);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}

	}
}
