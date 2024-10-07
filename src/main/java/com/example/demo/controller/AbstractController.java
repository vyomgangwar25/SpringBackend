package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.entities.CommonEntity;

public abstract class AbstractController<T extends CommonEntity, D>
{
	@Autowired
	JpaRepository<T, Integer> repository;
	
	private T add(T entity)
	{
		System.out.println("Pre save :" + entity.getId());
		return repository.save(entity);
	}
	
	public abstract T dtoToEntity(D dto);
	
	public ResponseEntity<?> create(@RequestBody D dto)
	{
		try {
			add(dtoToEntity(dto));
			return ResponseEntity.ok("Registered Successfully");
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
		
	}
}
