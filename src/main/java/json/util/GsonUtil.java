package json.util;

import com.google.gson.Gson;

public class GsonUtil {

	private static Gson gson = new Gson();

	public static String toJson(Object object) {
        String json = gson.toJson(object);
        return json;
    }
	
	@SuppressWarnings("unchecked")
	public static <T> T toBean(String json, Class<T> cls) throws Exception{
	    try {
			Object obj = gson.fromJson(json, cls);
			return (T)obj;
		} catch (Exception e) {
			throw e;
		}
	}
}
