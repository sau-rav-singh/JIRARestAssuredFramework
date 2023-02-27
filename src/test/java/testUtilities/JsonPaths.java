package testUtilities;

import java.util.HashMap;
import java.util.Map;

/**
 * This class provides a mapping of Jira issue fields to their corresponding
 * JSON paths. It is used by the Jira API client to extract the values of these
 * fields from Jira API responses.
 */
public class JsonPaths {
	// Map to store field name to JSON path mappings
	private Map<String, String> fieldToJsonPathMap;

	/**
	 * Constructor that initializes the field to JSON path mappings.
	 */
	public JsonPaths() {
		fieldToJsonPathMap = new HashMap<>();
		fieldToJsonPathMap.put("Display Name", "author.displayName");
		fieldToJsonPathMap.put("Comment", "body.content[0].content[0].text");
		fieldToJsonPathMap.put("Update Author Email Address", "updateAuthor.emailAddress");
	}

	/**
	 * Returns the JSON path for the specified Jira issue field.
	 * @param field the name of the Jira issue field
	 * @return the corresponding JSON path for the field
	 * @throws IllegalArgumentException if the specified field name is invalid
	 */
	public String getJsonPathForField(String field) {
		String jsonPath = fieldToJsonPathMap.get(field);
		if (jsonPath == null) {
			throw new IllegalArgumentException("Invalid field name: " + field);
		}
		return jsonPath;
	}
}
