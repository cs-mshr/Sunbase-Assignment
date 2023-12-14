# Project Title:

# SunBase Portal Integration Project

This project involves integrating SunBase Portal APIs to manage customer data. The APIs include authentication, creating, retrieving, updating, and deleting customer information. The implementation includes a basic HTML user interface with login functionality and screens for viewing, creating, updating, and deleting customers.

## 1. Project Description:

A Spring MVC web application for Employee Information Management with the following CRUD operations:

- Authentication
![image](https://github.com/cs-mshr/Sunbase-Assignment/assets/95642555/853ca38f-d8ac-4541-8d07-8b3a6f508468)

 
- Get all the employees
  ![image](https://github.com/cs-mshr/Sunbase-Assignment/assets/95642555/01a6d7ca-48d9-4592-9a6e-860474f213c6)


- Add a new employee
  ![image](https://github.com/cs-mshr/Sunbase-Assignment/assets/95642555/5a3065a6-bf2a-4e6a-9bc1-8064fd288880)

  
- Update an employee
  ![image](https://github.com/cs-mshr/Sunbase-Assignment/assets/95642555/fd64f70d-ac67-42e8-a3e9-07ec4f82c5f5)

- Delete an employee
- Just press the delete button
  ![image](https://github.com/cs-mshr/Sunbase-Assignment/assets/95642555/015e9cc7-9431-40bb-a45e-e87cca200c8d)



## 2. Tech Stack:

- Java 17
- Spring Boot
- HTML
- Bootstrap
- Thymeleaf
- Spring MVC
- Maven 
- Remote Endpoints to access databases

## 3. Installing:

i. Clone the git repo

```
https://github.com/cs-mshr/SpringBoot-Employee-Management-System.git
```

ii. Open project folder

iii. Explore



## 4. How To Use:

i. Create Database -> demo

ii. Open project in preferred IDE (I used SpringToolsSuite4) 

iii. Run project as Spring Boot App

iv. Open web app at localhost: 8080/

v. Add, Update and Delete records from web app 



## Authentication

To authenticate, make a POST request to the following endpoint:

**Path:** https://qa2.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp

**Method:** POST

**Body:**
```json
{
  "login_id": "test@sunbasedata.com",
  "password": "Test@123"
}
```
![image](https://github.com/cs-mshr/Sunbase-Assignment/assets/95642555/4beef2d6-4b0b-4ce3-9cc1-10852ef6fc5b)


This will return a bearer token that needs to be passed in the Authorization header for subsequent API calls.

## Screens

### 1. Login Screen

- Basic HTML form for entering login credentials.
- Submitting the form triggers authentication.
- If authentication fails, a 401 response is received.

  ![image](https://github.com/cs-mshr/Sunbase-Assignment/assets/95642555/e0cb5644-2501-4f19-b16d-12eb4e01641c)


### 2. Customer List Screen

- Display a list of customers retrieved through the following API:

  **Path:** https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp

  **Method:** GET

  **Parameters:**
  ```json
  {
    "cmd": "get_customer_list"
  }
  ```

  **Header:**
  ```
  Authorization: Bearer token_received_in_authentication_API_call
  ```

  **Response:** 200
  ```json
  [{
    "first_name": "Jane",
    "last_name": "Doe",
    "street": "Elvnu Street",
    "address": "H no 2",
    "city": "Delhi",
    "state": "Delhi",
    "email": "sam@gmail.com",
    "phone": "12345678"
  }]
  ```

  ![image](https://github.com/cs-mshr/Sunbase-Assignment/assets/95642555/363cc5c0-c86f-4dd6-bf0e-71bf111f6dba)


### 3. Add a New Customer Screen

- Basic HTML form for entering customer details.
- Submitting the form triggers the creation of a new customer through the following API:

  **Path:** https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp

  **Method:** POST

  **Parameters:**
  ```
  cmd: create
  ```

  **Header:**
  ```
  Authorization: Bearer token_received_in_authentication_API_call
  ```

  **Body:**
  ```json
  {
    "first_name": "Jane",
    "last_name": "Doe",
    "street": "Elvnu Street",
    "address": "H no 2",
    "city": "Delhi",
    "state": "Delhi",
    "email": "sam@gmail.com",
    "phone": "12345678"
  }
  ```

  **Response:**
  - Success: 201, Successfully Created
  - Failure: 400, First Name or Last Name is missing
 
![image](https://github.com/cs-mshr/Sunbase-Assignment/assets/95642555/a18d1a8c-d904-4e14-bbcd-3f15686e515a)


### 4. Delete a Customer Screen

- Basic HTML form for entering the UUID of the customer to be deleted.
- Submitting the form triggers the deletion of the specified customer through the following API:

  **Path:** https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp

  **Method:** POST

  **Parameters:**
  ```
  cmd: delete
  uuid: uuid_of_specific_customer
  ```

  **Header:**
  ```
  Authorization: Bearer token_received_in_authentication_API_call
  ```

  **Response:**
  - 200, Successfully deleted
  - 500, Error Not deleted
  - 400, UUID not found
 
  ![image](https://github.com/cs-mshr/Sunbase-Assignment/assets/95642555/c97f0d29-b452-40fe-b566-dec075337443)


### 5. Update a Customer Screen

- Basic HTML form for entering the UUID and updated customer details.
- Submitting the form triggers the update of the specified customer through the following API:

  **Path:** https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp

  **Method:** POST

  **Parameters:**
  ```
  cmd: update
  uuid: uuid_of_specific_customer
  ```

  **Header:**
  ```
  Authorization: Bearer token_received_in_authentication_API_call
  ```

  **Body:**
  ```json
  {
    "first_name": "Jane",
    "last_name": "Doe",
    "street": "Elvnu Street",
    "address": "H no 2",
    "city": "Delhi",
    "state": "Delhi",
    "email": "sam@gmail.com",
    "phone": "12345678"
  }
  ```

  **Response:**
  - 200, Successfully Updated
  - 500, UUID not found
  - 400, Body is Empty
 ![image](https://github.com/cs-mshr/Sunbase-Assignment/assets/95642555/bf06f882-7572-4719-ab18-27cad9a6ee4a)

  

## Note

- If the cmd parameter is incorrect, a 500 response is received, indicating an Invalid Command.
- If authentication fails for any API, a 401 response is received, indicating Invalid Authorization.

### Testing

You can use tools like Postman to test these APIs. The basic HTML UI provides functionality for login, viewing the customer list, creating a new customer, deleting, and updating an existing customer.

Feel free to customize the UI for a better user experience.
