package yukcommon.model.meta;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import com.google.gson.annotations.SerializedName;

import yukcommon.dic.type.MetaSettingType;
import yukcommon.exception.NotNullAllowedException;
import yukcommon.exception.WrongOperationException;
import yukcommon.model.AbsModel;

public class MetaSetting  extends AbsModel{
	private String name = "";
	@SerializedName("type")
	private MetaSettingType type;
	private String query = "";
	private Map<String, SubMetaSettingData> map = new ConcurrentSkipListMap<String, SubMetaSettingData>();
	
	public String getName() {
		if(name.isEmpty())
			throw new WrongOperationException("meta setting name is empty");
		return name;
	}
	public void setName(String name) {
		if(name == null || name.isEmpty())
			throw new NotNullAllowedException("meta setting name can't be null");
		this.name = name;
	}
	public MetaSettingType getType() {
		if(type == null)
			throw new WrongOperationException("meta setting type is empty");
		return type;
	}
	public void setType(MetaSettingType type) {
		if(type == null)
			throw new NotNullAllowedException("meta setting type can't be null");
		this.type = type;
	}
	public String getQuery() {
		if(query.isEmpty())
			throw new WrongOperationException("meta setting query is empty");
		return query;
	}
	public void setQuery(String query) {
		if(query == null || query.isEmpty())
			throw new NotNullAllowedException("meta setting query can't be null");
		this.query = query;
	}
	public Map<String, SubMetaSettingData> getMap() {
		return map;
	}
	public void setMap(Map<String, SubMetaSettingData> map) {
		if(map == null)
			throw new NotNullAllowedException("meta setting map can't be null");
		this.map = map;
	}
	
	
}
