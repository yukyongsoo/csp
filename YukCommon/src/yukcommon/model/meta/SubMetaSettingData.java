package yukcommon.model.meta;

import com.google.gson.annotations.SerializedName;

import yukcommon.dic.type.SubMetaSettingType;
import yukcommon.exception.NotNullAllowedException;
import yukcommon.exception.WrongOperationException;
import yukcommon.model.AbsModel;

public class SubMetaSettingData  extends AbsModel{
	private String keyName = "";
	private int order = -1;
	@SerializedName("type")
	private SubMetaSettingType type;
	
	public String getKeyName() {
		if(keyName.isEmpty())
			throw new WrongOperationException("sub meta setting keyName is empty");
		return keyName;
	}
	public void setKeyName(String keyName) {
		if(keyName == null || keyName.isEmpty())
			throw new NotNullAllowedException("sub meta setting keyName can't be empty");
		this.keyName = keyName;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		if(order < 0)
			throw new WrongOperationException("setting order can't be minus");
		this.order = order;
	}
	public SubMetaSettingType getType() {
		if(type == null)
			throw new WrongOperationException("sub meta setting type is empty");
		return type;
	}
	public void setType(SubMetaSettingType type) {
		if(type == null)
			throw new NotNullAllowedException("sub meta setting type can't be empty");
		this.type = type;
	}
	
	
}
