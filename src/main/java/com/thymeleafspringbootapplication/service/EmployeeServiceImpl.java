package com.thymeleafspringbootapplication.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thymeleafspringbootapplication.model.LoginResponse;
import okhttp3.*;
import org.springframework.stereotype.Service;

import com.thymeleafspringbootapplication.model.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService{

	private String Token = "";
	private String Url = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp";

	@Override
	public Boolean getLoginToken(LoginResponse credentials) throws IOException {

		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("application/json");

		String credentialBody = "{\n\"login_id\" : \""+ credentials.getLoginId()
				+"\",\n\"password\" :\""+credentials.getPassword()+"\"\n}";

		RequestBody body = RequestBody.create(mediaType, credentialBody);
		Request request = new Request.Builder()
				.url("https://qa2.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp")
				.method("POST", body)
				.addHeader("Content-Type", "application/json")
				.build();

		try (Response response = client.newCall(request).execute()) {

			if (response.isSuccessful()) {
				ObjectMapper objectMapper = new ObjectMapper();
				JsonNode jsonNode = objectMapper.readTree(response.body().string());
				String token = jsonNode.get("access_token").asText();

				this.Token = token;

				return true;
			} else {
				// Handle unsuccessful response
				System.out.println("Authentication failed. Response code: " + response.code());
				System.out.println("Response body: " + response.body().string());
			}
		}

        return false;
	}

	@Override
	public List<Employee> getAllEmployees() throws IOException {

		OkHttpClient client = new OkHttpClient().newBuilder().build();

		Request request = new Request.Builder()
				.url(Url + "?cmd=get_customer_list")
				.method("GET", null)
				.addHeader("Authorization", "Bearer " + Token)
				.build();
		Response response = client.newCall(request).execute();
		List<Employee> empList = parseEmployeeList(response.body().string());

		return empList;
	}

	private List<Employee> parseEmployeeList(String jsonResponse) throws IOException {
		List<Employee> empList = new ArrayList<>();

		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(jsonResponse);

		if (jsonNode.isArray()) {
			for (JsonNode employeeNode : jsonNode) {
				Employee employee = new Employee();
				employee.setId(employeeNode.get("uuid").asText());
				employee.setFirstName(employeeNode.get("first_name").asText());
				employee.setLastName(employeeNode.get("last_name").asText());
				employee.setStreet(employeeNode.get("street").asText());
				employee.setAddress(employeeNode.get("address").asText());
				employee.setCity(employeeNode.get("city").asText());
				employee.setState(employeeNode.get("state").asText());
				employee.setEmail(employeeNode.get("email").asText());
				employee.setPhone(employeeNode.get("phone").asText());

				empList.add(employee);
			}
		}

		return empList;
	}

	@Override
	public Employee getEmployeeById(String id) throws IOException {
		List<Employee> empList = getAllEmployees();

		Optional<Employee> optional = empList.stream()
				.filter(employee -> employee.getId().equals(id))
				.findFirst();

		Employee employee;
		if (optional.isPresent()) {
			employee =  optional.get();
		} else {
			throw new RuntimeException(" Employee not found for id :: " + id);
		}
		return employee;
	}

	@Override
	public void saveEmployee(Employee e) throws IOException {
		OkHttpClient client = new OkHttpClient().newBuilder().build();

		String EmpData = CreateJSON(e);
		System.out.println(EmpData);

		MediaType mediaType = MediaType.parse("application/json");

		RequestBody body = RequestBody.create(mediaType, EmpData);

		Request request = new Request.Builder()
				.url(Url+"?cmd=create")
				.method("POST", body)
				.addHeader("Content-Type", "application/json")
				.addHeader("Authorization","Bearer " + Token)
				.build();

		try (Response response = client.newCall(request).execute()) {

			if (response.isSuccessful()) {
				System.out.println("Request successful. Response: " + response.body().string());
			} else {
				System.out.println("Request failed. Response code: " + response.code());
			}
		}
	}

	@Override
	public void updateEmployee(Employee employee, String uuid) throws IOException {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("application/json");

		String updatedEmployeeJSON = CreateJSON(employee);
		RequestBody body = RequestBody.create(mediaType, updatedEmployeeJSON);
		Request request = new Request.Builder()
				.url(Url + "?cmd=update&uuid=" + uuid)
				.method("POST", body)
				.addHeader("Content-Type", "application/json")
				.addHeader("Authorization", "Bearer " + Token)
				.build();
		try (Response response = client.newCall(request).execute()) {
			if (response.isSuccessful()) {
				System.out.println("Request successful. Response: " + response.body().string());
			} else {
				System.out.println("Request failed. Response code: " + response.code());
			}
		}
	}


	@Override
	public void deleteEmployeeById(String uuid) throws IOException {
		OkHttpClient client = new OkHttpClient().newBuilder()
				.build();
		MediaType mediaType = MediaType.parse("text/plain");
		RequestBody body = RequestBody.create(mediaType, "");
		Request request = new Request.Builder()
				.url(Url+"?cmd=delete"+"&"+"uuid="+uuid)
				.method("POST", body)
				.addHeader("Authorization", "Bearer "+Token)
				.build();
		try (Response response = client.newCall(request).execute()) {
			if (response.isSuccessful()) {
				System.out.println("Request successful. Response: " + response.body().string());
			} else {
				System.out.println("Request failed. Response code: " + response.code());
			}
		}
	}

	public String CreateJSON(Employee e){

		String Json = "{\n   \"first_name\": \"" +e.getFirstName() ;
		Json += "\",\n    \"last_name\": \""+e.getLastName() ;
		Json += "\",\n    \"street\": \""+e.getStreet();
		Json += "\",\n    \"address\": \""+e.getAddress();
		Json += "\",\n    \"city\": \""+e.getCity();
		Json += "\",\n    \"state\": \""+e.getState();
		Json += "\",\n    \"email\": \""+e.getEmail();
		Json += "\",\n    \"phone\": \""+e.getPhone()+"\"\n}\n";

		return Json;
	}

}
