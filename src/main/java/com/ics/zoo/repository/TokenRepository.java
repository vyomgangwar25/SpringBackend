package com.ics.zoo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ics.zoo.entities.TokenCheck;
import com.ics.zoo.entities.User;

import ch.qos.logback.core.subst.Token;

public interface TokenRepository extends JpaRepository<TokenCheck, Integer> {
	 

	public List<TokenCheck> findByUser_Id(Integer userid);
	public TokenCheck findByToken(String token);
}