package com.ics.zoo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ics.zoo.entities.User;

/**
 * UserRepository
 * 
 * @author Vyom Gangwar
 * @since 03-Dec-2024
 * 
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	public User findByEmail(String email);
  
	boolean existsByUsername(String username);

	public User findByUsername(String username);
}
