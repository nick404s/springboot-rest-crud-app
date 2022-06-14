package com.nikolays.springboot.springbootrestcrudapp.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nikolays.springboot.springbootrestcrudapp.entity.Employee;

@Repository
public class EmployeeHiberImpl implements EmployeeDAO {
		
	// define entity manager
	private EntityManager entityManager;
	
	
	// inject the entity manager into constructor
	@Autowired
	public EmployeeHiberImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * Returns all employees from the Employee table.
	 */
	@Override
	@Transactional
	public List<Employee> findAll() {
		
		// get the current session from hibernate
		Session currentSession = entityManager.unwrap(Session.class);
		
		// query to database
		Query<Employee> aQuery = currentSession.createQuery("FROM Employee", Employee.class);
		
		// get all employees
		List<Employee> employees = aQuery.getResultList();
		
		return employees;
	}
}
