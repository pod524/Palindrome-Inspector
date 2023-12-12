Palindrome Inspector Application
Description
This application validates whether inputted text is a palindrome or not. It offers functionality to
check text by entering a username and the text string. It utilizes a cache to store previously validated
text and a persisted database to retain data even after application restart.

Usage
To use the application:

Start the Application:

Run the PalindromeInspectorApplication.java class.
Access the application via a browser using http://localhost:PORT/palindrome_checker/{username}/{text}.

Validating Text:
Enter the desired username and text into the search bar.
The application will respond with whether the text is a palindrome or not.
Viewing the H2 Database:

Access the H2 Console via http://localhost:PORT/h2-console.
Enter JDBC URL: jdbc:h2:file:./src/test/resources/test
Connect to view the persisted database data.

Dependencies
Spring Web
Spring Data JPA
H2 Database

Development Setup
Copy code
Open the project in your preferred IDE.

Build and run the PalindromeInspectorApplication.java class.

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

The project implements a simple palindrome checking application using Spring Boot, H2 Database,
Spring Data JPA, and Spring Web.
Here are some key architectural decisions and considerations:

Spring Boot: Chosen for rapid application development, auto-configuration, and a range of built-in features
that simplify setup and deployment.

RESTful API (Controller): Implemented using Spring's @RestController to expose endpoints for palindrome
validation. Follows REST principles for URL structuring.


Persistence Layer (Repository and Entity):

Entity Class (PalindromeRequest): Represents a persisted palindrome request, mapped to the H2 database table.
Repository Interface (PalindromeRepository): Utilizes Spring Data JPA's repository pattern, offering CRUD
operations for the PalindromeRequest entity.


Caching:

Utilizes Spring's caching mechanism (@Cacheable) to cache palindrome validation results. This minimizes
redundant computations for repeated inputs.
CacheConfig class configures the cache manager.


Concurrency:

The application can optionally simulate a delay (Thread.sleep()), to highlight the significance of caching in
handling performance concerns.


Non-Functional Requirements:

Performance: The use of caching aims to enhance performance by reducing computation time for frequently
occurring inputs.
Database: The H2 in-memory database is chosen, ensuring simplicity for development and testing. However,
for production use, this might need to be swapped with a more robust database like MySQL, PostgreSQL, etc.


Design Patterns:

Repository Pattern: Implemented via Spring Data JPA, abstracting database operations from the service layer.
Caching Pattern: Utilizes Spring's caching annotations for caching results, improving application
responsiveness and performance.


Separation of Concerns:

Services are responsible for business logic, separating it from the controllers that handle HTTP requests.
The repository layer abstracts database operations, ensuring a clear separation of concerns.
Overall, this application demonstrates the use of common Spring Boot features, follows best practices
in separation of concerns, utilizes caching for performance optimization, and employs design patterns
like the repository pattern for a clean architecture.
To further enhance it, considerations around scalability, error handling, and more comprehensive
unit/integration testing could be made.