package com.thymeleafspringbootapplication.service;

import java.io.IOException;
import java.util.List;

import com.thymeleafspringbootapplication.model.Employee;
import com.thymeleafspringbootapplication.model.LoginResponse;

public interface EmployeeService {
	List<Employee> getAllEmployees() throws IOException;
//	void saveEmployee(Employee employee);
	Employee getEmployeeById(String id) throws IOException;
	void saveEmployee(Employee e) throws IOException;
	void deleteEmployeeById(String id) throws IOException;

	Boolean getLoginToken(LoginResponse credentials) throws IOException;

	void updateEmployee(Employee employee,String uuid) throws IOException;
}
