package com.ics.zoo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ics.zoo.entities.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

}
