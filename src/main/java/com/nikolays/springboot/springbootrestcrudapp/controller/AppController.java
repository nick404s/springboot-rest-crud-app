package com.nikolays.springboot.springbootrestcrudapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nikolays.springboot.springbootrestcrudapp.entity.Employee;
import com.nikolays.springboot.springbootrestcrudapp.service.EmployeeService;


@Controller
@RequestMapping("/employees")
public class AppController {

	private final EmployeeService employeeService;
	
	@Autowired
	public AppController(EmployeeService anEmployeeService) {
		
		employeeService = anEmployeeService;	
	}

	@GetMapping("/list")
	public String displayEmployees(Model aModel) {
		
		// set the list of employees as a model attribute 
		aModel.addAttribute("employees", employeeService.getEmployees());
		
		// go to the employee list
		return "employees/employees-list";
	}
	
	@GetMapping("/form")
	public String displayForm(Model aModel) {
		
		Employee anEmployee = new Employee();
		
		// set the new employee as a model attribute for the form
		aModel.addAttribute("employee", anEmployee);
		
		// go to the employee form
		return "employees/employees-form";
	}
	
	@GetMapping("/update")
	public String displayUpdateForm(@RequestParam("employeeId") long anId, Model aModel) {
		
		// find the employee passed from the form to update
		Employee anEmployee = employeeService.findById(anId);
		
		// add the employee as attribute to the model
		aModel.addAttribute("employee", anEmployee);
		
		// go to the employee form
		return "employees/employees-form";
	}
	
	@GetMapping("/delete")
	public String deleteEmployee(@RequestParam("employeeId") long anId) {
		
		//delete the employee passed from the form
		employeeService.deleteEmployee(anId);
		
		// redirect to the list view
		return "redirect:/employees/list";
	}
	
	@PostMapping("/save")
	public String saveEmployee(@ModelAttribute("employee") Employee anEmployee) {

		// save an employee from the form
		employeeService.save(anEmployee);
	
		// redirect to the list view
		return "redirect:/employees/list";
	}
}
