package ecmTest.SPITest;

import java.util.List;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import yukcommon.exception.NotSupportException;
import yukcommon.model.User;
import yukcommon.spi.UserProvider;

public class TestUserProvider implements UserProvider{

	@Override
	public String addUser(HttpRequest request, HttpResponse response) throws Exception {
		throw new NotSupportException("");
	}

	@Override
	public void delUser(HttpRequest request, HttpResponse response) throws Exception {
		throw new NotSupportException("");
	}

	@Override
	public void updUser(HttpRequest request, HttpResponse response) throws Exception {
		throw new NotSupportException("");
	}

	@Override
	public List<User> getUserList(HttpRequest request, HttpResponse response) throws Exception {
		throw new NotSupportException("");
	}

	@Override
	public List<User> getGroupUser(HttpRequest request, HttpResponse response) {
		throw new NotSupportException("");
	}

	@Override
	public User getUser(String id) throws Exception {
		throw new NotSupportException("");
	}

	@Override
	public void updUserParent(HttpRequest request, HttpResponse response) throws Exception {
		throw new NotSupportException("");
	}

	

}
