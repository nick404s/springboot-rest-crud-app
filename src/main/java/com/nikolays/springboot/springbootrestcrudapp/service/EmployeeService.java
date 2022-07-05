package com.nikolays.springboot.springbootrestcrudapp.service;

import com.nikolays.springboot.springbootrestcrudapp.entity.Employee;

public interface EmployeeService {
	
	public Iterable<Employee> getEmployees();
	
	public Employee findById(long anId);
	
	public void save(Employee anEmployee);
	
	public boolean employeeIsPresent(long anId);
	
	public void deleteEmployee(long anId);
}
