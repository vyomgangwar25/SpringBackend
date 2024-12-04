package com.ics.zoo.entities;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;

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
public class User extends CommonEntity implements UserDetails {
	private static final long serialVersionUID = 1L;

	private String username;

	private String email;

	private String password;

	private String role;

	private Collection<? extends GrantedAuthority> authority;

	public Collection<? extends GrantedAuthority> getAuthority() {
		return authority;
	}

	public void setAuthority(Collection<? extends GrantedAuthority> authority) {
		this.authority = authority;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));
		return authority;
	}

}