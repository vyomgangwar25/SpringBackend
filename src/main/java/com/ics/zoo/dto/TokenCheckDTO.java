package com.ics.zoo.dto;

import com.ics.zoo.entities.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenCheckDTO {
	private String token;
	private Boolean isvalid;
	private User user;
	private String rtoken;

}
