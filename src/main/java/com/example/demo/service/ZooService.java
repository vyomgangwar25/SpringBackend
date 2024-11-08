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
	ZooRepository zoorepository;
	
	 @Autowired
	 private ModelMapper modelMapper;
	 
	public ResponseEntity<?> zooRegistration(ZooRegistrationDTO zooInput)
	{
		Zoo newZoo=modelMapper.map(zooInput,Zoo.class);
		zoorepository.save(newZoo);
		return ResponseEntity.ok(newZoo);
	}
	
	public  ResponseEntity<HashMap<String, Object>>extractZooData(Integer page,Integer pagesize) {
		PageRequest pageable = PageRequest.of(page, pagesize);
		Page<Zoo> pagezoo = zoorepository.findAll(pageable);
//		System.out.println(pagezoo.getSize());
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
	
	public ResponseEntity<?>updateZooData(Integer id,ZooRegistrationDTO updatezoo)
	{
		Zoo zoodata= zoorepository.findById(id).get();
	    zoodata.setName(updatezoo.getName());
	    zoodata.setLocation(updatezoo.getLocation());
	    zoodata.setSize(updatezoo.getSize());
	    zoorepository.save(zoodata);
		 return ResponseEntity.ok(ResponseEnum.Update.getMessage());
	}
	
	public ResponseEntity<String> deleteZooData(Integer id) {
		if (zoorepository.existsById(id)) {
			zoorepository.deleteById(id);
			return ResponseEntity.ok(ResponseEnum.Delete.getMessage());
		}
		return ResponseEntity.status(404).body(ResponseEnum.NotFound.getMessage());
	} 
}
