package YukEcmWeb.cmis.serviceImpl;

import org.apache.chemistry.opencmis.commons.data.Acl;
import org.apache.chemistry.opencmis.commons.data.ExtensionsData;
import org.apache.chemistry.opencmis.commons.enums.AclPropagation;

public class CmisAcl {
	private static class LazyHolder {
	    private static final CmisAcl service = new CmisAcl();
	}

	public static CmisAcl getInstance(){
		return LazyHolder.service;
	}
	
	private CmisAcl() {}

	public Acl getAcl(String repositoryId, String objectId, Boolean onlyBasicPermissions, ExtensionsData extension) {
		// TODO Auto-generated method stub
		return null;
	}

	public Acl applyAcl(String repositoryId, String objectId, Acl addAces, Acl removeAces,
			AclPropagation aclPropagation, ExtensionsData extension) {
		// TODO Auto-generated method stub
		return null;
	}

	public Acl applyAcl(String repositoryId, String objectId, Acl aces, AclPropagation aclPropagation) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
