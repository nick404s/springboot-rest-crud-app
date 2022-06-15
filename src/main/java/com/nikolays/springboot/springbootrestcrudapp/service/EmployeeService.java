package com.nikolays.springboot.springbootrestcrudapp.service;

import java.util.List;

import com.nikolays.springboot.springbootrestcrudapp.entity.Employee;

public interface EmployeeService {
	
	public List<Employee> findAll();
	
	public Employee findById(int anId);
	
	public void update(Employee anEmployee);
	
	public void deleteById(int anId);
}
