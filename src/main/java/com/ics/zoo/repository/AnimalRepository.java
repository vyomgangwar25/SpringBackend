package com.ics.zoo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ics.zoo.entities.Animal;

/**
 * AnimalRepository
 * 
 * @author Vyom Gangwar
 * @since 03-Dec-2024
 */
public interface AnimalRepository extends JpaRepository<Animal, Integer> {
	long countByZooId(Integer zooId);

	public Page<Animal> findByZooId(Integer zooId, Pageable pageable);

	@Query("SELECT a from Animal a join zoo z where (a.name Like %:text% Or a.gender LIKE %:text% ) AND a.zoo.id =:zooId")
	public List<Animal> serachByNameOrGenderAndId(String text, Integer zooId);

}
