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

	@Then("Create the addCommentToBug payload")
	public void createAddCommentPayload() {
		String comment = excelSheetReader.readCell("COMMENT");
		System.out.println("comment is " + comment);
		try {
			updatedJsonPayload = createPayload.addCommentPayload(comment);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@When("{string} request is sent with the {string} HTTP method on IssueID as {string}")
	public void the_something_request_is_sent_with_the_something_http_method_on_issueid_something(String resource,
			String httpMethod, String issueID) {
		issueID = excelSheetReader.readCell(issueID);
		APIResources apiResource = APIResources.valueOf(resource);
		if (httpMethod.equalsIgnoreCase("POST")) {
			addCommentRequest = specBuilder.requestSpecification().pathParam("id", issueID).body(updatedJsonPayload)
					.when().post(apiResource.getResource());
		}
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