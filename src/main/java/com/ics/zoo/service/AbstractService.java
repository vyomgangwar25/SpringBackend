package com.ics.zoo.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class AbstractService<T extends JpaRepository<?, ?>>
{
	@Autowired
	private T repository;
	
	@Autowired
	protected ModelMapper modelMapper;
	
	public T getRepository()
	{
		return repository;
	}
}
