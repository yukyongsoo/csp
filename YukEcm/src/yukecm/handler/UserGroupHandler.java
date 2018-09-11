package yukecm.handler;

import java.util.List;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import yukcommon.dic.EtcDic;
import yukcommon.dic.UriDic;
import yukcommon.model.Group;
import yukcommon.model.User;
import yukcommon.net.AbsNetHandler;
import yukcommon.util.JsonUtil;
import yukecm.controller.GroupController;
import yukecm.controller.SecureController;
import yukecm.controller.UserController;

public class UserGroupHandler extends AbsNetHandler{
	@Override
	public void handleImpl(HttpRequest request, HttpResponse response, String uri,User user) throws Exception {
		if (uri.equals(UriDic.ADDUSER)) {
			String id = UserController.getInstance().addUser(request,response);
			response.addHeader(EtcDic.RETID, id);
		}
		else if (uri.equals(UriDic.GETUSER)) {
			List<User> userList = UserController.getInstance().getUserList(request,response);
			response.addHeader(EtcDic.USER, JsonUtil.toJson(userList));
		}
		else if(uri.equals(UriDic.GETGROUPUSER)) {
			List<User> userList = UserController.getInstance().getGroupUser(request,response);
			response.addHeader(EtcDic.USER, JsonUtil.toJson(userList));
		}
		else if (uri.equals(UriDic.DELUSER)) {
			UserController.getInstance().delUser(request,response);
		}
		else if (uri.equals(UriDic.UPDUSER)) {
			UserController.getInstance().updUser(request,response);
		}
		else if (uri.equals(UriDic.UPDUSERPARENT)) {
			UserController.getInstance().updUserParent(request,response);
		}
		else if(uri.equals(UriDic.ADDGROUP)) {
			String id = GroupController.getInstance().addGroup(request,response);
			response.addHeader(EtcDic.RETID, id);
		}
		else if(uri.equals(UriDic.GETGROUP)) {
			Group group = GroupController.getInstance().getGroup(request,response);
			response.addHeader(EtcDic.GROUP, JsonUtil.toJson(group));
		}
		else if(uri.equals(UriDic.UPDGROUP)) {
			GroupController.getInstance().updGroup(request,response);
		}
		else if(uri.equals(UriDic.DELGROUP)) {
			GroupController.getInstance().delGroup(request,response);
		}
		else if(uri.equals(UriDic.GETGROUPLIST)) {
			List<Group> list = GroupController.getInstance().getGroupList(request,response);
			response.addHeader(EtcDic.GROUP, JsonUtil.toJson(list));
		}
		else if(uri.equals(UriDic.GETGROUPCHILDLIST)) {
			List<Group> list = GroupController.getInstance().getGroupChildList(request,response);
			response.addHeader(EtcDic.GROUP, JsonUtil.toJson(list));
		}
		else if(uri.equals(UriDic.CHECKUSER)) {
			//see absHandler
		}
	}

	@Override
	public User preCheck(HttpRequest request, HttpResponse response, String uri) throws Exception {
		User user = SecureController.getInstance().checkUser(request, response, uri);
		SecureController.getInstance().checkSystemPermission(request,response,user,uri);
		return user;
	}
}
