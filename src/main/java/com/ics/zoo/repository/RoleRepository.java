package com.ics.zoo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ics.zoo.entities.Roles;
import java.util.List;


@Repository
public interface RoleRepository extends JpaRepository<Roles, Integer> {

	public Roles findByRole(String role);
	 

}
