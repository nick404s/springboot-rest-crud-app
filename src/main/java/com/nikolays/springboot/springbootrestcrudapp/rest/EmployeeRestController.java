package com.nikolays.springboot.springbootrestcrudapp.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nikolays.springboot.springbootrestcrudapp.entity.Employee;
import com.nikolays.springboot.springbootrestcrudapp.service.EmployeeService;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {
	
	private EmployeeService employeeService;
	
	
	@Autowired
	public EmployeeRestController(EmployeeService anEmployeeService) {
		employeeService = anEmployeeService;
	}
	
	
	@GetMapping("/employees")
	public List<Employee> findAll() {
		return employeeService.findAll();
	}

}
