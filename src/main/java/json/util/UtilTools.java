package json.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * 基于jackson-databind
 * com.fasterxml.jackson.databind.ObjectMapper
 */
public class UtilTools {

	public static String toJSON(Object data) {
		ObjectMapper om = new ObjectMapper();
		try {
			return om.writeValueAsString(data);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public static <T> T json2Object(String content, Class<T> clazz) {
		ObjectMapper om = new ObjectMapper();
		try {
			return om.readValue(content, clazz);
		} catch (IOException e) {
			throw new RuntimeException("convert json to object error", e);
		}
	}

	public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}

	public static JsonNode jsonReadNode(String content, String key) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode root = mapper.readTree(content);
			return root.path(key);
		} catch (Exception e) {
			throw new RuntimeException("convert json to object error", e);
		}
	}

	public static String jsonReadValue(String content, String key) {
		JsonNode data = jsonReadNode(content, key);
		if (!data.isMissingNode()) {
			return data.toString();
		} else {
			return null;
		}
	}
}
