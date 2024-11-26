package com.ics.zoo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ics.zoo.dto.ZooDTO;
import com.ics.zoo.dto.ZooRegistrationDTO;
import com.ics.zoo.entities.Zoo;
import com.ics.zoo.enums.ResponseEnum;
import com.ics.zoo.repository.ZooRepository;

@Service
public class ZooService extends AbstractService<ZooRepository>   {

	public ResponseEntity<Zoo> register(ZooRegistrationDTO zooInput) {
		Zoo newZoo = modelMapper.map(zooInput, Zoo.class);
		getRepository().save(newZoo);
		return ResponseEntity.ok(newZoo);
	}

	public ResponseEntity<HashMap<String, Object>> extract(Integer page, Integer pagesize) {
		PageRequest pageable = PageRequest.of(page, pagesize);
		Page<Zoo> pagezoo = getRepository().findAll(pageable);
		Long totalzoo = getRepository().count();
		List<ZooDTO> zoodata = new ArrayList<>();
		for (Zoo abc : pagezoo) {
			zoodata.add(new ZooDTO(abc.getName(), abc.getLocation(), abc.getSize(),abc.getDescription(), abc.getId()));
		}
		HashMap<String, Object> response = new HashMap<>();
		response.put("zoodata", zoodata);
		response.put("totalzoo", totalzoo);
		return ResponseEntity.ok(response);
	}

	public ResponseEntity<String> update(Integer id, ZooRegistrationDTO updatezoo) {
		Zoo zoodata = getRepository().findById(id).get();
		zoodata.setName(updatezoo.getName());
		zoodata.setLocation(updatezoo.getLocation());
		zoodata.setSize(updatezoo.getSize());
		getRepository().save(zoodata);
		return ResponseEntity.ok(ResponseEnum.UPDATE.getMessage());
	}

	public ResponseEntity<String> delete(Integer id) {
		if (getRepository().existsById(id)) {
			getRepository().deleteById(id);
			return ResponseEntity.ok(ResponseEnum.DELETE.getMessage());
		}
		return ResponseEntity.status(404).body(ResponseEnum.NOT_FOUND.getMessage());
	}
}
