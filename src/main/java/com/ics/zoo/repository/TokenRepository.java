package com.ics.zoo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ics.zoo.entities.TokenCheck;

/**
 * TokenRepository
 * 
 * @author Vyom Gangwar
 * @since 03-Dec-2024
 * 
 ***/

public interface TokenRepository extends JpaRepository<TokenCheck, Integer> {

	public TokenCheck findByToken(String token);
	public List<TokenCheck> findByIsvalid(Boolean value);
	public TokenCheck findByRtoken(String rtoken);
}