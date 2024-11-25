package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.service.AbstractService;

public class AbstractController<T extends AbstractService<?>>
{
	@Autowired
	private T service;
	
	public T getService()
	{
		return service;
	}
}
