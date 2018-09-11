package YukEcmWeb.cmis.serviceImpl;

import java.math.BigInteger;
import java.util.List;

import org.apache.chemistry.opencmis.commons.data.ExtensionsData;
import org.apache.chemistry.opencmis.commons.data.RenditionData;

public class CmisRendition {
	private static class LazyHolder {
	    private static final CmisRendition service = new CmisRendition();
	}

	public static CmisRendition getInstance(){
		return LazyHolder.service;
	}
	
	private CmisRendition() {}

	public List<RenditionData> getRenditions(String repositoryId, String objectId, String renditionFilter,
			BigInteger maxItems, BigInteger skipCount, ExtensionsData extension) {
		// TODO Auto-generated method stub
		return null;
	}
}
