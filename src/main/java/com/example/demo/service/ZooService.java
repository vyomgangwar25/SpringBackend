package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ZooDTO;
import com.example.demo.dto.ZooRegistrationDTO;
import com.example.demo.entities.Zoo;
import com.example.demo.enums.ResponseEnum;
import com.example.demo.repository.ZooRepository;

@Service
public class ZooService {
	@Autowired
	private ZooRepository zoorepository;

	@Autowired
	private ModelMapper modelMapper;

	public ResponseEntity<Zoo> registration(ZooRegistrationDTO zooInput) {
		Zoo newZoo = modelMapper.map(zooInput, Zoo.class);
		zoorepository.save(newZoo);
		return ResponseEntity.ok(newZoo);
	}

	public ResponseEntity<HashMap<String, Object>> extract(Integer page, Integer pagesize) {
		PageRequest pageable = PageRequest.of(page, pagesize);
		Page<Zoo> pagezoo = zoorepository.findAll(pageable);
		Long totalzoo = zoorepository.count();
		List<ZooDTO> zoodata = new ArrayList<>();
		for (Zoo abc : pagezoo) {
			zoodata.add(new ZooDTO(abc.getName(), abc.getLocation(), abc.getSize(), abc.getId()));
		}
		HashMap<String, Object> response = new HashMap<>();
		response.put("zoodata", zoodata);
		response.put("totalzoo", totalzoo);
		return ResponseEntity.ok(response);
	}

	public ResponseEntity<String> update(Integer id, ZooRegistrationDTO updatezoo) {
		Zoo zoodata = zoorepository.findById(id).get();
		zoodata.setName(updatezoo.getName());
		zoodata.setLocation(updatezoo.getLocation());
		zoodata.setSize(updatezoo.getSize());
		zoorepository.save(zoodata);
		return ResponseEntity.ok(ResponseEnum.UPDATE.getMessage());
	}

	public ResponseEntity<String> delete(Integer id) {
		if (zoorepository.existsById(id)) {
			zoorepository.deleteById(id);
			return ResponseEntity.ok(ResponseEnum.DELETE.getMessage());
		}
		return ResponseEntity.status(404).body(ResponseEnum.NOT_FOUND.getMessage());
	}
}
