package ecmTest.SPITest;

import java.util.List;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import yukcommon.model.Group;
import yukcommon.spi.GroupProvider;

public class TestGroupProvider implements GroupProvider{

	@Override
	public String addGroup(HttpRequest request, HttpResponse response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Group getGroup(HttpRequest request, HttpResponse response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delGroup(HttpRequest request, HttpResponse response) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updGroup(HttpRequest request, HttpResponse response) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Group> getGroupList(HttpRequest request, HttpResponse response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Group> getGroupChildList(HttpRequest request, HttpResponse response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
