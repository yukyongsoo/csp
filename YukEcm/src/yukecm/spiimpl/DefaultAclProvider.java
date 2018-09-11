package yukecm.spiimpl;

import java.util.List;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import yukcommon.dic.EtcDic;
import yukcommon.dic.type.OnErrorType;
import yukcommon.model.Acl;
import yukcommon.spi.AclProvider;
import yukcommon.util.JsonUtil;
import yukecm.etc.EcmUtil;
import yukecm.injecter.acl.AclInjector;

public class DefaultAclProvider implements AclProvider{
	@Override
	public Acl getAcl(HttpRequest request, HttpResponse response) throws Exception {
		String json = request.getFirstHeader(EtcDic.ACL).getValue();
		Acl acl = JsonUtil.fromJson(json, Acl.class);
		return AclInjector.getInstance().getAcl(acl.getId(),OnErrorType.NONE);
	}

	@Override
	public Acl getAclByName(HttpRequest request, HttpResponse response) throws Exception {
		String json = request.getFirstHeader(EtcDic.ACL).getValue();
		Acl acl = JsonUtil.fromJson(json, Acl.class);
		return AclInjector.getInstance().getAclByName(acl.getName(),OnErrorType.NONE);
	}

	@Override
	public String addAcl(HttpRequest request, HttpResponse response) throws Exception {
		String json = request.getFirstHeader(EtcDic.ACL).getValue();
		Acl acl = JsonUtil.fromJson(json, Acl.class);
		AclInjector.getInstance().getAclByName(acl.getName(), OnErrorType.EXIST);
		acl.setId(EcmUtil.getId());
		AclInjector.getInstance().addAcl(acl);
		return acl.getId();
	}

	@Override
	public void delAcl(HttpRequest request, HttpResponse response) throws Exception {
		String json = request.getFirstHeader(EtcDic.ACL).getValue();
		Acl acl = JsonUtil.fromJson(json, Acl.class);
		Acl nAcl = AclInjector.getInstance().getAcl(acl.getId(),OnErrorType.NOTEXIST);
		AclInjector.getInstance().delAcl(nAcl);
	}

	@Override
	public void updAcl(HttpRequest request, HttpResponse response) throws Exception {
		String json = request.getFirstHeader(EtcDic.ACL).getValue();
		Acl acl = JsonUtil.fromJson(json, Acl.class);
		Acl nAcl = AclInjector.getInstance().getAcl(acl.getId(),OnErrorType.NOTEXIST);
		AclInjector.getInstance().updAcl(nAcl);
	}

	@Override
	public List<Acl> getAclList(HttpRequest request, HttpResponse response) throws Exception {
		return AclInjector.getInstance().getAclList();
	}

	@Override
	public Acl getAcl(String id) throws Exception {
		return AclInjector.getInstance().getAcl(id,OnErrorType.NONE);
	}

	@Override
	public Acl getAclByName(String aclName) throws Exception {
		return AclInjector.getInstance().getAclByName(aclName,OnErrorType.NONE);
	}
}
