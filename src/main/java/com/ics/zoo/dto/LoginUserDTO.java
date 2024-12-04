package com.ics.zoo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDTO {

	@Email(message = "email address not valid!!")
	public String email;

	@Size(min = 6, message = "Password must be at least 6 characters")
	@NotEmpty(message = "Password must not be empty")
	public String password;

}
