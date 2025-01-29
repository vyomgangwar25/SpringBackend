package com.ics.zoo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ics.zoo.entities.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

}
