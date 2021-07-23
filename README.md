# Coding Challenge
## What's Provided
A simple [Spring Boot](https://projects.spring.io/spring-boot/) web application has been created and bootstrapped 
with data. The application contains information about all employees at a company. On application start-up, an in-memory 
Mongo database is bootstrapped with a serialized snapshot of the database. While the application runs, the data may be
accessed and mutated in the database without impacting the snapshot.

### How to Run
The application may be executed by running `gradlew bootRun`.

### How to Use
The following endpoints are available to use:
```
* CREATE
    * HTTP Method: POST 
    * URL: localhost:8080/employee
    * PAYLOAD: Employee
    * RESPONSE: Employee
* READ
    * HTTP Method: GET 
    * URL: localhost:8080/employee/{id}
    * RESPONSE: Employee
* UPDATE
    * HTTP Method: PUT 
    * URL: localhost:8080/employee/{id}
    * PAYLOAD: Employee
    * RESPONSE: Employee
```
The Employee has a JSON schema of:
```json
{
  "type":"Employee",
  "properties": {
    "employeeId": {
      "type": "string"
    },
    "firstName": {
      "type": "string"
    },
    "lastName": {
          "type": "string"
    },
    "position": {
          "type": "string"
    },
    "department": {
          "type": "string"
    },
    "directReports": {
      "type": "array",
      "items" : "string"
    }
  }
}
```
For all endpoints that require an "id" in the URL, this is the "employeeId" field.

## What to Implement
Clone or download the repository, do not fork it.

### Task 1
Create a new type, ReportingStructure, that has two properties: employee and numberOfReports.

For the field "numberOfReports", this should equal the total number of reports under a given employee. The number of 
reports is determined to be the number of directReports for an employee and all of their distinct reports. For example, 
given the following employee structure:
```
                    John Lennon
                /               \
         Paul McCartney         Ringo Starr
                               /        \
                          Pete Best     George Harrison
```
The numberOfReports for employee John Lennon (employeeId: 16a596ae-edd3-4847-99fe-c4518e82c86f) would be equal to 4. 

This new type should have a new REST endpoint created for it. This new endpoint should accept an employeeId and return 
the fully filled out ReportingStructure for the specified employeeId. The values should be computed on the fly and will 
not be persisted.

### Task 2
Create a new type, Compensation. A Compensation has the following fields: employee, salary, and effectiveDate. Create 
two new Compensation REST endpoints. One to create and one to read by employeeId. These should persist and query the 
Compensation from the persistence layer.

## Delivery
Please upload your results to a publicly accessible Git repo. Free ones are provided by Github and Bitbucket.

## New Functionality
Below is the writeup for the newly added functionality.

### Employee Reports Endpoint
The following endpoint was added:
```
* GET REPORTS
    * HTTP Method: GET 
    * URL: localhost:8080/employee/{id}/reports
    * RESPONSE: ReportingStructure
```
This operation computes the total number of reports under a given employee and returns that value with the employee value in a response.

### Employee Reports Sample
The following is a sample request and response:
```
GET: localhost:8080/employee/16a596ae-edd3-4847-99fe-c4518e82c86f/reports

RESPONSE BODY:
{
    "employee": {
        "employeeId": "16a596ae-edd3-4847-99fe-c4518e82c86f",
        "firstName": "John",
        "lastName": "Lennon",
        ...
    },
    "numberOfReports": 4
}
```

### Employee Reports Assumptions
The following functionality assumptions were made while developing this feature:
* There are no loops in the employee reporting graph (i.e. reports form a directed acyclic graph). 
* Employees listed as direct reports should exist as valid employees in the database and a runtime exception is thrown if not. 
* The "employee" data field in the ReportingStructure contains the entire employee object for the employee requested.

### Compensation Endpoints
The following endpoints were added:
```
* CREATE
    * HTTP Method: POST 
    * URL: localhost:8080/employee/compensation
    * PAYLOAD: Compensation
    * RESPONSE: Compensation
* READ
    * HTTP Method: GET 
    * URL: localhost:8080/employee/{id}/compensation
    * RESPONSE: Compensation
```
The CREATE operation takes a Compensation value as part of the request body and adds that value to the compensation repository. *Note: the employee field in the request body only needs to contain a structure with an employeeID. See sample below.*

The READ operation retrieves an existing compensation for a given employee and returns that value in the response.

### Compensation Sample
The following are sample requests and responses:
```
POST: localhost:8080/employee/compensation

REQUEST BODY:
{
    "employee": {
        "employeeId": "16a596ae-edd3-4847-99fe-c4518e82c86f"
    },
    "salary": 100000,
    "effectiveDate": "09/01/2021"
}

RESPONSE BODY:
{
    "employee": {
        "employeeId": "16a596ae-edd3-4847-99fe-c4518e82c86f",
        "firstName": "John",
        "lastName": "Lennon",
        ...
    },
    "salary": 100000,
    "effectiveDate": "09/01/2021"
}

GET: localhost:8080/employee/16a596ae-edd3-4847-99fe-c4518e82c86f/compensation

RESPONSE BODY:
*Same as above*
```

### Compensation Assumptions
The following functionality assumptions were made while developing this feature:
* An employee must exist to have a Compensation created for it.
* An employee can only have one Compensation associated with it since it is identified by the employee ID. 
* A new Compensation will not be created for an employee when one already exists.
* Attempting to get an employee's Compensation when it does not exist throws a runtime exception.

## Final Note
This project is part of a coding challege. For questions, contact aaronamc@gmail.com.

Thank you for your time.