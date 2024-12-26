package com.ics.zoo.controller;

import java.util.HashMap;
import java.util.List;

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
import com.ics.zoo.entities.Zoo;
import com.ics.zoo.service.AnimalService;

 

/**
 * animal controller
 * 
 * @author Vyom Gangwar
 * @since 25-Oct-2024
 * 
 */
@RestController
@RequestMapping("/animal")
public class AnimalController extends AbstractController<AnimalService> {
	/**
	 * this method is used to create a new animal
	 * 
	 * @param animalinput
	 * @return ResponseEntity<String>
	 * @author Vyom Gangwar
	 */
	@PostMapping("/create")
	public ResponseEntity<String> create(@RequestBody AnimalDTO animalinput) {
		return getService().registration(animalinput);
	}

	/**
	 * this method returns the list of animal in zoo
	 * 
	 * @param id,page and pagesize. page and pagesize is used in pagination
	 * @author Vyom Gangwar
	 */
	@GetMapping("/list/{id}")
	public ResponseEntity<HashMap<String, Object>> list(@PathVariable Integer id, @RequestParam Integer page,
			@RequestParam Integer pagesize) {
		return getService().extract(id, page, pagesize);
	}

	/**
	 * this method is used to update the animal information
	 * 
	 * @param id,updateanimal
	 * @return ResponseEntity<String>
	 * @author Vyom Gangwar
	 */

	@PutMapping("/update/{id}")
	public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody AnimalUpdateDTO updateanimal) {
		return getService().update(id, updateanimal);
	}

	/**
	 * this method is used to delete the animal
	 * 
	 * @param id
	 * @return ResponseEntity<String>
	 * @author Vyom Gangwar
	 **/
	@PreAuthorize("hasAuthority('AUTHORITY_3')")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable Integer id) {
		return getService().delete(id);
	}

	/**
	 * this method returns the list of zoo except the zoo whose ID we are passing
	 * 
	 * @param zooid
	 * @return ResponseEntity<List<Zoo>>
	 * @author Vyom Gangwar
	 */
	@GetMapping("/zoolist")
	public ResponseEntity<List<Zoo>> dropDownList(@RequestParam Integer zooId) {
		return getService().transferableZooList(zooId);
	}

	/**
	 * this method is used to transfer the animal from one zoo to another
	 * 
	 * @param animalid,zooid
	 * @return ResponseEntity<String>  
	 * @author Vyom Gangwar
	 */

	@PutMapping("/transfer")
	public ResponseEntity<String> transfer(@RequestParam Integer animalid, @RequestParam Integer zooid) {
		return getService().transfer(animalid, zooid);
	}

	/**
	 * this method is used to show the history table of animal
	 * 
	 * @param animalId
	 * @return history of animal
	 * @author Vyom Gangwar
	 */
	@GetMapping("/history/{animalId}")
	public ResponseEntity<?> history(@PathVariable Integer animalId) {
		return getService().history(animalId);
	}
}
