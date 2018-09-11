package yukcommon.spi;

import java.util.List;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import yukcommon.model.Group;

public interface GroupProvider {

	String addGroup(HttpRequest request, HttpResponse response) throws Exception;

	Group getGroup(HttpRequest request, HttpResponse response) throws Exception;

	void delGroup(HttpRequest request, HttpResponse response) throws Exception;

	void updGroup(HttpRequest request, HttpResponse response) throws Exception;

	List<Group> getGroupList(HttpRequest request, HttpResponse response) throws Exception;

	List<Group> getGroupChildList(HttpRequest request, HttpResponse response) throws Exception;

}
