package yukecmapi;

import java.util.List;

import yukcommon.dic.UriDic;
import yukcommon.dic.type.UserManageType;
import yukcommon.model.Group;
import yukcommon.model.User;

public class UserGroupApi extends AbsApi{
	public UserGroupApi(EcmConnection conn) {
		super(conn);
	}

	public String addUser(String id, String name, String password,UserManageType type) throws Exception{
		UserGroupApiImpl impl = EcmApiFactory.getUserGroupApi(conn,UriDic.ADDUSER);
		return impl.addUser(id, name, password,type);
	}

	public void delUser(String id) throws Exception{
		UserGroupApiImpl impl = EcmApiFactory.getUserGroupApi(conn,UriDic.DELUSER);
		impl.delUser(id);
	}

	public void updUser(String id, String newName, String newPassword,UserManageType type) throws Exception{
		UserGroupApiImpl impl = EcmApiFactory.getUserGroupApi(conn,UriDic.UPDUSER);
		impl.updUser(id, newName, newPassword,type);
	}
	
	public void updUserParent(String id, String parentId) throws Exception{
		UserGroupApiImpl impl = EcmApiFactory.getUserGroupApi(conn,UriDic.UPDUSERPARENT);
		impl.updUserParent(id, parentId);
	}
	
	public void checkUser(String id, String password) throws Exception{
		UserGroupApiImpl impl = EcmApiFactory.getUserGroupApi(conn,UriDic.CHECKUSER);
		impl.checkUser(id, password);
	}
	
	public List<User> getUserList() throws Exception{
		UserGroupApiImpl impl = EcmApiFactory.getUserGroupApi(conn,UriDic.GETUSER);
		return impl.getUserList();
	}
	
	public List<User> getGroupUser(String parentId) throws Exception{
		UserGroupApiImpl impl = EcmApiFactory.getUserGroupApi(conn,UriDic.GETGROUPUSER);
		return impl.getGroupUser(parentId);
	}
	
	public String addGroup(String name,String parentId) throws Exception{
		UserGroupApiImpl impl = EcmApiFactory.getUserGroupApi(conn,UriDic.ADDGROUP);
		return impl.addGroup(name,parentId);
	}
	
	public void delGroup(String id) throws Exception{
		UserGroupApiImpl impl = EcmApiFactory.getUserGroupApi(conn,UriDic.DELGROUP);
		impl.delGroup(id);
	}
	
	public Group getGroup(String id) throws Exception{
		UserGroupApiImpl impl = EcmApiFactory.getUserGroupApi(conn,UriDic.GETGROUP);
		return impl.getGroup(id);
	}
	
	public void updGroup(String id, String name, String parentId) throws Exception{
		UserGroupApiImpl impl = EcmApiFactory.getUserGroupApi(conn,UriDic.UPDGROUP);
		impl.updGroup(id, name, parentId);
	}

	public List<Group> getGroupList() throws Exception{
		UserGroupApiImpl impl = EcmApiFactory.getUserGroupApi(conn,UriDic.GETGROUPLIST);
		return impl.getGroupList();
	}
	
	public List<Group> getGroupChildList(String parentId) throws Exception{
		UserGroupApiImpl impl = EcmApiFactory.getUserGroupApi(conn,UriDic.GETGROUPCHILDLIST);
		return impl.getGroupChildList(parentId);
	}

	
	
}
