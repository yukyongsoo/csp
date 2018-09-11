package ecmTest.SPITest;

import java.util.List;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import yukcommon.model.Acl;
import yukcommon.spi.AclProvider;

public class TestAclPrivoider implements AclProvider{

	@Override
	public Acl getAcl(HttpRequest request, HttpResponse response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Acl getAclByName(HttpRequest request, HttpResponse response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addAcl(HttpRequest request, HttpResponse response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delAcl(HttpRequest request, HttpResponse response) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updAcl(HttpRequest request, HttpResponse response) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Acl> getAclList(HttpRequest request, HttpResponse response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Acl getAcl(String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Acl getAclByName(String aclName) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
