package com.ics.zoo.dto;

import com.ics.zoo.annotation.UniqueUserName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO extends LoginUserDTO {

	@UniqueUserName
	@NotNull(message = "name cannot be empty")
	@NotBlank(message = "name cannot be black")
	public String username;

	@NotNull(message = "enter role")
	public Integer roleId;
	//private Collection<? extends GrantedAuthority> authority;

	
	

}
