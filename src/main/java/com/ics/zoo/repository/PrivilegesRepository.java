package com.ics.zoo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ics.zoo.entities.Privileges;

@Repository
public interface PrivilegesRepository extends JpaRepository<Privileges, Integer> {

}