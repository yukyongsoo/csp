package yukcommon.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import yukcommon.dic.type.RuleType;
import yukcommon.exception.NotNullAllowedException;
import yukcommon.exception.WrongOperationException;

public class WorkingGroup extends AbsModel{
	private String id = "";
	private String name = "";
	private boolean audit = false;
	private List<String> initList = new ArrayList<String>();
	private String migId = "";
	private String desId = "";
	
	//use for net communication
	private String tempId = "";
	@SerializedName("tempType")
	private RuleType tempType;
	
	public String getId() {
		if(id.isEmpty())
			throw new WrongOperationException("working group id is empty");
		return id;
	}
	public void setId(String id) {
		if(id == null || id.isEmpty())
			throw new NotNullAllowedException("working group id can't be null");
		this.id = id;
	}
	public String getName() {
		if(name.isEmpty())
			throw new WrongOperationException("working group name is empty");
		return name;
	}
	public void setName(String name) {
		if(name == null || name.isEmpty())
			throw new NotNullAllowedException("working group name can't be null");
		this.name = name;
	}
	public boolean isAudit() {
		return audit;
	}
	public void setAudit(boolean audit) {
		this.audit = audit;
	}
	public List<String> getInitList() {
		return initList;
	}
	public void setInitList(List<String> initList) {
		if(initList == null)
			throw new NotNullAllowedException("working group initList can't be null");
		this.initList = initList;
	}
	public String getMigId() {
		return migId;
	}
	public void setMigId(String migId) {
		this.migId = migId;
	}
	public String getDesId() {
		return desId;
	}
	public void setDesId(String desId) {
		this.desId = desId;
	}
	public String getTempId() {
		return tempId;
	}
	public void setTempId(String tempId) {
		if(tempId == null || tempId.isEmpty())
			throw new NotNullAllowedException("working group tempId can't be null");
		this.tempId = tempId;
	}
	public RuleType getTempType() {
		if(tempType == null)
			throw new WrongOperationException("working group temp Rule Type is null");
		return tempType;
	}
	public void setTempType(RuleType tempType) {
		if(tempType == null)
			throw new NotNullAllowedException("working group tempType can't be null");
		this.tempType = tempType;
	}
	
	@Override
	public String toString() {		
		return "Current WorkGroup Data: id = " + id + ".name=" + name + ".audit=" + audit + 
		".initList=" + initList.toString()  + ".migId=" + migId + ".desId=" + desId  + ".tempId=" + tempId
		+ ".tempType=" + tempType;
	}
}
