package com.ics.zoo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ics.zoo.dto.ZooDTO;
import com.ics.zoo.dto.ZooRegistrationDTO;
import com.ics.zoo.entities.Zoo;
import com.ics.zoo.enums.ResponseEnum;
import com.ics.zoo.repository.ZooRepository;

/**
 * Zoo Service
 * 
 * @author Vyom Gangwar
 */
@Service
public class ZooService extends AbstractService<ZooRepository> {
	/**
	 * this is used to create new zoo
	 * 
	 * @param zooInput
	 * @return ResponseEntity<String>
	 */
	public ResponseEntity<String> register(ZooRegistrationDTO zooInput) {
		try {
			Zoo newZoo = modelMapper.map(zooInput, Zoo.class);
			getRepository().save(newZoo);
			return ResponseEntity.ok(ResponseEnum.REGISTRATION.getMessage());
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}

	/**
	 * this is used to extract the list of zoo.
	 * @param id,page and pagesize. page and pagesize is used in pagination
	 */

	public ResponseEntity<HashMap<String, Object>> extract(Integer page, Integer pagesize) {
		try {
			Page<Zoo> pagezoo = getRepository().findAll(PageRequest.of(page, pagesize));
			List<ZooDTO> zoodata = new ArrayList<>();
			for (Zoo zoo : pagezoo) {
				ZooDTO mappedzoo = modelMapper.map(zoo, ZooDTO.class);
				zoodata.add(mappedzoo);
			}
			HashMap<String, Object> response = new HashMap<>();
			response.put("zoodata", zoodata);
			response.put("totalzoo", getRepository().count());
			return ResponseEntity.ok(response);
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}
	/**
	 * This method is used to search the zoo
	 * @param value
	 * @return list of zoo
	 * 
	 * */
	public ResponseEntity<List<Zoo>>search(String value){
		try {
			List<Zoo>list=getRepository().searchByZooNameOrLocation(value);
			return  ResponseEntity.ok(list);
		}
		catch (Exception ex) {
			 throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}	
	}

	/**
	 * this is used to update the zoo. it first find the zoo with id and then update
	 * the zoo info
	 * 
	 * @param id,updatezoo
	 * @return ResponseEntity<String>
	 */

	public ResponseEntity<String> update(Integer id, ZooRegistrationDTO updatezoo) {
		try {
			if (getRepository().existsById(id)) {
				Zoo zooodata = modelMapper.map(updatezoo, Zoo.class);
				zooodata.setId(id);
				getRepository().save(zooodata);
			}
			return ResponseEntity.ok(ResponseEnum.UPDATE.getMessage());
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}

	/**
	 * this is used to delete the zoo.it first check if the zoo is exist or not? if
	 * exist then delete the zoo
	 * 
	 * @param id
	 * @return ResponseEntity<String>
	 */
	public ResponseEntity<String> delete(Integer id) {
		try {
			if (getRepository().existsById(id)) {
				getRepository().deleteById(id);
			}
			return ResponseEntity.ok(ResponseEnum.DELETE.getMessage());
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}
}
