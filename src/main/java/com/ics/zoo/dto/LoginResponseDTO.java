package com.ics.zoo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
	private String token;
	private String role;
	private String email;
	private String username;
	private Integer id;
	private String refreshToken;

}
