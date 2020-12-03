# Taco Loco Backend Delivery Service
This is a Spring Boot project with Maven as the build tool, developed on Windows 10 with Eclipse IDE.

## How to Run
**Requirements**  
- Java 8 
- JAVA_HOME environment variable pointing correctly to Java8 folder
- PATH environment variable pointing correctly to Java8\bin folder

**Steps to Run Application**
1. Download the repository as a zip file
2. Extract the project
3. Open the command line and navigate to the deliveryservice project folder
4. run the command ```mvnw spring-boot:run```

## Project Overview
**Main Project Structure**  
src/main/java  
_1. com.tacoloco.deliveryservice.controller.DeliveryController.java_    
  - This holds the REST Controller and exposes HTTP API endpoints    
  
_2. com.tacoloco.deliveryservice.repository.DeliverySpringDataRepository_  
  - aka the service layer that communicates with the database   
  
_3. com.tacoloco.deliveryservice.model.Delivery_  
  - the data model used the represent a Delivery. Spring Boot will automatically configure the database table based on this.  

src/test/java (unit tests)  
_1. com.tacoloco.deliveryservice.controller.DeliveryControllerTest.java_  
_2. com.tacoloco.deliveryservice.repository.DeliverySpringDataRepositoryTest_    

**Web Server**  
Spring Boot has an embedded Tomcat Server - which is what I'm using for this challenge.

**ORM Framework**  
I am using Spring Data JPA as the layer of abstraction on top of Hibernate to communicate
with the database. 

**Database**  
This application is currently running on an in-memory H2-Database  
The console can be accessed at: http://localhost:8080/h2-console  
JDBC URL: jdbc:h2:mem:testdb  
User Name: sa  
(no password)  

**Testing**    
I used JUnit, Mockito, and the Spring Test Framework for Unit testing. For end-to-end testing, I used Postman to validate the APIs and the H2 console to check to database. 

I've created basic unit tests for the Controller and Repository. 
There isn't much point in testing the Repository for this exercise, since I'm using the Spring Data JPA Library which has the CRUD operations implemented. However, in the case that we wish to create custom CRUD operations in the future - this would be useful for testing that.

## Data Model
Delivery
 ```
{
	id: <long>,
	firstName: <String>, 
	lastName: <String>,
	addressLine1: <String>,
	addressLine2: <String>,
	city: <String>,
	state: <String>,
	zipcode: <String>,
	country: <String>
}
 ```


## HTTP API Endpoints 
The application will start on port 8080 when running locally  
http://localhost:8080  

**GET /delivery**  
  Response Status: 200 Ok  
  Response Body: JSON Array of Delivery  

**GET /delivery/{id}**  
_when a matching id is found_  
Response Status: 200 Ok  
Response Body: JSON of the Delivery

_when NO matching id_  
Response Status: 204 No Content

**POST /delivery**  
Request Body: Delivery in JSON  

Response Status: 201 Created  
Response Location Header: URI to view created delivery  

**PUT /delivery/{id}**  
Request Body: Delivery in JSON  

_when a matching id is found_  
Response Status: 201 Created  
Response Location Header: URI to view created delivery  

_when NO matching id is found_  
Response Status: 409 Conflict  
Response Body: "Delivery does not exist!"  


**DELETE /delivery/{id}**  
_when a matching id is found_  
Response Status: 204 No Content  

_when NO matching id is found_  
Response Status: 409 Conflict  
Response Body: "Delivery does not exist!"  


