package YukEcmWeb.cmis.serviceImpl;

import java.math.BigInteger;
import java.util.List;

import org.apache.chemistry.opencmis.commons.data.Acl;
import org.apache.chemistry.opencmis.commons.data.ExtensionsData;
import org.apache.chemistry.opencmis.commons.data.ObjectList;
import org.apache.chemistry.opencmis.commons.data.Properties;
import org.apache.chemistry.opencmis.commons.enums.RelationshipDirection;
import org.springframework.test.web.servlet.result.FlashAttributeResultMatchers;

public class CmisRealation {
	private static class LazyHolder {
	    private static final CmisRealation service = new CmisRealation();
	}

	public static CmisRealation getInstance(){
		return LazyHolder.service;
	}
	
	private CmisRealation() {}

	public String createRelationship(String repositoryId, Properties properties, List<String> policies, Acl addAces,
			Acl removeAces, ExtensionsData extension) {
		// TODO Auto-generated method stub
		return null;
	}

	public ObjectList getObjectRelationships(String repositoryId, String objectId, Boolean includeSubRelationshipTypes,
			RelationshipDirection relationshipDirection, String typeId, FlashAttributeResultMatchers flash,
			Boolean includeAllowableActions, BigInteger maxItems, BigInteger skipCount, ExtensionsData extension) {
		// TODO Auto-generated method stub
		return null;
	}
}
