package com.ics.zoo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ics.zoo.entities.RolePrivileges;

/**
 *
 * @author Vyom Gangwar
 * @since 24-Dec-2024
 */
@Repository
public interface RolePrivilegesRepository extends JpaRepository<RolePrivileges, Integer> {

	public List<RolePrivileges> findByRoleId(Integer id);

}
