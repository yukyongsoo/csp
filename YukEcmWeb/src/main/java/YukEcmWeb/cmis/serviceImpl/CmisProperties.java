package YukEcmWeb.cmis.serviceImpl;

import java.util.List;

import org.apache.chemistry.opencmis.commons.data.BulkUpdateObjectIdAndChangeToken;
import org.apache.chemistry.opencmis.commons.data.ExtensionsData;
import org.apache.chemistry.opencmis.commons.data.Properties;
import org.apache.chemistry.opencmis.commons.spi.Holder;

public class CmisProperties {
	private static class LazyHolder {
	    private static final CmisProperties service = new CmisProperties();
	}

	public static CmisProperties getInstance(){
		return LazyHolder.service;
	}
	
	private CmisProperties() {}

	public Properties getProperties(String repositoryId, String objectId, String filter, ExtensionsData extension) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void updateProperties(String repositoryId, Holder<String> objectId, Holder<String> changeToken,
			Properties properties, ExtensionsData extension) {
		// TODO Auto-generated method stub
		
	}

	public List<BulkUpdateObjectIdAndChangeToken> bulkUpdateProperties(String repositoryId,
			List<BulkUpdateObjectIdAndChangeToken> objectIdsAndChangeTokens, Properties properties,
			List<String> addSecondaryTypeIds, List<String> removeSecondaryTypeIds, ExtensionsData extension) {
		// TODO Auto-generated method stub
		return null;
	}

	public Properties getPropertiesOfLatestVersion(String repositoryId, String objectId, String versionSeriesId,
			Boolean major, String filter, ExtensionsData extension) {
		// TODO Auto-generated method stub
		return null;
	}
}
