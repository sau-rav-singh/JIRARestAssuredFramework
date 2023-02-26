/**
 * The utilities package contains utility classes that provide commonly used functionality for tests in the project.
 * These utility classes are designed to make tests easier to write and maintain, and to promote code reuse throughout
 * the project.
 */
package utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * The CreatePayload class provides utility methods for creating JSON payloads used in tests.
 * These methods read JSON files from the TestConstants class, modify them with new data,
 * and return them as strings.
 */
public class CreatePayload {
	
	/**
	 * Creates a JSON payload for adding a comment.
	 * This method reads the JSON file specified in TestConstants.ADD_COMMENT_JSON_PAYLOAD_PATH,
	 * modifies it with the given comment, and returns it as a string.
	 * @param comment The comment to add to the payload.
	 * @return A JSON payload string with the specified comment added.
	 * @throws JSONException If there is an error parsing the JSON.
	 * @throws IOException If there is an error reading the JSON file.
	 */
	public String addCommentPayload(String comment) throws JSONException, IOException {

		JSONObject jsonObject = new JSONObject(
				new String(Files.readAllBytes(Paths.get(TestConstants.ADD_COMMENT_JSON_PAYLOAD_PATH ))));
		
		jsonObject.getJSONObject("body").getJSONArray("content").getJSONObject(0).getJSONArray("content")
				.getJSONObject(0).put("text", comment);
		
		return jsonObject.toString();
	}
	
	/**
	 * Creates an invalid JSON payload for adding a comment.
	 * This method reads the JSON file specified in TestConstants.INVALID_ADD_COMMENT_JSON_PAYLOAD_PATH,
	 * modifies it with the given comment, and returns it as a string.
	 * @param comment The comment to add to the payload.
	 * @return An invalid JSON payload string with the specified comment added.
	 * @throws JSONException If there is an error parsing the JSON.
	 * @throws IOException If there is an error reading the JSON file.
	 */
	public String invalidAddCommentPayload(String comment) throws JSONException, IOException {

		JSONObject jsonObject = new JSONObject(
				new String(Files.readAllBytes(Paths.get(TestConstants.INVALID_ADD_COMMENT_JSON_PAYLOAD_PATH ))));
		
		jsonObject.getJSONObject("body").getJSONArray("content").getJSONObject(0).getJSONArray("content")
				.getJSONObject(0).put("text", comment);
		
		return jsonObject.toString();
	}
}
