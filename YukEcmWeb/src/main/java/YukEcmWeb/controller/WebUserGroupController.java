package YukEcmWeb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import YukEcmWeb.SessionBean;
import yukcommon.dic.EtcDic;
import yukcommon.dic.UriDic;
import yukcommon.model.Group;
import yukcommon.model.User;
import yukcommon.util.JsonUtil;
import yukecmapi.UserGroupApi;

@RestController
public class WebUserGroupController{
	@Autowired(required = true)
    private SessionBean session;

	@RequestMapping(UriDic.GETUSER)
	public List<User> getUser() throws Exception {
		UserGroupApi api = new UserGroupApi(session.getConn());
		return api.getUserList();
	}
	
	@RequestMapping(UriDic.GETGROUPUSER)
	public List<User> getGroupUser(@RequestHeader(EtcDic.USER) String json) throws Exception {
		User user = JsonUtil.fromJson(json, User.class);
		UserGroupApi api = new UserGroupApi(session.getConn());
		return api.getGroupUser(user.getParentId());
	}
	
	
	@RequestMapping(UriDic.ADDUSER)
	public void addUser(@RequestHeader(EtcDic.USER) String json) throws Exception {
		User user = JsonUtil.fromJson(json, User.class);
		UserGroupApi api = new UserGroupApi(session.getConn());
		api.addUser(user.getId(), user.getName(), user.getPassword(),user.getType());
	}
	
	@RequestMapping(UriDic.UPDUSER)
	public void updUser(@RequestHeader(EtcDic.USER) String json) throws Exception {
		User user = JsonUtil.fromJson(json, User.class);
		UserGroupApi api = new UserGroupApi(session.getConn());
		api.updUser(user.getId(), user.getName(), user.getPassword(),user.getType());
	}
	
	@RequestMapping(UriDic.UPDUSERPARENT)
	public void updUserParent(@RequestHeader(EtcDic.USER) String json) throws Exception {
		User user = JsonUtil.fromJson(json, User.class);
		UserGroupApi api = new UserGroupApi(session.getConn());
		api.updUserParent(user.getId(), user.getParentId());
	}
	
	@RequestMapping(UriDic.DELUSER)
	public void delUser(@RequestHeader(EtcDic.USER) String json) throws Exception {
		User user = JsonUtil.fromJson(json, User.class);
		UserGroupApi api = new UserGroupApi(session.getConn());
		api.delUser(user.getId());
	}
	
	@RequestMapping(UriDic.ADDGROUP)
	public String addGroup(@RequestHeader(EtcDic.GROUP) String json) throws Exception {
		Group group = JsonUtil.fromJson(json, Group.class);
		UserGroupApi api = new UserGroupApi(session.getConn());
		return api.addGroup(group.getName(), group.getParentId());
	}
	
	@RequestMapping(UriDic.DELGROUP)
	public void delGroup(@RequestHeader(EtcDic.GROUP) String json) throws Exception {
		Group group = JsonUtil.fromJson(json, Group.class);
		UserGroupApi api = new UserGroupApi(session.getConn());
		api.delGroup(group.getId());
	}
	
	@RequestMapping(UriDic.GETGROUPLIST)
	public List<Group> getGroupList() throws Exception {
		UserGroupApi api = new UserGroupApi(session.getConn());
		return api.getGroupList();
	}
	
	@RequestMapping(UriDic.GETGROUPCHILDLIST)
	public List<Group> getGroup(@RequestHeader(EtcDic.GROUP) String json) throws Exception {
		Group group = JsonUtil.fromJson(json, Group.class);
		UserGroupApi api = new UserGroupApi(session.getConn());
		return api.getGroupChildList(group.getId());
	}
	
	@RequestMapping(UriDic.UPDGROUP)
	public void updGroup(@RequestHeader(EtcDic.GROUP) String json) throws Exception {
		Group group = JsonUtil.fromJson(json, Group.class);
		UserGroupApi api = new UserGroupApi(session.getConn());
		api.updGroup(group.getId(), group.getName(), group.getParentId());
	}
}
