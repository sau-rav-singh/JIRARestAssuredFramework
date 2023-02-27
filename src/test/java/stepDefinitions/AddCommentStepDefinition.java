package stepDefinitions;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.testng.Assert;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import testUtilities.CreatePayload;
import testUtilities.ExcelSheetManager;
import testUtilities.ExcelSheetReader;
import testUtilities.JsonPaths;
import testUtilities.SpecBuilders;

public class AddCommentStepDefinition {

	// Initializes a logger instance for the current class that enables logging
	// messages to be written to a log file and console.
	private final Logger logger = LogManager.getLogger(getClass());

	// Instance variables used throughout the step definitions
	private final CreatePayload createPayload;//
	private final JsonPaths jsonpaths;
	private final ExcelSheetReader excelSheetReader;
	private String updatedJsonPayload;
	private String postResponse;
	private Response addCommentResponse;

	public AddCommentStepDefinition() {
		createPayload = new CreatePayload();
		excelSheetReader = ExcelSheetManager.getExcelSheetReader();
		jsonpaths = new JsonPaths();
	}

	@Then("Read comment as{string} to create a valid addCommentToBug payload as{string}")
	public void createAddCommentPayload(String comment, String payloadName) {

		// Read the comment from the Excel sheet
		comment = excelSheetReader.readCell(comment);
		try {
			// Create the payload based on the payload name
			if (payloadName.equals("addCommentToBug")) {
				updatedJsonPayload = createPayload.addCommentPayload(comment);
			} else {
				updatedJsonPayload = createPayload.invalidAddCommentPayload(comment);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Then("Read comment as{string} to create an invalid addCommentToBug payload as{string}")
	public void createInvalidAddCommentPayload(String comment, String payloadName) {

		// Use the createAddCommentPayload method to create the payload
		createAddCommentPayload(comment, payloadName);
	}

	@When("The {string} request is sent with the {string} HTTP method on IssueID as {string}")
	public void request_is_sent_with_http_method_on_issueid(String resource, String httpMethod, String issueID) {

		issueID = excelSheetReader.readCell(issueID);
		
		if (httpMethod.equalsIgnoreCase("POST")) {
			addCommentResponse=SpecBuilders.SendPostAndReturnResponse(updatedJsonPayload,issueID,resource);
		}
	}

	@When("The {string} request is sent with the {string} HTTP method on a non-existing IssueID as {string}")
	public void the_request_is_sent_with_the_http_method_on_a_non_existing_issue_id_as(String resource,
			String httpMethod, String issueID) {

		request_is_sent_with_http_method_on_issueid(resource, httpMethod, issueID);
	}

	@Then("Validate that the response status code is {string}")
	public void the_response_status_code_should_be_something(String responseCode) {

		postResponse = SpecBuilders.responseSpecification(addCommentResponse).statusCode(Integer.parseInt(responseCode))
				.extract().response().body().asString();
	}

	@Then("Validate the following fields from the response:")
	public void validate_the_following_fields_from_the_response(DataTable dataTable) {

		JsonPath js = new JsonPath(postResponse);

		List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

		for (Map<String, String> row : rows) {
			String field = row.get("Field");
			String expectedValue = excelSheetReader.readCell(row.get("Value"));
			String actualValue = js.get(jsonpaths.getJsonPathForField(field));
			Assert.assertEquals(expectedValue, actualValue);
		}

		String idFromJSON = js.get("id");
		logger.info("Issue Created is " + idFromJSON);
	}
}