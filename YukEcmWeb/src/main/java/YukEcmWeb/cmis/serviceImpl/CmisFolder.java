package YukEcmWeb.cmis.serviceImpl;

import java.math.BigInteger;
import java.util.List;

import org.apache.chemistry.opencmis.commons.data.Acl;
import org.apache.chemistry.opencmis.commons.data.ExtensionsData;
import org.apache.chemistry.opencmis.commons.data.FailedToDeleteData;
import org.apache.chemistry.opencmis.commons.data.ObjectData;
import org.apache.chemistry.opencmis.commons.data.ObjectInFolderContainer;
import org.apache.chemistry.opencmis.commons.data.Properties;
import org.apache.chemistry.opencmis.commons.enums.IncludeRelationships;
import org.apache.chemistry.opencmis.commons.enums.UnfileObject;

public class CmisFolder {
	private static class LazyHolder {
	    private static final CmisFolder service = new CmisFolder();
	}

	public static CmisFolder getInstance(){
		return LazyHolder.service;
	}
	
	private CmisFolder() {}

	public List<ObjectInFolderContainer> getFolderTree(String repositoryId, String folderId, BigInteger depth,
			String filter, Boolean includeAllowableActions, IncludeRelationships includeRelationships,
			String renditionFilter, Boolean includePathSegment, ExtensionsData extension) {
		// TODO Auto-generated method stub
		return null;
	}

	public ObjectData getFolderParent(String repositoryId, String folderId, String filter, ExtensionsData extension) {
		// TODO Auto-generated method stub
		return null;
	}

	public String createFolder(String repositoryId, Properties properties, String folderId, List<String> policies,
			Acl addAces, Acl removeAces, ExtensionsData extension) {
		// TODO Auto-generated method stub
		return null;
	}

	public FailedToDeleteData deleteTree(String repositoryId, String folderId, Boolean allVersions,
			UnfileObject unfileObjects, Boolean continueOnFailure, ExtensionsData extension) {
		// TODO Auto-generated method stub
		return null;
	}
}
