package com.ics.zoo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ics.zoo.entities.Roles;
 

/**
 * RoleRepository
 * 
 * @author Vyom Gangwar
 * @since 03-Dec-2024
 **/

@Repository
public interface RoleRepository extends JpaRepository<Roles, Integer> {

	public Roles findByRole(String role);

}
