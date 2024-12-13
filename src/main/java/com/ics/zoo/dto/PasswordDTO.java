package com.ics.zoo.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordDTO {
private	String oldpassword;

@Size(min = 6, message = "Password must be at least 6 characters")
@NotEmpty(message = "Password must not be empty")
private	String newpassword;

}
