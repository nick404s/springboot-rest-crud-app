package com.nikolays.springboot.springbootrestcrudapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nikolays.springboot.springbootrestcrudapp.dao.EmployeeDAO;
import com.nikolays.springboot.springbootrestcrudapp.entity.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	// an EmployeeDAO object to inject
	private EmployeeDAO employeeDAO;
	
	@Autowired
	public EmployeeServiceImpl(EmployeeDAO anEmployeeDAO) {
		
		employeeDAO = anEmployeeDAO;
	}

	@Override
	@Transactional
	public List<Employee> findAll() {
		// delegate to the DAO
		return employeeDAO.findAll();
	}

	@Override
	@Transactional
	public Employee findById(int anId) {
		// delegate to the DAO
		return employeeDAO.findById(anId);
	}

	@Override
	@Transactional
	public void update(Employee anEmployee) {
		// delegate to the DAO
		employeeDAO.update(anEmployee);

	}

	@Override
	@Transactional
	public void deleteById(int anId) {
		// delegate to the DAO
		employeeDAO.deleteById(anId);
	}

}
