package com.ics.zoo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ics.zoo.entities.Zoo;

/**
 * ZooRepository
 * 
 * @author Vyom Gangwar
 * @since 03-Dec-2024
 */
public interface ZooRepository extends JpaRepository<Zoo, Integer> {
	public List<Zoo> findAllByIdNot(Integer zooId);
	
	@Query("SELECT  z FROM Zoo z where z.name  LIKE  %:searchValue% Or z.location LIKE %:searchValue%")
	List<Zoo>searchByZooNameOrLocation(@Param("searchValue") String searchValue);

}
