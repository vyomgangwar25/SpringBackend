package com.ics.zoo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ics.zoo.entities.TokenCheck;

public interface TokenRepository extends JpaRepository<TokenCheck, Integer> {
	public List<TokenCheck> findByUserId(Integer id);

}