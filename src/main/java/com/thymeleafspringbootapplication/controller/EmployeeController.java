package com.thymeleafspringbootapplication.controller;

import com.thymeleafspringbootapplication.model.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.thymeleafspringbootapplication.model.Employee;
import com.thymeleafspringbootapplication.service.EmployeeService;

import java.io.IOException;

@Controller
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/")
	public String home(Model model) throws IOException {
		LoginResponse loginResponse = new LoginResponse();
		model.addAttribute("loginResponse", loginResponse);
		return "login";
	}

	@PostMapping("/auth")
	public String authorize(@ModelAttribute("loginResponse") LoginResponse credentials) throws IOException {

		System.out.println(credentials.toString());
		Boolean isValidToken = employeeService.getLoginToken(credentials);

		if(isValidToken){
			return "redirect:/listOfEmployee";
		}else{
			return "redirect:/";
		}
	}

	@GetMapping("/listOfEmployee")
	public String viewHomePage(Model model) throws IOException {
		model.addAttribute("listEmployees", employeeService.getAllEmployees());
		return "index";
	}
	
	@GetMapping("/showNewEmployeeForm")
	public String showNewEmployeeForm(Model model) {
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		return "new_employee";
	}
	
	@PostMapping("/saveEmployee")
	public String saveEmployee(@ModelAttribute("employee") Employee employee) throws IOException {
		employeeService.saveEmployee(employee);
		return "redirect:/listOfEmployee";
	}

	@PostMapping("/updateEmployee")
	public String updateEmployee(@ModelAttribute("employee") Employee employee) throws IOException {
		employeeService.updateEmployee(employee,employee.getId());
		return "redirect:/listOfEmployee";
	}
	
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable(value="id") String id, Model model) throws IOException {
		Employee employee = employeeService.getEmployeeById(id);
		model.addAttribute("employee", employee);
		return "update_employee";
	}
	
	@GetMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable (value = "id") String id) throws IOException {
		this.employeeService.deleteEmployeeById(id);
		return "redirect:/listOfEmployee";
	}

}
