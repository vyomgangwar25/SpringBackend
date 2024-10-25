package com.example.demo.dto;

import java.util.List;

public class AnimalTransferDataDTO
{
	AnimalDTO animalData;
	
	List<AnimalTransferDTO> transferHistoryList;

	public void setAnimalData(AnimalDTO animalData) {
		this.animalData = animalData;
	}

	public void setTransferHistoryList(List<AnimalTransferDTO> transferHistoryList) {
		this.transferHistoryList = transferHistoryList;
	}

	public AnimalDTO getAnimalData() {
		return animalData;
	}

	public List<AnimalTransferDTO> getTransferHistoryList() {
		return transferHistoryList;
	}
	
	
}
