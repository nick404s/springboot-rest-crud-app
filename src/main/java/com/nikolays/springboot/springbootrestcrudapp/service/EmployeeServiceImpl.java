package com.nikolays.springboot.springbootrestcrudapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nikolays.springboot.springbootrestcrudapp.dao.EmployeeRepository;
import com.nikolays.springboot.springbootrestcrudapp.entity.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeRepository employeeRepository;
	
	@Autowired
	public EmployeeServiceImpl(EmployeeRepository anEmployeeRepository) {
		employeeRepository = anEmployeeRepository;
	}
	
	@Override
	public Iterable<Employee> getEmployees() {
		
		return employeeRepository.findAllByOrderByLastNameAsc();
	}

	@Override
	public Employee findById(long anId) {
		Optional<Employee> result = employeeRepository.findById(anId);
		
		Employee anEmployee = null;
		
		if (result.isPresent()) {
			anEmployee = result.get();
		}
		else {
			throw new RuntimeException("Did not find employee ID: " + anId);
		}
		
		return anEmployee;
	}

	@Override
	public void save(Employee anEmployee) {

		employeeRepository.save(anEmployee);
	}

	@Override
	public boolean employeeIsPresent(long anId) {
		
		Optional<Employee> employee = employeeRepository.findById(anId);
		
		if (employee.isPresent()) {
			return true;
		}
		return false;
	}

	@Override
	public void deleteEmployee(long anId) {

		if(employeeIsPresent(anId)) {
			employeeRepository.deleteById(anId);
		}
	}


}
