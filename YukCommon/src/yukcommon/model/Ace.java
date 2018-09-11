package yukcommon.model;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import yukcommon.dic.type.PermissionType;
import yukcommon.exception.NotNullAllowedException;
import yukcommon.exception.WrongOperationException;

public class Ace extends AbsModel{
	private String id = "";
	private String childId = "";
	private Map<PermissionType, Boolean> permissionMap = new EnumMap<PermissionType, Boolean>(PermissionType.class);
	
	public Ace() {
		if(permissionMap.size() < 1) {
			for(PermissionType type : PermissionType.values())
				permissionMap.put(type, false);
		}
	}
	
	public String getId() {
		if(id.isEmpty())
			throw new WrongOperationException("ace contain aclid is null");
		return id;
	}
	public void setId(String id) {
		if(id == null || id.isEmpty())
			throw new NotNullAllowedException("ace id can't be null");
		this.id = id;
	}
	public String getChildId() {
		if(childId.isEmpty())
			throw new WrongOperationException("ace target is null");
		return childId;
	}
	public void setChildId(String childId) {
		if(childId == null || childId.isEmpty())
			throw new NotNullAllowedException("ace entry id can't be null");
		this.childId = childId;
	}
	
	public List<PermissionType> getHasPermission() {
		return new ArrayList<PermissionType>(permissionMap.keySet());
	}
	
	public Map<PermissionType, Boolean> getPermissionSet(){
		return permissionMap;
	}
	
	public void setPermission(Map<PermissionType, Boolean> map) {
		if(map == null)
			throw new NotNullAllowedException("Permission target Map can't be null");
		for(PermissionType type : PermissionType.values())
			permissionMap.put(type, false);
		for(Entry<PermissionType, Boolean> entry : map.entrySet()) {
			if(entry.getValue())
				permissionMap.put(entry.getKey(), entry.getValue());
		}
	}
	
	public void addMultiPermission(List<PermissionType> hasPermission) {
		if(hasPermission == null)
			throw new NotNullAllowedException("acl permission List can't be null");
		for(PermissionType type : hasPermission) 
			addPermission(type);
	}
	
	public void removeMultiPermission(List<PermissionType> hasPermission) {
		if(hasPermission == null)
			throw new NotNullAllowedException("acl permission List can't be null");
		for(PermissionType type : hasPermission) 
			removePermission(type);
	}
	
	public void addPermission(PermissionType type) {
		if(type == null)
			throw new NotNullAllowedException("Permisson add neeed type");
		permissionMap.put(type, true);
	}
	
	public void removePermission(PermissionType type) {
		if(type == null)
			throw new NotNullAllowedException("Permisson remove neeed type");
		permissionMap.put(type, false);
	}
	
	@Override
	public String toString() {
		return "Current Ace Data: Aclid = " + id + ".AceId=" + childId + ".Permisson=" + permissionMap.toString();
	}
}
