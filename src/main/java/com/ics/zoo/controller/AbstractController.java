package com.ics.zoo.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.ics.zoo.service.AbstractService;

/**
 * Abstract controller
 * @author Vyom Gangwar
 * */
public class AbstractController<T extends AbstractService<?>>
{
	@Autowired
	private T service;
	
	public T getService()
	{
		return service;
	}
}
