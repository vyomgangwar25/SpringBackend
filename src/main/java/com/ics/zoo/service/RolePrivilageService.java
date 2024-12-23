package com.ics.zoo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ics.zoo.entities.RolePrivileges;
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
		//List<Integer> privilegeList = new ArrayList<>();
		//Roles role = roleRepository.findByRole(user.getRole());
		//List<RolePrivileges> rolePriviledgeList = rolePrivilegesRepository.findByRoleId_Role(role.getId());
		
		//List<RolePrivileges> rolePriviledgeList = rolePrivilegesRepository.findByRoleId_Role(user.getRole());
	 
//		
//		for (RolePrivileges roleprivileges : rolePriviledgeList) {
//			privilegeList.add(roleprivileges.getPrivileges().getId());
//		}
//		user.setAuthority(setRolePrivilege(user.getRole(), privilegeList));
		
		user.setAuthority(rolePrivilegesRepository.findByRoleId_Role(user.getRole())
				.stream()
				.map(t -> new SimpleGrantedAuthority("AUTHORITY_" + t.getId()))
				.collect(Collectors.toList()));

		return user;
	}

//	@Cacheable("fetchRoles")
//	public List<Roles> roles() {
//		return roleRepository.findAll();
//	}
//	
//	@Cacheable("fetchRolePrivileges")
//	public List<RolePrivileges>roleprivilege(){
//		return rolePrivilegesRepository.findAll();
//	}

//	public Collection<? extends GrantedAuthority> setRolePrivilege(String role, List<Integer> privilege) {
//
//		Set<GrantedAuthority> setrolePrivilegesList = new HashSet<>();
//		setrolePrivilegesList.add(new SimpleGrantedAuthority("ROLE_" + role));
//		for (int i = 0; i < privilege.size(); i++) {
//			setrolePrivilegesList.add(new SimpleGrantedAuthority("AUTHORITY_" + privilege.get(i)));
//		}
//		return setrolePrivilegesList;
//	}
}
