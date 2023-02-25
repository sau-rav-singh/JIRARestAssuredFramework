# JIRARestAssuredFramework

This is a REST API testing framework for JIRA implemented using RestAssured and TestNG.

## Installation

* Clone the repository to your local machine using

```bash
git clone https://github.com/sau-rav-singh/JIRARestAssuredFramework.git.
```

* Import the project into your preferred IDE (e.g. IntelliJ, Eclipse).
* Configure the project settings as follows:
  + Set the JRE to Java 8 or later.
  + Add the following libraries to the project build path:
    + TestNG
    + RestAssured
    + Apache POI
* Update the config.properties file located in the src/main/resources folder with your JIRA API token, email address, and JIRA instance URL.

## Usage

# Running the tests
To run the tests, execute the testng.xml file located in the root of the project directory using TestNG.

# Adding new tests
To add a new test case, follow these steps:

* Create a new TestNG test class in the src/test/java/com/restassured/framework/tests package.
* Write a test method that uses the RestAssured library to interact with the JIRA API.
Use the ExcelSheetManager class to read test data from an Excel sheet located in the src/test/resources folder.
* Use the JsonPaths class to map JIRA API response fields to their corresponding JSON paths.
* Use the APIResources enum to define the JIRA API resources that your test method will use.
* Use the SpecBuilders class to build the RequestSpecification and ResponseSpecification objects required by RestAssured.
* Use assertions to verify the test results.
* Generating test reports
To generate a test report, execute the testng.xml file located in the root of the project directory using TestNG. After the tests have finished running, a test report will be generated in the test-output folder.

## Credits

This project was developed by Saurav Singh

## License

This project is licensed under the MIT License. See the LICENSE file for details.
