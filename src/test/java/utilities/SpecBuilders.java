/**
 * This package contains utility classes for the project.
 */
package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

/**
 * This class provides methods for creating a request specification and a response specification
 * using RestAssured library. It also contains methods for reading config properties and creating
 * a log file for API request and response logs.
 */
public class SpecBuilders {

    /**
     * This method creates a request specification using RestAssured library. It sets the base URL
     * and authentication information, adds filters for logging the request and response logs,
     * and sets the header to "application/json".
     *
     * @return RequestSpecification object for the API request
     */
    public RequestSpecification requestSpecification() {

        // Create a log file with a timestamp in the filename
        String logFileName = TestConstants.LOG_FILE_PATH + "TestLogs_" + getCurrentDate() + ".txt";

        try (PrintStream log = new PrintStream(new FileOutputStream(logFileName))) {
            // Create a request specification with authentication and log filters
            RequestSpecification authRequest = RestAssured.given().baseUri(readConfigProperties("baseUrl"))
                    .filters(RequestLoggingFilter.logRequestTo(log)).filters(ResponseLoggingFilter.logResponseTo(log))
                    .log().all()
                    .auth().preemptive().basic("singh.saurav@icloud.com", readConfigProperties("token"))
                    .header("Content-type", "application/json");
            return authRequest;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error creating log file", e);
        }
    }

    /**
     * This method creates a response specification using RestAssured library. It sets the expected
     * content type to JSON and logs the response.
     *
     * @param response Response object for the API request
     * @return ValidatableResponse object for the API response
     */
    public ValidatableResponse responseSpecification(Response response ) {

        return response.then().spec(new ResponseSpecBuilder().expectContentType(ContentType.JSON).build()).log().all();

    }

    /**
     * This method returns the current date in "yyyy-MM-dd" format.
     *
     * @return String representing the current date
     */
    private String getCurrentDate() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    /**
     * This method reads a property from the config.properties file.
     *
     * @param key String representing the property name
     * @return String representing the property value
     */
    private String readConfigProperties(String key) {
        Properties prop = new Properties();
        try (FileInputStream input = new FileInputStream(TestConstants.CONFIG_PROPERTIES_PATH)) {
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to read config.properties file.", ex);
        }
        return prop.getProperty(key);
    }

}
