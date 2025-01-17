package com.ics.zoo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ics.zoo.entities.Model;

public interface ModalRepository extends JpaRepository<Model, Integer> {

}
