package json.jsonparse;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

public class JsonAttrUtil {

	public static void parse(String json, Set<JsonAttr> allAttrs) {
		JSONObject jsonObj = JSONObject.fromObject(json);
		parse(jsonObj, JsonAttr.ROOT_CODE, allAttrs);
	}
	
	private static void parse(JSONObject jsonObj, String prefix, Set<JsonAttr> allAttrs) {
		Iterator<?> keyIter = jsonObj.keys();
		while(keyIter.hasNext()) {
			String key = (String) keyIter.next();
			String attrKey = prefix + "." + key;
			Object val = jsonObj.get(key);
			if(JSONUtils.isNumber(val)) {
				allAttrs.add(new JsonAttr(key, attrKey, AttrType.NUMBER));
			} else if(JSONUtils.isBoolean(val)) {
				allAttrs.add(new JsonAttr(key, attrKey, AttrType.BOOL));
			} else if(JSONUtils.isString(val)) {
				allAttrs.add(new JsonAttr(key, attrKey, AttrType.STRING));
			} else if(JSONUtils.isNull(val)) {
				allAttrs.add(new JsonAttr(key, attrKey, AttrType.OBJECT));
			} else if(JSONUtils.isObject(val)) {
				allAttrs.add(new JsonAttr(key, attrKey, AttrType.OBJECT));
				parse((JSONObject)val, attrKey, allAttrs);
			} else if(JSONUtils.isArray(val)) {
				allAttrs.add(new JsonAttr(key, attrKey, AttrType.ARRAY));
				parse((JSONArray)val, attrKey, allAttrs);
			}
		}
	}
	
	private static void parse(JSONArray jsonArr, String prefix, Set<JsonAttr> allAttrs) {
		for (int i = 0; i < jsonArr.size(); i++) {
			Object arr_i = jsonArr.get(i);
			if(JSONUtils.isObject(arr_i)) {
				parse((JSONObject)arr_i, prefix, allAttrs);
			} else if(JSONUtils.isArray(arr_i)) {
				parse((JSONArray)arr_i, prefix, allAttrs);
			}
		}
	}
	
	public static JsonAttr getJsonAttrTree(Set<JsonAttr> allAttrs) {
		JsonAttr root = JsonAttr.createRootNode();
		root.setChildren(findChildren(JsonAttr.ROOT_CODE, allAttrs));
		return root;
	}
	
	private static List<JsonAttr> findChildren(String parentCodePath, Set<JsonAttr> allAttrs) {
		List<JsonAttr> children = allAttrs.stream().filter(e -> e.getCodePath().startsWith(parentCodePath+"."))
				.filter(e -> !e.getCodePath().substring(parentCodePath.length() + 1).contains("."))
				.filter(e -> {
					if(e.getDataType() == AttrType.OBJECT 
							|| e.getDataType() == AttrType.ARRAY) {
						e.setChildren(findChildren(e.getCodePath(), allAttrs));
					}
					return true;
				})
				.sorted().collect(Collectors.toList());
		return children;
	}
	
	public static void main(String[] args) {
		Set<JsonAttr> allAttrs = new LinkedHashSet<>();
		String json = "{"
				+ "\"a1\":1, "
				+ "\"b1\":\"strb1\", "
				+ "\"c1\":{\"c1a2\":2, \"c1b2\":\"strc1b2\", \"c1c2\":{\"c1c2a3\":3, \"c1c2b3\":\"strc1c2b3\"}}, "
				+ "\"d1\":[{\"d1a2\":4, \"d1b2\":\"strd1b2\", \"d1c2\":{\"d1c2a3\":5, \"d1c2b3\":\"strd1c2b3\"}}"
					+ ",{\"d1a2\":6, \"d1b2\":\"strd1b2\", \"d1c2\":{\"d1c2a3\":7, \"d1c2b3\":\"strd1c2b3\"}}],"
				+ "\"e1\":[\"str1e1\",\"str2e1\"],"
				+ "\"f1\":true,"
				+ "\"g1\":null"
				+ "}";
		String json2 = "{"
				+ "\"A1\":1, "
				+ "\"c1\":{\"c1A2\":2, \"c1c2\":{\"c1c2A3\":3}}"
				+ "}";
		
		parse(json, allAttrs);
		parse(json2, allAttrs);
		getJsonAttrTree(allAttrs).print();
		
	}
}
