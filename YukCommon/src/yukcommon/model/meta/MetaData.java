package yukcommon.model.meta;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import yukcommon.exception.NotNullAllowedException;
import yukcommon.exception.WrongOperationException;
import yukcommon.model.AbsModel;

public class MetaData  extends AbsModel{
	private String metaName = "";
	private Map<String,String> map = new HashMap<String, String>();

	public String getMetaName() {
		if(metaName.isEmpty())
			throw new WrongOperationException("meta Data name is empty");
		return metaName;
	}

	public void setMetaName(String metaName) {
		if(metaName == null || metaName.isEmpty())
			throw new NotNullAllowedException("meta Data name can't be null");
		this.metaName = metaName;
	}

	public void addMeta(String columnName, String value) {
		map.put(columnName, value);
	}

	public void delMeta(String columnName){
		map.remove(columnName);
	}

	public Object getMeta(String columnName) {
		return map.get(columnName);
	}
	
	public String getMetaAsString(String columnName) {
		return map.get(columnName);
	}
	
	public long getMetaAsLong(String columnName) {
		return Long.parseLong(map.get(columnName));
	}
	
	public int getMetaAsInt(String columnName) {
		return Integer.parseInt(map.get(columnName));
	}
	
	public boolean getMetaAsBool(String columnName) {
		return Boolean.parseBoolean(map.get(columnName));
	}

	public Set<String> getKeySet(){
		return map.keySet();
	}
}
