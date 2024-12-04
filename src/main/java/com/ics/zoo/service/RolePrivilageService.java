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

@Service
public class RolePrivilageService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private RolePrivilegesRepository rolePrivilegesRepository;

	public UserDetails loadUserByUsername(User user) {
		List<Integer> privilegeList = new ArrayList<>();
		Roles role = roleRepository.findByRole(user.getRole());

		List<RolePrivileges> rolePriviledgeList = rolePrivilegesRepository.findByRoleId(role.getId());
		for (RolePrivileges roleprivileges : rolePriviledgeList) {
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
