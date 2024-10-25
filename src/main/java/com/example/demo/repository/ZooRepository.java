package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Zoo;

public interface ZooRepository extends JpaRepository<Zoo, Integer> 
{
	public List<Zoo>findAllByIdNot(Integer zooId);
}
