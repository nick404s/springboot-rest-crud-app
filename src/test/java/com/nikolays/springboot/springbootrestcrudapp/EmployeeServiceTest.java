package com.nikolays.springboot.springbootrestcrudapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import com.nikolays.springboot.springbootrestcrudapp.dao.EmployeeRepository;
import com.nikolays.springboot.springbootrestcrudapp.entity.Employee;
import com.nikolays.springboot.springbootrestcrudapp.service.EmployeeService;

@TestPropertySource("/application.properties")
@SpringBootTest
public class EmployeeServiceTest {
	
	@Autowired
	private JdbcTemplate jdbc;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@BeforeEach
	public void setUp() {
		
		jdbc.execute("INSERT INTO employee(first_name, last_name, email) " +
		"VALUES ('Nick', 'Cage', 'ncage@someplace.com')");
	}
	
	@Test
	public void saveEmployeeServiceTest() {
		
		Employee employeeTwo = new Employee("John", "Doe", "jdoe@someplace.com");
		
		employeeService.save(employeeTwo);
		
		Employee employee = employeeRepository.findByEmail("jdoe@someplace.com");
		
		Assertions.assertEquals("jdoe@someplace.com", 
				employee.getEmail(), "find by email");
	}

	
	@Test
	public void employeeIsPresentTest() {
		
		Assertions.assertTrue(employeeService.employeeIsPresent(1));
		
		Assertions.assertFalse(employeeService.employeeIsPresent(0));
	}
	
	@Test
	public void deleteEmployeeServiceTest() {
		
		Optional<Employee> deletedEmployee = employeeRepository.findById(1L);
		
		Assertions.assertTrue(deletedEmployee.isPresent(), "Return True");
		
		employeeService.deleteEmployee(1L);
		
		deletedEmployee = employeeRepository.findById(1L);
		
		Assertions.assertFalse(deletedEmployee.isPresent(), "Return False");
	}
	
	@Sql("/insertData.sql")
	@Test
	public void getEmployeesServiceTest() {
		
		Iterable<Employee> iterableEmployees = employeeService.getEmployees();
		
		List<Employee> employees = new ArrayList<>();
		
		for(Employee employee : iterableEmployees) {
			employees.add(employee);
		}
		
		Assertions.assertEquals(5, employees.size());
	}

	@AfterEach
	public void tearDown() {
		jdbc.execute("DELETE FROM employee");
	}
}
