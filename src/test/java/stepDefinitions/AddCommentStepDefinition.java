package stepDefinitions;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.testng.Assert;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import utilities.APIResources;
import utilities.CreatePayload;
import utilities.ExcelSheetManager;
import utilities.ExcelSheetReader;
import utilities.JsonPaths;
import utilities.SpecBuilders;

public class AddCommentStepDefinition {

	private final CreatePayload createPayload;
	private final JsonPaths jsonpaths;
	private final SpecBuilders specBuilder;
	private final ExcelSheetReader excelSheetReader;
	private String updatedJsonPayload;
	private String postResponse;
	private Response addCommentRequest;

	public AddCommentStepDefinition() {
		createPayload = new CreatePayload();
		specBuilder = new SpecBuilders();
		excelSheetReader = ExcelSheetManager.getExcelSheetReader();
		jsonpaths = new JsonPaths();
	}

	@Then("Read comment as{string} to create a valid addCommentToBug payload as{string}")
	public void createAddCommentPayload(String comment, String payloadName) {

		comment = excelSheetReader.readCell(comment);
		try {
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

		createAddCommentPayload(comment,payloadName);
	}

	@When("{string} request is sent with the {string} HTTP method on IssueID as {string}")
	public void request_is_sent_with_http_method_on_issueid(String resource, String httpMethod, String issueID) {
		issueID = excelSheetReader.readCell(issueID);
		APIResources apiResource = APIResources.valueOf(resource);
		if (httpMethod.equalsIgnoreCase("POST")) {
			addCommentRequest = specBuilder.requestSpecification().pathParam("id", issueID).body(updatedJsonPayload)
					.when().post(apiResource.getResource());
		}
	}

	@When("the {string} request is sent with the {string} HTTP method on a non-existing IssueID as {string}")
	public void the_request_is_sent_with_the_http_method_on_a_non_existing_issue_id_as(String resource,
			String httpMethod, String issueID) {
		request_is_sent_with_http_method_on_issueid(resource, httpMethod, issueID);
	}

	@Then("Validate that the response status code is {string}")
	public void the_response_status_code_should_be_something(String responseCode) {

		postResponse = specBuilder.responseSpecification(addCommentRequest)
				.statusCode(Integer.parseInt(responseCode)).extract().response().body().asString();
	}

	@Then("Validate the following fields from the response:")
	public void validate_the_following_fields_from_the_response(DataTable dataTable) {
		JsonPath js = new JsonPath(postResponse);
		List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
		for (Map<String, String> row : rows) {
			String field = row.get("Field");
			String expectedValue = excelSheetReader.readCell(row.get("Value"));
			System.out.println("field is " + field);
			System.out.println("expectedValue is " + expectedValue);
			String actualValue = js.get(jsonpaths.getJsonPathForField(field));
			Assert.assertEquals(expectedValue, actualValue);
		}
		String idFromJSON = js.get("id");
		System.out.println("Issue Created is "+idFromJSON);
	}
}