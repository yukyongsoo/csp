package yukecmapi;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import yukcommon.dic.EtcDic;
import yukcommon.dic.type.UserManageType;
import yukcommon.model.Group;
import yukcommon.model.User;
import yukcommon.net.NetClient;
import yukcommon.net.NetWrapper;
import yukcommon.util.EncryptUtil;
import yukcommon.util.JsonUtil;

public class UserGroupApiImpl extends AbsApiImpl {

	protected UserGroupApiImpl(NetClient client, NetWrapper wrap) {
		super(client, wrap);
	}

	public String addUser(String id, String name, String password, UserManageType type) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		User user = new User();
		user.setId(id);
		user.setName(name);
		user.setType(type);
		user.setPassword(EncryptUtil.getInstance().encryptDBKey(password));
		wrap.addHeader(EtcDic.USER, JsonUtil.toJson(user));
		client.excute(wrap);
		return wrap.getHeaderValue(EtcDic.RETID);
	}

	public void delUser(String id) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		User user = new User();
		user.setId(id);
		wrap.addHeader(EtcDic.USER, JsonUtil.toJson(user));
		client.excute(wrap);
	}

	public void updUser(String id, String newName, String newPassword, UserManageType type) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		User user = new User();
		user.setId(id);
		user.setName(newName);
		user.setType(type);
		user.setPassword(EncryptUtil.getInstance().encryptDBKey(newPassword));
		wrap.addHeader(EtcDic.USER, JsonUtil.toJson(user));
		client.excute(wrap);
	}
	
	public void updUserParent(String id, String parentId) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		User user = new User();
		user.setId(id);
		user.setParentId(parentId);
		wrap.addHeader(EtcDic.USER, JsonUtil.toJson(user));
		client.excute(wrap);
	}
	
	public List<User> getUserList() throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		client.excute(wrap);
		String json = wrap.getHeaderValue(EtcDic.USER);
		return JsonUtil.fromJson(json, JsonUtil.LISTUSER);
	}
	
	public List<User> getGroupUser(String parentId) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		User user = new User();
		user.setParentId(parentId);
		wrap.addHeader(EtcDic.USER, JsonUtil.toJson(user));
		client.excute(wrap);
		String json = wrap.getHeaderValue(EtcDic.USER);
		return JsonUtil.fromJson(json, JsonUtil.LISTUSER);
	}

	public void checkUser(String id, String password) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		User user = new User();
		user.setId(id);
		user.setPassword(EncryptUtil.getInstance().encryptDBKey(password));
		client.excute(wrap);
	}

	public String addGroup(String name, String parentId) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		Group group = new Group();
		group.setName(name);
		group.setParentId(parentId);
		wrap.addHeader(EtcDic.GROUP, JsonUtil.toJson(group));
		client.excute(wrap);
		return wrap.getHeaderValue(EtcDic.RETID);
	}

	public void delGroup(String id) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		Group group = new Group();
		group.setId(id);
		wrap.addHeader(EtcDic.GROUP, JsonUtil.toJson(group));
		client.excute(wrap);
	}

	public Group getGroup(String id) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		Group group = new Group();
		group.setId(id);
		wrap.addHeader(EtcDic.GROUP, JsonUtil.toJson(group));
		client.excute(wrap);
		String json = wrap.getHeaderValue(EtcDic.GROUP);
		return JsonUtil.fromJson(json, Group.class);
	}

	public void updGroup(String id, String name, String parentId) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		Group group = new Group();
		group.setId(id);
		group.setName(name);
		group.setParentId(parentId);
		wrap.addHeader(EtcDic.GROUP, JsonUtil.toJson(group));
		client.excute(wrap);
	}

	public List<Group> getGroupList() throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		Group group = new Group();
		wrap.addHeader(EtcDic.GROUP, JsonUtil.toJson(group));
		client.excute(wrap);
		String json = wrap.getHeaderValue(EtcDic.GROUP);
		return JsonUtil.fromJson(json, JsonUtil.LISTGROUP);
	}

	public List<Group> getGroupChildList(String parentId) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		Group group = new Group();
		group.setId(parentId);
		wrap.addHeader(EtcDic.GROUP, JsonUtil.toJson(group));
		client.excute(wrap);
		String json = wrap.getHeaderValue(EtcDic.GROUP);
		return JsonUtil.fromJson(json, JsonUtil.LISTGROUP);
	}



	

}
