package yukcommon.spi;

import java.util.List;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import yukcommon.model.User;

public interface UserProvider {

	public String addUser(HttpRequest request, HttpResponse response) throws Exception;

	public void delUser(HttpRequest request, HttpResponse response) throws Exception;

	public void updUser(HttpRequest request, HttpResponse response) throws Exception;

	public List<User> getUserList(HttpRequest request, HttpResponse response) throws Exception;

	public User getUser(String id) throws Exception;

	public List<User> getGroupUser(HttpRequest request, HttpResponse response) throws Exception;

	public void updUserParent(HttpRequest request, HttpResponse response) throws Exception;
}