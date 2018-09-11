package yukcommon.spi;

import java.util.List;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import yukcommon.model.Acl;

public interface AclProvider {

	public Acl getAcl(HttpRequest request, HttpResponse response) throws Exception;

	public Acl getAclByName(HttpRequest request, HttpResponse response) throws Exception;

	public String addAcl(HttpRequest request, HttpResponse response) throws Exception;

	public void delAcl(HttpRequest request, HttpResponse response) throws Exception;

	public void updAcl(HttpRequest request, HttpResponse response) throws Exception;

	public List<Acl> getAclList(HttpRequest request, HttpResponse response) throws Exception;

	public Acl getAcl(String id)throws Exception;

	public Acl getAclByName(String aclName)throws Exception;
}