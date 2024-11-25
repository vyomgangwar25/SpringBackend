package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class AbstractService<T extends JpaRepository<?, ?>>
{
	@Autowired
	private T repository;
	
	public T getRepository()
	{
		return repository;
	}
}
