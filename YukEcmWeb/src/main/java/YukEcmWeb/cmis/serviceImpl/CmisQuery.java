package YukEcmWeb.cmis.serviceImpl;

import java.math.BigInteger;

import org.apache.chemistry.opencmis.commons.data.ExtensionsData;
import org.apache.chemistry.opencmis.commons.data.ObjectList;
import org.apache.chemistry.opencmis.commons.enums.IncludeRelationships;

public class CmisQuery {
	private static class LazyHolder {
	    private static final CmisQuery service = new CmisQuery();
	}

	public static CmisQuery getInstance(){
		return LazyHolder.service;
	}
	
	private CmisQuery() {}

	public ObjectList query(String renditionFilter, String statement, Boolean searchAllVersions,
			Boolean includeAllowableActions, IncludeRelationships includeRelationships, String renditionFilter2,
			BigInteger maxItems, BigInteger skipCount, ExtensionsData extension) {
		// TODO Auto-generated method stub
		return null;
	}
}
