package YukEcmWeb.cmis.serviceImpl;

import java.util.List;

import org.apache.chemistry.opencmis.commons.data.Acl;
import org.apache.chemistry.opencmis.commons.data.AllowableActions;
import org.apache.chemistry.opencmis.commons.data.ExtensionsData;
import org.apache.chemistry.opencmis.commons.data.ObjectData;
import org.apache.chemistry.opencmis.commons.data.Properties;

public class CmisPolicy {
	private static class LazyHolder {
	    private static final CmisPolicy service = new CmisPolicy();
	}

	public static CmisPolicy getInstance(){
		return LazyHolder.service;
	}
	
	private CmisPolicy() {}

	public String createPolicy(String repositoryId, Properties properties, String folderId, List<String> policies,
			Acl addAces, Acl removeAces, ExtensionsData extension) {
		// TODO Auto-generated method stub
		return null;
	}

	public AllowableActions getAllowableActions(String repositoryId, String objectId, ExtensionsData extension) {
		// TODO Auto-generated method stub
		return null;
	}

	public void applyPolicy(String repositoryId, String policyId, String objectId, ExtensionsData extension) {
		// TODO Auto-generated method stub
		
	}

	public void removePolicy(String repositoryId, String policyId, String objectId, ExtensionsData extension) {
		// TODO Auto-generated method stub
		
	}

	public List<ObjectData> getAppliedPolicies(String repositoryId, String objectId, String filter,
			ExtensionsData extension) {
		// TODO Auto-generated method stub
		return null;
	}
}
