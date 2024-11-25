package com.ics.zoo.entities;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class RolePrivileges extends CommonEntity {

	public RolePrivileges() {

	}

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "role_id", referencedColumnName ="id")
	private Roles role;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "privilege_id", referencedColumnName ="id")
	private Privileges privileges;

	public RolePrivileges(Roles role, Privileges privileges) {
		this.role = role;
		this.privileges = privileges;
	}

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}

	public Privileges getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Privileges privileges) {
		this.privileges = privileges;
	}

}
