package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.ZooDTO;
import com.example.demo.entities.Zoo;
import com.example.demo.repository.ZooRepository;



public class ZooController {
   @Autowired
   ZooRepository zoorepository;
	@PostMapping("/zoo")
	public ResponseEntity<?> zooCreation(@RequestBody ZooDTO zooInput)
	{
		//System.out.print("in zoo controller");
	   Zoo newzoo=new Zoo(zooInput.getName(), zooInput.getLocation(), zooInput.getSize());
	   zoorepository.save(newzoo);
		return ResponseEntity.ok(newzoo);
	}
	
	@GetMapping("/extractzoo")
	public ResponseEntity<HashMap<String,Object>>Extractzoo(@RequestParam Integer page, @RequestParam Integer pagesize)
	{
        System.out.print("in extractZoo controller");
		    PageRequest pageable = PageRequest.of(page, pagesize);
		    Page<Zoo> pagezoo = zoorepository.findAll(pageable);
		    Long totalzoo = zoorepository.count();
		    System.out.print(totalzoo);
		    
		    List<ZooDTO>zoodata=new ArrayList<>();
		    for(Zoo abc:pagezoo)
		    {
		    	
		    zoodata.add(new ZooDTO(abc.getName(),abc.getLocation(),abc.getSize()));
		    }
		    HashMap<String, Object> response = new HashMap<>();
		    response.put("zoodata",zoodata );
		    response.put("totalzoo", totalzoo);
		    return ResponseEntity.ok(response);	 
	}
	
	@PreAuthorize("hasRole('admin')")
	@DeleteMapping("/deletezoo/{id}")
	public ResponseEntity<?>Detelezoo(@PathVariable Integer id)
	{  
		 
		
		if(zoorepository.existsById(id))
		{
			//System.out.println("in delete zoo controller");
			zoorepository.deleteById(id);
			return ResponseEntity.ok("Zoo with a particular id deleted successfully");
		 
		}
		return ResponseEntity.status(404).body("not found");
		 
	}
}