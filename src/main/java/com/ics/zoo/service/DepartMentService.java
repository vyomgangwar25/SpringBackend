package com.ics.zoo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ics.zoo.entities.Department;
import com.ics.zoo.handler.ResponseData;
import com.ics.zoo.repository.DepartmentRepository;

@Service
public class DepartMentService {

	@Autowired
	private DepartmentRepository departmentRepository;

	public ResponseEntity<String> register(Department departmentdata) {
//		User newUser=mapper.map(userdata, User.class);
		Department newUser = new Department(departmentdata.getDepartmentName(), departmentdata.getDepartmentAddress(),
				departmentdata.getDepartmentCode());
		newUser.setId(departmentdata.getId());

		departmentRepository.save(newUser);
		return ResponseEntity.ok("user saved" + newUser);
	}

	public ResponseEntity<?> list(Integer id) {
	 
		Department data = departmentRepository.findById(id).get();
		return ResponseEntity.ok(data);
	}

}
