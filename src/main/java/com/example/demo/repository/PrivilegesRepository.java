package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Privileges;

@Repository
public interface PrivilegesRepository extends JpaRepository<Privileges, Integer> {

}
