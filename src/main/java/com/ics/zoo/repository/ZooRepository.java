package com.ics.zoo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ics.zoo.entities.Zoo;

/**
 * ZooRepository
 * 
 * @author Vyom Gangwar
 * @since 03-Dec-2024
 */
public interface ZooRepository extends JpaRepository<Zoo, Integer> {
	public List<Zoo> findAllByIdNot(Integer zooId);
}
