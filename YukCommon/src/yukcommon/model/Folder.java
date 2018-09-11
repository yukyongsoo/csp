package yukcommon.model;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import yukcommon.exception.NotNullAllowedException;
import yukcommon.exception.WrongOperationException;

public class Folder extends AbsModel{
	//if parentId is Null than root folder
	private String parentId = "";
	private String id = "";
	private String name = "";
	private String descr = "";
	private String aclId = "";
	private boolean isDerived = true;

	public String getName() throws UnsupportedEncodingException {
		return URLDecoder.decode(getEncodedName(),"UTF-8");
	}

	public String getEncodedName() {
		if(name.isEmpty())
			throw new WrongOperationException("folder name is empty");
		return name;
	}

	public void setName(String name) throws UnsupportedEncodingException {
		if(name == null || name.isEmpty())
			throw new NotNullAllowedException("folder name is empty");
		this.name = URLEncoder.encode(name,"UTF-8");
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		if(parentId == null)
			parentId = "";
		this.parentId = parentId;
	}

	public String getId() {
		if(id.isEmpty())
			throw new WrongOperationException("folder id is empty");
		return id;
	}

	public void setId(String id) {
		if(id == null || id.isEmpty())
			throw new NotNullAllowedException("folder id can't be empty");
		this.id = id;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getAclId() {
		return aclId;
	}

	public void setAclId(String aclId) {
		if(aclId != null)
			this.aclId = aclId;
	}

	public boolean isDerived() {
		return isDerived;
	}

	public void setDerived(boolean isDerived) {
		this.isDerived = isDerived;
	}

	@Override
	public String toString() {		
		return "Current Folder Data: id = " + id + ".parentId=" + parentId + ".name=" + name +
				".descr=" + descr + ".aclId=" + aclId + ".isDerived=" + isDerived;
	}
	
}
