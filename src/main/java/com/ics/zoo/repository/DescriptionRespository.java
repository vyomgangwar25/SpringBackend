package com.ics.zoo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ics.zoo.entities.Description;
@Repository
public interface DescriptionRespository extends JpaRepository<Description, Integer> {

}
