package com.ics.zoo.service;

import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.ics.zoo.entities.User;
import com.ics.zoo.repository.RolePrivilegesRepository;

/**
 * RolePrivileges service
 * 
 * @author Vyom Gangwar
 */
@Service
public class RolePrivilageService {

	@Autowired
	private RolePrivilegesRepository rolePrivilegesRepository;

	/**
	 * this method set the authority
	 * 
	 * @param user
	 * @return user(object)
	 * 
	 ***/

	public UserDetails loadUserByUsername(User user) {
		user.setAuthority(rolePrivilegesRepository.findByRoleId(user.getRoleId()).stream()
				.map(t -> new SimpleGrantedAuthority("AUTHORITY_" + t.getId())).collect(Collectors.toList()));

		return user;
	}

}
