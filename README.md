# Notes:-

### The solution is concentrated more on the back end implementation. Lately, in the
typical production systems, the front end and back end codes are decoupled
and maintained separately. The front end and back end communicates using
RESTful APIs. I have provided the solution using Spring MVC as it is easier to
keep the solution in one component for this technical test purpose. I have got
the clarification from Shelly before choosing Thymeleaf for the implementation.

# Home URL:-

### http://localhost:8080/local/authority

# Services URL:-

### http://localhost:8080/local/authority   - GET
### http://localhost:8080/ratings   - POST

# Implementation:-

### Spring MVC has been used to implement the solution. The
MVC controllers can be easily converted to RESTful API if required. The
Controller is light weight and it doesn't any business logic
### The error page is configured to show any exceptions occurred in the
back end
### JUnit and Mockito have been used to cover the testing for all the
services and util classes
### Spring MVC mock has been used for Controller testing (Integration testing).
Basically, this is the integration test for the application. Please note that
the service object is not mocked, so the test will fail if the food hygiene
remote service is not available.

The Controller class testing can be converted to Junit testing (non integration
testing) by mocking the service objects as well.

# Class overview:-

### LocalAuthorityController - Controller to get the local authorities to
create the dropdown for selection
### RatingsController - Controller to get the ratings statistics for the
selected local authority
### LocalAuthorityServiceImpl - REST Client to get the local authorities from
remote service
### EstablishmentServiceImpl - REST Client to get the establishments for
the local authority from remote service
### ScoreDescriptorServiceImpl - REST Client to get the ratings description
from remote service
### RatingsPercentageCalculator - Main orchestrator class to get the required
data, aggregate and match the rating keys and calculate the percentage for
each rating key

The calculator service implementation is done using Strategy pattern. In case
the application requires to have different calculation, the specific implementation
can be done and the service can be injected to get the data.

### JsonUtils - To convert the JSON string to POJO objects. The conversion
takes only the required attributes from the JSON to save memory and
improve performance

# Technology Stack:-

### Java 8
### JUnit and Mockito
### Spring MVC, Core and Test
### JSONPath Jayway

# Maven Build:-

### mvn clean test - To run all tests except integration tests
### mvn clean verify - To run all tests including integration tests
### mvn spring-boot:run - To start the application
### Home page - http://localhost:8080/local/authority