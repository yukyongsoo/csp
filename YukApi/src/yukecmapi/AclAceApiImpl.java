package yukecmapi;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import yukcommon.dic.EtcDic;
import yukcommon.dic.type.PermissionType;
import yukcommon.model.Ace;
import yukcommon.model.Acl;
import yukcommon.net.NetClient;
import yukcommon.net.NetWrapper;
import yukcommon.util.JsonUtil;

public class AclAceApiImpl extends AbsApiImpl{

	protected AclAceApiImpl(NetClient client, NetWrapper wrap) {
		super(client, wrap);
	}

	public String addAcl(String aclName) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		Acl acl = new Acl();
		acl.setName(aclName);
		wrap.addHeader(EtcDic.ACL, JsonUtil.toJson(acl));
		client.excute(wrap);
		return wrap.getHeaderValue(EtcDic.RETID);
	}

	public void delAcl(String id) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		Acl acl = new Acl();
		acl.setId(id);
		wrap.addHeader(EtcDic.ACL, JsonUtil.toJson(acl));
		client.excute(wrap);
	}

	public void updAcl(String aclId, String newName) throws URISyntaxException, IOException, InterruptedException, ExecutionException{
		Acl acl = new Acl();
		acl.setId(aclId);
		acl.setName(newName);
		wrap.addHeader(EtcDic.ACL, JsonUtil.toJson(acl));
		client.excute(wrap);
	}

	public List<Acl> getAcl() throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		client.excute(wrap);
		String json = wrap.getHeaderValue(EtcDic.ACL);
		return JsonUtil.fromJson(json, JsonUtil.LISTACL);
	}

	public void addAce(String aclId, String aceId,  Map<PermissionType,Boolean> permissionMap) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		Ace ace = new Ace();
		ace.setId(aclId);
		ace.setChildId(aceId);
		ace.setPermission(permissionMap);
		wrap.addHeader(EtcDic.ACE, JsonUtil.toJson(ace));
		client.excute(wrap);
	}

	public void delAce(String aclId, String aceId) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		Ace ace = new Ace();
		ace.setId(aclId);
		ace.setChildId(aceId);
		wrap.addHeader(EtcDic.ACE, JsonUtil.toJson(ace));
		client.excute(wrap);
	}

	public List<Ace> getAce(String aclId) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		Ace ace = new Ace();
		ace.setId(aclId);
		wrap.addHeader(EtcDic.ACE, JsonUtil.toJson(ace));
		client.excute(wrap);
		String json = wrap.getHeaderValue(EtcDic.ACE);
		return JsonUtil.fromJson(json, JsonUtil.LISTACE);
	}

	public void updAce(String aclId, String aceId,  Map<PermissionType,Boolean> permissionMap) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		Ace ace = new Ace();
		ace.setId(aclId);
		ace.setChildId(aceId);
		ace.setPermission(permissionMap);
		wrap.addHeader(EtcDic.ACE, JsonUtil.toJson(ace));
		client.excute(wrap);
	}
}
