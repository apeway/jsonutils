package json.jsonparse;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JsonAttr implements Comparable<JsonAttr>{

	public static final String ROOT_CODE = "ROOT";
	
	private String code; // 属性名
	private String codePath; // 属性名层级路径，用.分隔
	private String name; // 预留
	private AttrType dataType;
	
//	private String uid;
//	private String puid = null;
	private List<JsonAttr> children;
	
	public JsonAttr() {
		super();
	}
	public JsonAttr(String code, String codePath, AttrType dataType) {
		super();
		this.code = code;
		this.codePath = codePath;
		this.dataType = dataType;
	}
	public static JsonAttr createRootNode() {
		return new JsonAttr("ROOT","ROOT",AttrType.OBJECT);
	}
	
	public void print() {
		print(1);
	}
	private void print(int level) {
		for (int i = 0; i < level; i++) {
			System.out.print("|--");
		}
		System.out.println(code + "," + dataType);
		if(children != null) {
			for (JsonAttr child : children) {
				child.print(level + 1);
			}
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codePath == null) ? 0 : codePath.hashCode());
		result = prime * result + ((dataType == null) ? 0 : dataType.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JsonAttr other = (JsonAttr) obj;
		if (codePath == null) {
			if (other.codePath != null)
				return false;
		} else if (!codePath.equals(other.codePath))
			return false;
		if (dataType != other.dataType)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "JsonAttr [codePath=" + codePath + ", dataType=" + dataType + "]";
	}
	@Override
	public int compareTo(JsonAttr o) {
		return codePath.compareTo(o.getCodePath());
	}

}
