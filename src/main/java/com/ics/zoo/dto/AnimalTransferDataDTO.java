package com.ics.zoo.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AnimalTransferDataDTO
{
	private AnimalDTO animalData;
	
	List<AnimalTransferDTO> transferHistoryList;
}
