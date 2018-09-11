package yukecm.controller;

import java.util.List;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import yukcommon.exception.AdpatorException;
import yukcommon.model.User;
import yukcommon.spi.UserProvider;
import yukecm.config.BaseProperty;
import yukecm.etc.EcmUtil;

public class UserController  {
	private static class LazyHolder {
	    private static final UserController userController;
	    static{
	    	try {
				userController = new UserController();
			} catch (AdpatorException e) {
				throw new ExceptionInInitializerError(e);
			}
	    }
	}

	public static UserController getInstance() {
		return LazyHolder.userController;
	}

	UserProvider provider;

	private UserController() {
		provider = (UserProvider)EcmUtil.makeClass(BaseProperty.getInstance().userProvider);
	}

	public String addUser(HttpRequest request, HttpResponse response) throws Exception {
		return provider.addUser(request, response);
	}

	public List<User> getUserList(HttpRequest request, HttpResponse response) throws Exception {
		return provider.getUserList(request, response);
	}

	public void delUser(HttpRequest request, HttpResponse response) throws Exception {
		provider.delUser(request, response);
	}

	public void updUser(HttpRequest request, HttpResponse response) throws Exception {
		provider.updUser(request, response);
	}
	
	public void updUserParent(HttpRequest request, HttpResponse response) throws Exception {
		provider.updUserParent(request,response);
	}

	public User getUser(String id) throws Exception {
		return provider.getUser(id);
	}
	
	public List<User> getGroupUser(HttpRequest request, HttpResponse response) throws Exception{
		return provider.getGroupUser(request,response);
	}

	
}
