package yukecm.spiimpl;

import java.util.List;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import yukcommon.dic.EtcDic;
import yukcommon.dic.type.OnErrorType;
import yukcommon.model.User;
import yukcommon.spi.UserProvider;
import yukcommon.util.JsonUtil;
import yukecm.injecter.user.UserInjector;

public class DefaultUserProvider implements UserProvider{
	@Override
	public String addUser(HttpRequest request, HttpResponse response) throws Exception {
		String json = request.getFirstHeader(EtcDic.USER).getValue();
		User user = JsonUtil.fromJson(json, User.class);
		UserInjector.getInstance().getUser(user.getId(),OnErrorType.EXIST);
		UserInjector.getInstance().addUser(user);
		return user.getId();
	}

	@Override
	public void delUser(HttpRequest request, HttpResponse response) throws Exception {
		String json = request.getFirstHeader(EtcDic.USER).getValue();
		User user = JsonUtil.fromJson(json, User.class);
		UserInjector.getInstance().getUser(user.getId(),OnErrorType.NOTEXIST);
		UserInjector.getInstance().delUser(user);
	}

	@Override
	public void updUser(HttpRequest request, HttpResponse response) throws Exception {
		String json = request.getFirstHeader(EtcDic.USER).getValue();
		User user = JsonUtil.fromJson(json, User.class);
		UserInjector.getInstance().getUser(user.getId(),OnErrorType.NOTEXIST);
		UserInjector.getInstance().updUser(user);
	}
	
	@Override
	public void updUserParent(HttpRequest request, HttpResponse response) throws Exception {
		String json = request.getFirstHeader(EtcDic.USER).getValue();
		User user = JsonUtil.fromJson(json, User.class);
		User old = UserInjector.getInstance().getUser(user.getId(),OnErrorType.NOTEXIST);
		old.setParentId(user.getParentId());
		UserInjector.getInstance().updUser(old);	
	}

	@Override
	public List<User> getUserList(HttpRequest request, HttpResponse response) throws Exception {
		return UserInjector.getInstance().getUserList(OnErrorType.NONE);
	}

	@Override
	public User getUser(String id) throws Exception {
		return UserInjector.getInstance().getUser(id,OnErrorType.NONE);
	}

	@Override
	public List<User> getGroupUser(HttpRequest request, HttpResponse response) throws Exception{
		String json = request.getFirstHeader(EtcDic.USER).getValue();
		User user = JsonUtil.fromJson(json, User.class);
		return UserInjector.getInstance().getGroupUser(user.getParentId(),OnErrorType.NONE);
	}


}
