package com.ics.zoo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ics.zoo.entities.Animal;

/**
 * Doc
 *
 * @author Vyom Gangwar
 * @since 03-Dec-2024
 */
public interface AnimalRepository extends JpaRepository<Animal, Integer> {
	long countByZooId(Integer zooId);
	public Page<Animal> findByZooId(Integer zooId, Pageable pageable);
}
