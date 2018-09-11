package yukcommon.model;

import com.google.gson.annotations.SerializedName;

import yukcommon.dic.type.UserManageType;
import yukcommon.exception.NotNullAllowedException;
import yukcommon.exception.WrongOperationException;

public class User extends AbsModel{
	private String id = "";
	private String name = "";
	private String parentid = "";
	private String password = "";
	@SerializedName("UserManageType")
	private UserManageType type = UserManageType.PUBLIC;
	
	public String getId() {
		if(id.isEmpty())
			throw new WrongOperationException("user id is null");
		return id;
	}
	public void setId(String id) {
		if(id == null || id.isEmpty())
			throw new NotNullAllowedException("user id can't be null");
		this.id = id;
	}
	public String getName() {
		if(name.isEmpty())
			throw new WrongOperationException("user name is null");
		return name;
	}
	public void setName(String name) {
		if(name == null || name.isEmpty())
			throw new NotNullAllowedException("user name can't be null");
		this.name = name;
	}
	public String getPassword() {
		if(password.isEmpty())
			throw new WrongOperationException("user password is null");
		return password;
	}
	public void setPassword(String password) {
		if(password == null || password.isEmpty())
			throw new NotNullAllowedException("user password can't be null");
		this.password = password;
	}
	public String getParentId() {
		return parentid;
	}
	public void setParentId(String parent) {
		this.parentid = parent;
	}
	public UserManageType getType() {
		if(type == null)
			throw new WrongOperationException("user Manage Type is null");
		return type;
	}
	public void setType(UserManageType type) {
		if(type == null)
			throw new NotNullAllowedException("user Manage Type can't be null");
		this.type = type;
	}
	
	@Override
	public String toString() {		
		return "Current User Data: id = " + id + ".name=" + name + ".type=" + type.toString() + 
		".parentid=" + parentid;
	}
}
