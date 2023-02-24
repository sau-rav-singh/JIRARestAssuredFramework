package utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONException;
import org.json.JSONObject;

public class CreatePayload {

	public String addCommentPayload(String comment) throws JSONException, IOException {

		JSONObject jsonObject = new JSONObject(
				new String(Files.readAllBytes(Paths.get("src\\test\\resources\\data\\json\\addComment.json"))));
		
		jsonObject.getJSONObject("body").getJSONArray("content").getJSONObject(0).getJSONArray("content")
				.getJSONObject(0).put("text", comment);
		
		return jsonObject.toString();
	}

}
