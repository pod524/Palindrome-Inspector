Palindrome Inspector Application
Description
This application validates whether inputted text is a palindrome or not. It offers functionality to check text by entering a username and the text string. It utilizes a cache to store previously validated text and a persisted database to retain data even after application restart.

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