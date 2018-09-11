package yukecmapi;

import java.util.List;
import java.util.Map;

import yukcommon.dic.UriDic;
import yukcommon.dic.type.PermissionType;
import yukcommon.model.Ace;
import yukcommon.model.Acl;

public class AclAceApi extends AbsApi{

	public AclAceApi(EcmConnection conn) {
		super(conn);
	}

	public String addAcl(String aclName) throws Exception{
		AclAceApiImpl impl = EcmApiFactory.getAclAceApi(conn,UriDic.ADDACL);
		return impl.addAcl(aclName);
	}

	public void delAcl(String id) throws Exception{
		AclAceApiImpl impl = EcmApiFactory.getAclAceApi(conn,UriDic.DELACL);
		impl.delAcl(id);
	}

	public void updAcl(String aclId, String newName) throws Exception{
		AclAceApiImpl impl = EcmApiFactory.getAclAceApi(conn,UriDic.UPDACL);
		impl.updAcl(aclId, newName);
	}
	
	public List<Acl> getAcl() throws Exception{
		AclAceApiImpl impl = EcmApiFactory.getAclAceApi(conn,UriDic.GETACL);
		return impl.getAcl();
	}
	
	public void addAce(String aclId, String aceId, Map<PermissionType,Boolean> permissionMap) throws Exception{
		AclAceApiImpl impl = EcmApiFactory.getAclAceApi(conn,UriDic.ADDACE);
		impl.addAce(aclId,aceId, permissionMap);
	}
	
	public void delAce(String aclId, String aceId) throws Exception{
		AclAceApiImpl impl = EcmApiFactory.getAclAceApi(conn,UriDic.DELACE);
		impl.delAce(aclId,aceId);
	}
	
	public List<Ace> getAce(String aclId) throws Exception{
		AclAceApiImpl impl = EcmApiFactory.getAclAceApi(conn,UriDic.GETACE);
		return impl.getAce(aclId);
	}
	
	public void updAce(String aclId,String aceId,  Map<PermissionType,Boolean> permissionMap) throws Exception{
		AclAceApiImpl impl = EcmApiFactory.getAclAceApi(conn,UriDic.UPDACE);
		impl.updAce(aclId,aceId,permissionMap);
	}
}
