package yukcommon.model;

import yukcommon.exception.NotNullAllowedException;
import yukcommon.exception.WrongOperationException;

public class Acl extends AbsModel{
	private String id = "";
	private String name = "";
	
	
	public String getId() {
		if(id.isEmpty())
			throw new WrongOperationException("acl id is empty");
		return id;
	}
	public void setId(String id) {
		if(id == null || id.isEmpty())
			throw new NotNullAllowedException("acl id can't be null");
		this.id = id;
	}
	public String getName() {
		if(name.isEmpty())
			throw new WrongOperationException("acl name is empty");
		return name;
	}
	public void setName(String name) {
		if(name == null || name.isEmpty())
			throw new NotNullAllowedException("acl name can't be null");
		this.name = name;
	}

	@Override
	public String toString() {
		return "Current AcL Data: Aclid = " + id + ".name=" + name;
	}
	
}
