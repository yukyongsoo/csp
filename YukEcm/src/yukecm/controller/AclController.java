package yukecm.controller;

import java.util.List;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import yukcommon.exception.AdpatorException;
import yukcommon.model.Acl;
import yukcommon.spi.AclProvider;
import yukecm.config.BaseProperty;
import yukecm.etc.EcmUtil;

public class AclController {
	private static class LazyHolder {
	    private static final AclController acl; 
	    static {
	    	try {
				acl = new AclController();
			} catch (AdpatorException e) {
				throw new ExceptionInInitializerError(e);
			}
	    }
	}

	public static AclController getInstance(){
		return LazyHolder.acl;
	}

	private AclProvider provider;

	private AclController() {
		provider = (AclProvider) EcmUtil.makeClass(BaseProperty.getInstance().aclProvider);
	}

	public String addAcl(HttpRequest request, HttpResponse response) throws Exception {
		return provider.addAcl(request, response);
	}

	public List<Acl> getAclList(HttpRequest request, HttpResponse response) throws Exception {
		return provider.getAclList(request, response);
	}

	public void delAcl(HttpRequest request, HttpResponse response) throws Exception {
		provider.delAcl(request, response);
	}

	public void updAcl(HttpRequest request, HttpResponse response) throws Exception {
		provider.updAcl(request, response);
	}

	public Acl getAcl(HttpRequest request, HttpResponse response) throws Exception {
		return provider.getAcl(request, response);
	}

	public Acl getAcl(String id) throws Exception {
		return provider.getAcl(id);
	}

	public Acl getAclByName(String aclName) throws Exception {
		return provider.getAclByName(aclName);
	}
}
