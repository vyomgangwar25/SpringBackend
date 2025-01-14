package com.ics.zoo.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ics.zoo.entities.AnimalTransferHistory;


/**
 * AnimalTransferHistoryRepository
 * 
 * @author Vyom Gangwar
 * @since 03-Dec-2024
 * 
 */
@Repository
public interface AnimalTransferHistoryRepository extends JpaRepository<AnimalTransferHistory, Integer>
{
	public List<AnimalTransferHistory> findByAnimalId(Integer id); 
}
 

