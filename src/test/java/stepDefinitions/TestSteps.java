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
import utilities.ExcelSheetWriter;
import utilities.JsonPaths;
import utilities.SpecBuilders;

public class TestSteps {

	private final CreatePayload createPayload;
	private final JsonPaths jsonpaths;
	private final SpecBuilders specBuilder;
	private final ExcelSheetReader excelSheetReader;
	private final ExcelSheetWriter excelSheetWriter;
	private String updatedJsonPayload;
	private String postResponse;
	private Response addCommentRequest;

	public TestSteps() {
		createPayload = new CreatePayload();
		specBuilder = new SpecBuilders();
		excelSheetReader = ExcelSheetManager.getExcelSheetReader();
		excelSheetWriter = ExcelSheetManager.getExcelSheetWriter();
		jsonpaths = new JsonPaths();
	}

	@When("Read symbol as {string} and orderType as {string} and print the values")
	public void read_symbol_as_and_order_type_as_and_print_the_values(String symbol, String orderType)
			throws IOException {

		if (excelSheetReader == null) {
			throw new IllegalStateException("ExcelSheetReader is not initialized for the current thread.");
		}
		symbol = excelSheetReader.readCell(symbol);
		orderType = excelSheetReader.readCell(orderType);
		System.out.println("Symbol is " + symbol);
		System.out.println("Order Type  is " + orderType);
		excelSheetWriter.writeCell("EXCEL", symbol);
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

		postResponse = addCommentRequest.then().spec(specBuilder.responseSpecification())
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
		try {
			excelSheetWriter.writeCell("ID", idFromJSON);
		} catch (IOException e) {
			e.printStackTrace();
		}
		excelSheetWriter.closeFile();
	}
}