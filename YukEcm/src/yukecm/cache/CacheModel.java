package yukecm.cache;

import com.google.gson.annotations.SerializedName;

import yukcommon.dic.type.CacheWorkType;
import yukcommon.exception.NotNullAllowedException;
import yukcommon.exception.WrongOperationException;

public class CacheModel{
	protected CacheModel(){}
	@SerializedName("workType")
	private CacheWorkType workType;
	private String name = "";
	private String key;
	private String value;
	private int failCount = 0;
	
	public CacheWorkType getWorkType() {
		if(workType == null)
			throw new WrongOperationException("cache type is empty");
		return workType;
	}
	public void setWorkType(CacheWorkType workType) {
		if(workType == null)
			throw new NotNullAllowedException("cache type can't be null");
		this.workType = workType;
	}
	public String getName() {
		if(name.isEmpty())
			throw new WrongOperationException("cache name is empty");
		return name;
	}
	public void setName(String name) {
		if(name == null || name.isEmpty())
			throw new NotNullAllowedException("cache name can't be null");
		this.name = name;
	}
	public String getKey() {
		if(key.isEmpty())
			throw new WrongOperationException("cache key is empty");
		return key;
	}
	public void setKey(String key) {
		if(key == null || key.isEmpty())
			throw new NotNullAllowedException("cache key can't be null");
		this.key = key;
	}
	public String getValue() {
		if(value.isEmpty())
			throw new WrongOperationException("cache value is empty");
		return value;
	}
	public void setValue(String value) {
		if(value == null || value.isEmpty())
			throw new NotNullAllowedException("cache value can't be null");
		this.value = value;
	}
	public int getFailCount() {
		return failCount;
	}
	public void addFailCount() {
		this.failCount = this.failCount + 1;
	}
	
	
}
