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
	public List<Employee> findAll() {
		
		// get the current session from hibernate
		Session currentSession = entityManager.unwrap(Session.class);
		
		// query to database
		Query<Employee> aQuery = currentSession.createQuery("from Employee", Employee.class);
		
		// get all employees
		List<Employee> employees = aQuery.getResultList();
		
		return employees;
	}
	
	
	/**
	 * Returns an employee by id from the Employee table.
	 */
	@Override
	public Employee findById(int anId) {
		
		// get the current session from hibernate
		Session currentSession = entityManager.unwrap(Session.class);
		
		// get an Employee from database
		Employee anEmployee = currentSession.get(Employee.class, anId);
		
		return anEmployee;
	}

	/**
	 * Saves or updates an employee to the Employee table.
	 */
	@Override
	public void update(Employee anEmployee) {
		
		// get the current session from hibernate
		Session currentSession = entityManager.unwrap(Session.class);
		
		// save or update an Employee in the database
		currentSession.saveOrUpdate(anEmployee);
		
	}

	/**Deletes an employee from the Employee table.
	 */
	@Override
	public void deleteById(int anId) {
		
		// get the current session from hibernate
		Session currentSession = entityManager.unwrap(Session.class);
		 
		// query to database
		Query aQuery = currentSession.createQuery("delete from Employee where id=:employeeId");
		
		// set id parameter
		aQuery.setParameter("employeeId", anId);
		
		// delete employee
		aQuery.executeUpdate();
	}
}
