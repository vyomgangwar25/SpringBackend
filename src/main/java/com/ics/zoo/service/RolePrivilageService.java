package com.ics.zoo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ics.zoo.entities.RolePrivileges;
import com.ics.zoo.entities.Roles;
import com.ics.zoo.entities.User;
import com.ics.zoo.repository.RolePrivilegesRepository;
import com.ics.zoo.repository.RoleRepository;
import com.ics.zoo.repository.UserRepository;

@Service
public class RolePrivilageService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private RolePrivilegesRepository rolePrivilegesRepository;

	public UserDetails loadUserByUsername(String username) {
		List<Integer> privilegeList = new ArrayList<>();
		User user = userRepository.findByUsername(username);
		Roles role = roleRepository.findByRole(user.getRole());

		List<RolePrivileges> ll = rolePrivilegesRepository.findByRoleId(role.getId());
		for (RolePrivileges roleprivileges : ll) {
			privilegeList.add(roleprivileges.getPrivileges().getId());
		}
		user.setAuthority(setRolePrivilege(role.getRole(), privilegeList));

		return user;
	}

	public Collection<? extends GrantedAuthority> setRolePrivilege(String role, List<Integer> privilege) {

		Set<GrantedAuthority> setrolePrivilegesList = new HashSet<>();
		setrolePrivilegesList.add(new SimpleGrantedAuthority("ROLE_" + role));
		for (int i = 0; i < privilege.size(); i++) {
			setrolePrivilegesList.add(new SimpleGrantedAuthority("AUTHORITY_" + privilege.get(i)));
		}
		return setrolePrivilegesList;
	}
}
