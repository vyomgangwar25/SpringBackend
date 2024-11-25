package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Animal;

public interface AnimalRepository extends JpaRepository<Animal, Integer> {
    long countByZooId(Integer zooId);
   public  Page<Animal> findByZooId(Integer zooId, Pageable pageable);
   public  List<Animal> getByZooId(Integer zooId);
}
