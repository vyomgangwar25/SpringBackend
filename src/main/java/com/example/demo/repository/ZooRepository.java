package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entities.Zoo;

public interface ZooRepository extends JpaRepository<Zoo, Integer> {
	
	
	@Query("SELECT z FROM Zoo z WHERE z.id != :n")
public  List<Zoo>getZooListById(@Param("n") Integer n);
}
