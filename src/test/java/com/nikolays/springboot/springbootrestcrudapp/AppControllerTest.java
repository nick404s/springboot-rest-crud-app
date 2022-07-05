package com.nikolays.springboot.springbootrestcrudapp;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import com.nikolays.springboot.springbootrestcrudapp.dao.EmployeeRepository;
import com.nikolays.springboot.springbootrestcrudapp.entity.Employee;
import com.nikolays.springboot.springbootrestcrudapp.service.EmployeeService;

@TestPropertySource("/application.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class AppControllerTest {
	
	private static MockHttpServletRequest request;
	
	@Autowired
	private JdbcTemplate jdbc;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Mock
	private EmployeeService employeeCreateServiceMock;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	private Employee employeeTwo, employeeThree;
	
	@BeforeAll
	public static void setup() {
		request = new MockHttpServletRequest();
		request.setParameter("firstName", "John");
		request.setParameter("lastName", "Doe");
		request.setParameter("email", "jdoe@someplace.com");
	}

	@BeforeEach
	public void setUp() {
		jdbc.execute("INSERT INTO employee(first_name, last_name, email) " +
		"VALUES ('Nick', 'Cage', 'ncage@someplace.com')");	
		
		
		employeeTwo = new Employee("John", "Doe", "jdoe@someplace.com");
		employeeThree = new Employee("Baby", "Yoda", "by@someplace.com");
	} 
	
	@Test
	public void getEmployeesHttpRequestTest() throws Exception {
		
		List<Employee> employeesList = new ArrayList<>(Arrays.asList(employeeTwo,employeeThree));
		
		when(employeeCreateServiceMock.getEmployees()).thenReturn(employeesList);
		
		Assertions.assertIterableEquals(employeesList, employeeCreateServiceMock.getEmployees());
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/employees/list"))
				.andExpect(status().isOk()).andReturn();
		
		ModelAndView mav = mvcResult.getModelAndView();
		
		ModelAndViewAssert.assertViewName(mav, "employees/employees-list");
	}
	
	@Test
	public void formEmployeeHttpRequestTest() throws Exception {
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/employees/form"))
				.andExpect(status().isOk()).andReturn();
		
		ModelAndView mav = mvcResult.getModelAndView();
		
		ModelAndViewAssert.assertViewName(mav, "employees/employees-form");
	}
	
	@Test
	public void saveEmployeeHttpRequestTest() throws Exception {
		
		List<Employee> employeesList = new ArrayList<>(Arrays.asList(employeeTwo));
		
		when(employeeCreateServiceMock.getEmployees()).thenReturn(employeesList);
		
		Assertions.assertIterableEquals(employeesList, employeeCreateServiceMock.getEmployees());
		
		MvcResult mvcResult = mockMvc.perform(post("/employees/save")
				.contentType(MediaType.APPLICATION_JSON)
				.param("firstName", request.getParameter("firstName"))
				.param("lastName", request.getParameter("lastName"))
				.param("email", request.getParameter("email")))
				.andExpect(redirectedUrl("/employees/list")).andReturn();
		
		ModelAndView mav = mvcResult.getModelAndView();
		
		ModelAndViewAssert.assertViewName(mav, "redirect:/employees/list");
		
		Employee verifyEmployee = employeeRepository.findByEmail("jdoe@someplace.com");
		
		Assertions.assertNotNull(verifyEmployee, "Employee should be found");
	}
	
	@Test
	public void updateEmployeeHttpRequestTest() throws Exception {
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/employees/form")
				.param("employeeId", "1"))
				.andExpect(status().isOk()).andReturn();
		
		ModelAndView mav = mvcResult.getModelAndView();
		
		ModelAndViewAssert.assertViewName(mav, "employees/employees-form");
	}
	
	@Test
	public void deleteEmployeeHttpRequestTest() throws Exception {
		
		Assertions.assertTrue(employeeRepository.findById(1L).isPresent());
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/employees/delete")
				.param("employeeId", "1"))
				.andExpect(redirectedUrl("/employees/list")).andReturn();
		
		ModelAndView mav = mvcResult.getModelAndView();
		
		ModelAndViewAssert.assertViewName(mav, "redirect:/employees/list");
		
		Assertions.assertFalse(employeeRepository.findById(1L).isPresent());
	} 
	
	@AfterEach
	public void tearDown() {
		jdbc.execute("DELETE FROM employee");
	}
}
