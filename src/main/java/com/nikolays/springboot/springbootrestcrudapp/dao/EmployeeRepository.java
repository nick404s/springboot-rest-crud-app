package com.nikolays.springboot.springbootrestcrudapp.dao;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.nikolays.springboot.springbootrestcrudapp.entity.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
	
	// get sorted list of employees
	public List<Employee> findAllByOrderByLastNameAsc();
	
	// get employees by last name
	public List<Employee> findByLastName(String lastName);
	
	// find an employee by email address
	public Employee findByEmail(String email);	
}
