package com.example.demo.service;

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
import com.example.demo.entities.RolePrivileges;
import com.example.demo.entities.Roles;
import com.example.demo.entities.User;
import com.example.demo.repository.RolePrivilegesRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

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
		//System.out.println(user.getUsername());
		Roles role = roleRepository.findByRole(user.getRole());
		//System.out.println(role.getRole() + "  " + role.getId());
		List<RolePrivileges> ll = rolePrivilegesRepository.findByRoleId(role.getId());
		for (RolePrivileges roleprivileges : ll) {
			privilegeList.add(roleprivileges.getPrivileges().getId());
			//System.out.println("privilegesid--->"+roleprivileges.getPrivileges().getId());
		}
		user.setAuthority(setRolePrivilege(role.getRole(), privilegeList));

		return user;
	}

	public Collection<? extends GrantedAuthority> setRolePrivilege(String role, List<Integer> privilege) {
		//System.out.println(role);
		Set<GrantedAuthority> setrolePrivilegesList = new HashSet<>();
		setrolePrivilegesList.add(new SimpleGrantedAuthority("ROLE_" + role));
		for (int i = 0; i < privilege.size(); i++) {
			setrolePrivilegesList.add(new SimpleGrantedAuthority("AUTHORITY_" + privilege.get(i)));
			//System.out.println(privilege.get(i));
		}
		return setrolePrivilegesList;
	}
}
