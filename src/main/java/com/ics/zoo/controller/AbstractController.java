package com.ics.zoo.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.ics.zoo.service.AbstractService;

public class AbstractController<T extends AbstractService<?>>
{
	@Autowired
	private T service;
	
	public T getService()
	{
		return service;
	}
}
