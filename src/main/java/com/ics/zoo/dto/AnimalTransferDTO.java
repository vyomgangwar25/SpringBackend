package com.ics.zoo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class AnimalTransferDTO {
	String fromzoo;
	String tooZoo;
	String animalName;
	String userName;

}
