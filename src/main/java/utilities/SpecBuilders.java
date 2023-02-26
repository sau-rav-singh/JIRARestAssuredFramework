package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import reporting.ExtentReportManager;

public class SpecBuilders {


	public RequestSpecification requestSpecification() {

		RequestSpecification authRequest = RestAssured.given().baseUri(readConfigProperties("baseUrl")).auth()
				.preemptive().basic("singh.saurav@icloud.com", readConfigProperties("token"))
				.header("Content-type", "application/json");
		printRequestLogInReport(authRequest);
		return authRequest;
	}
	
	public RequestSpecification requestSpecification(String requestPayload) {

		RequestSpecification authRequest = RestAssured.given().baseUri(readConfigProperties("baseUrl")).auth()
				.preemptive().basic("singh.saurav@icloud.com", readConfigProperties("token"))
				.header("Content-type", "application/json").body(requestPayload);
		
		printRequestLogInReport(authRequest);
		
		return authRequest;
	}

	public ValidatableResponse responseSpecification(Response response) {

		printResponseLogInReport(response);
		return response.then().spec(new ResponseSpecBuilder().expectContentType(ContentType.JSON).build()).log().all();

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

	private static void printRequestLogInReport(RequestSpecification requestSpecification) {
		QueryableRequestSpecification queryableRequestSpecification = SpecificationQuerier.query(requestSpecification);
		ExtentReportManager.logInfoDetails("Endpoint is " + queryableRequestSpecification.getBaseUri());
		ExtentReportManager.logInfoDetails("Method is " + queryableRequestSpecification.getMethod());
		ExtentReportManager.logInfoDetails("Headers are ");
		ExtentReportManager.logHeaders(queryableRequestSpecification.getHeaders().asList());
		ExtentReportManager.logInfoDetails("Request body is ");
		ExtentReportManager.logJson(queryableRequestSpecification.getBody());
	}

	private static void printResponseLogInReport(Response response) {
		ExtentReportManager.logInfoDetails("Response status is " + response.getStatusCode());
		ExtentReportManager.logInfoDetails("Response Headers are ");
		ExtentReportManager.logHeaders(response.getHeaders().asList());
		ExtentReportManager.logInfoDetails("Response body is ");
		ExtentReportManager.logJson(response.getBody().prettyPrint());
	}
}