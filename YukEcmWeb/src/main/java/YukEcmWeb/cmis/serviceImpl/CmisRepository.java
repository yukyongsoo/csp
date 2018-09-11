package YukEcmWeb.cmis.serviceImpl;

import java.math.BigInteger;
import java.util.List;

import org.apache.chemistry.opencmis.commons.data.ExtensionsData;
import org.apache.chemistry.opencmis.commons.data.ObjectList;
import org.apache.chemistry.opencmis.commons.data.RepositoryInfo;
import org.apache.chemistry.opencmis.commons.spi.Holder;

public class CmisRepository {
	private static class LazyHolder {
	    private static final CmisRepository service = new CmisRepository();
	}

	public static CmisRepository getInstance(){
		return LazyHolder.service;
	}
	
	private CmisRepository() {}

	public List<RepositoryInfo> getRepositoryInfos(ExtensionsData extension) {
		// TODO Auto-generated method stub
		return null;
	}

	public RepositoryInfo getRepositoryInfo(String repositoryId, ExtensionsData extension) {
		// TODO Auto-generated method stub
		return null;
	}

	public ObjectList getContentChanges(String repositoryId, Holder<String> changeLogToken, Boolean includeProperties,
			String filter, Boolean includePolicyIds, Boolean includeAcl, BigInteger maxItems,
			ExtensionsData extension) {
		// TODO Auto-generated method stub
		return null;
	}
}
