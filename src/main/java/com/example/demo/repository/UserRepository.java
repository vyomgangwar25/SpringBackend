package com.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>
{
	public User findByEmail(String email);
	public User findByUsername(String username);
	public List<User> findAll();
}
