package YukEcmWeb.cmis.serviceImpl;

import java.math.BigInteger;
import java.util.List;

import org.apache.chemistry.opencmis.commons.data.ExtensionsData;
import org.apache.chemistry.opencmis.commons.definitions.TypeDefinition;
import org.apache.chemistry.opencmis.commons.definitions.TypeDefinitionContainer;
import org.apache.chemistry.opencmis.commons.definitions.TypeDefinitionList;

public class CmisType {
	private static class LazyHolder {
	    private static final CmisType service = new CmisType();
	}

	public static CmisType getInstance(){
		return LazyHolder.service;
	}
	
	private CmisType() {}

	public TypeDefinitionList getTypeChildren(String repositoryId, String typeId, Boolean includePropertyDefinitions,
			BigInteger maxItems, BigInteger skipCount, ExtensionsData extension) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TypeDefinitionContainer> getTypeDescendants(String repositoryId, String typeId, BigInteger depth,
			Boolean includePropertyDefinitions, ExtensionsData extension) {
		// TODO Auto-generated method stub
		return null;
	}

	public TypeDefinition getTypeDefinition(String repositoryId, String typeId, ExtensionsData extension) {
		// TODO Auto-generated method stub
		return null;
	}

	public TypeDefinition createType(String repositoryId, TypeDefinition type, ExtensionsData extension) {
		// TODO Auto-generated method stub
		return null;
	}

	public TypeDefinition updateType(String repositoryId, TypeDefinition type, ExtensionsData extension) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteType(String repositoryId, String typeId, ExtensionsData extension) {
		// TODO Auto-generated method stub
		
	}
}
