package yukecm.controller;

import java.util.List;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import yukcommon.model.Group;
import yukcommon.spi.GroupProvider;
import yukecm.config.BaseProperty;
import yukecm.etc.EcmUtil;

public class GroupController {
	
	private static class LazyHolder {
	    private static final GroupController group = new GroupController();
	}

	public static GroupController getInstance(){
		return LazyHolder.group;
	}

	private GroupProvider provider;
	
	private GroupController() {
		provider = (GroupProvider)EcmUtil.makeClass(BaseProperty.getInstance().groupProvider);
	}

	public String addGroup(HttpRequest request, HttpResponse response) throws Exception {
		return provider.addGroup(request,response);
	}

	public Group getGroup(HttpRequest request, HttpResponse response) throws Exception {
		return provider.getGroup(request,response);
	}

	public void delGroup(HttpRequest request, HttpResponse response) throws Exception {
		provider.delGroup(request,response);
	}

	public void updGroup(HttpRequest request, HttpResponse response) throws Exception {
		provider.updGroup(request,response);
	}

	public List<Group> getGroupList(HttpRequest request, HttpResponse response) throws Exception{
		return provider.getGroupList(request,response);
	}

	public List<Group> getGroupChildList(HttpRequest request, HttpResponse response) throws Exception {
		return provider.getGroupChildList(request,response);
	}
}
