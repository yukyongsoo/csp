package YukEcmWeb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import YukEcmWeb.SessionBean;
import yukcommon.dic.EtcDic;
import yukcommon.dic.UriDic;
import yukcommon.model.Ace;
import yukcommon.model.Acl;
import yukcommon.util.JsonUtil;
import yukecmapi.AclAceApi;

@RestController
public class WebAclAceController {
	@Autowired(required = true)
    private SessionBean session;

	@RequestMapping(UriDic.GETACL)
	public List<Acl> getAcl() throws Exception {
		AclAceApi api = new AclAceApi(session.getConn());
		return api.getAcl();
	}
	
	@RequestMapping(UriDic.ADDACL)
	public String addAcl(@RequestHeader(EtcDic.ACL) String json) throws Exception {
		Acl acl = JsonUtil.fromJson(json, Acl.class);
		AclAceApi api = new AclAceApi(session.getConn());
		return api.addAcl(acl.getName());
	}
	
	@RequestMapping(UriDic.DELACL)
	public void delAcl(@RequestHeader(EtcDic.ACL) String json) throws Exception {
		Acl acl = JsonUtil.fromJson(json, Acl.class);
		AclAceApi api = new AclAceApi(session.getConn());
		api.delAcl(acl.getId());
	}
	
	@RequestMapping(UriDic.UPDACL)
	public void updAcl(@RequestHeader(EtcDic.ACL) String json) throws Exception {
		Acl acl = JsonUtil.fromJson(json, Acl.class);
		AclAceApi api = new AclAceApi(session.getConn());
		api.updAcl(acl.getId(), acl.getName());
	}
	
	@RequestMapping(UriDic.ADDACE)
	public void addAce(@RequestHeader(EtcDic.ACE) String json) throws Exception {
		Ace ace = JsonUtil.fromJson(json, Ace.class);
		AclAceApi api = new AclAceApi(session.getConn());
		api.addAce(ace.getId(), ace.getChildId(), ace.getPermissionSet());
	}
	
	@RequestMapping(UriDic.DELACE)
	public void delAce(@RequestHeader(EtcDic.ACE) String json) throws Exception {
		Ace ace = JsonUtil.fromJson(json, Ace.class);
		AclAceApi api = new AclAceApi(session.getConn());
		api.delAce(ace.getId(),ace.getChildId());
	}
	
	@RequestMapping(UriDic.GETACE)
	public List<Ace> getAce(@RequestHeader(EtcDic.ACE) String json) throws Exception {
		Ace ace = JsonUtil.fromJson(json, Ace.class);
		AclAceApi api = new AclAceApi(session.getConn());
		return api.getAce(ace.getId());
	}
	
	@RequestMapping(UriDic.UPDACE)
	public void updAce(@RequestHeader(EtcDic.ACE) String json) throws Exception {
		Ace ace = JsonUtil.fromJson(json, Ace.class);
		AclAceApi api = new AclAceApi(session.getConn());
		api.updAce(ace.getId(), ace.getChildId(), ace.getPermissionSet());
	}
}
