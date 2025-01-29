package com.ics.zoo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ics.zoo.entities.Department;
import com.ics.zoo.service.DepartMentService;

@RestController
@RequestMapping("/department")
public class DepartmentController {

	@Autowired
	private DepartMentService departMentService;

	@PostMapping("/create")
	ResponseEntity<?> create(@RequestBody Department departmentData) {
		return departMentService.register(departmentData);
	}

	@GetMapping("/list/{id}")
	ResponseEntity<?> list(@PathVariable Integer id) {
		return departMentService.list(id);
	}

}
