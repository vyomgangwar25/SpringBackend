package com.ics.zoo.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

/**
 * RolePrivileges Entity
 * @author Vyom Gangwar
 * 
 * **/
@Entity
public class RolePrivileges extends CommonEntity {
    
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "role_id", referencedColumnName = "id")
	private Roles role;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "privilege_id", referencedColumnName = "id")
	private Privileges privileges;

}  
