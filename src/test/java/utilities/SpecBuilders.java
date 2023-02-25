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
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecBuilders {

	public RequestSpecification requestSpecification() {

		String logFileName = TestConstants.LOG_FILE_PATH + "TestLogs_" + getCurrentDate() + ".txt";

		try (PrintStream log = new PrintStream(new FileOutputStream(logFileName))) {
			RequestSpecification authRequest = RestAssured.given().baseUri(readConfigProperties("baseUrl"))
					.filter(RequestLoggingFilter.logRequestTo(log)).filter(ResponseLoggingFilter.logResponseTo(log))
					.auth().preemptive().basic("singh.saurav@icloud.com", readConfigProperties("token"))
					.header("Content-type", "application/json");
			return authRequest;
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Error creating log file", e);
		}
	}

	public ResponseSpecification responseSpecification() {

		return new ResponseSpecBuilder().expectContentType(ContentType.JSON).build();
	}

	private String getCurrentDate() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(date);
	}

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
