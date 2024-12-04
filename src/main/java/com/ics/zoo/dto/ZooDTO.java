package com.ics.zoo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ZooDTO extends ZooRegistrationDTO {

	@NotNull(message = "Integer must not be blank")
	private Integer id;

}
