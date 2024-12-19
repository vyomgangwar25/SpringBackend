package com.ics.zoo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TokenCheck extends CommonEntity {
	private String token;
	private Integer isvalid;
	@Column(name="user_id")
	private Integer userId;
}
