package yukcommon.model;

import yukcommon.exception.NotNullAllowedException;
import yukcommon.exception.WrongOperationException;

public class Group extends AbsModel{
	private String id = "";
	private String parentId = "";
	private String name = "";
	
	public String getId() {
		if(id.isEmpty())
			throw new WrongOperationException("group id is null");
		return id;
	}
	public void setId(String id) {
		if(id == null || id.isEmpty())
			throw new NotNullAllowedException("group id can't be null");
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		if(parentId == null)
			return;
		this.parentId = parentId;
	}
	public String getName() {
		if(name.isEmpty())
			throw new WrongOperationException("group name is null");
		return name;
	}
	public void setName(String name) {
		if(name == null || name.isEmpty())
			throw new NotNullAllowedException("group name can't be null");
		this.name = name;
	}
	
	@Override
	public String toString() {		
		return "Current Group Data: id = " + id + ".parentId=" + parentId + ".name=" + name;
	}
}
