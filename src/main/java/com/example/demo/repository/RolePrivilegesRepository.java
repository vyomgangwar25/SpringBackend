package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.entities.RolePrivileges;
 

@Repository
public interface RolePrivilegesRepository extends JpaRepository<RolePrivileges, Integer> {

  public List<RolePrivileges> findByRoleId(Integer id);

}
