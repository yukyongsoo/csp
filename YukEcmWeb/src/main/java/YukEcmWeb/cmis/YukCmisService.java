package YukEcmWeb.cmis;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;

import java.math.BigInteger;
import java.util.List;

import org.apache.chemistry.opencmis.commons.data.Acl;
import org.apache.chemistry.opencmis.commons.data.AllowableActions;
import org.apache.chemistry.opencmis.commons.data.BulkUpdateObjectIdAndChangeToken;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.data.ExtensionsData;
import org.apache.chemistry.opencmis.commons.data.FailedToDeleteData;
import org.apache.chemistry.opencmis.commons.data.ObjectData;
import org.apache.chemistry.opencmis.commons.data.ObjectInFolderContainer;
import org.apache.chemistry.opencmis.commons.data.ObjectInFolderList;
import org.apache.chemistry.opencmis.commons.data.ObjectList;
import org.apache.chemistry.opencmis.commons.data.ObjectParentData;
import org.apache.chemistry.opencmis.commons.data.Properties;
import org.apache.chemistry.opencmis.commons.data.RenditionData;
import org.apache.chemistry.opencmis.commons.data.RepositoryInfo;
import org.apache.chemistry.opencmis.commons.definitions.TypeDefinition;
import org.apache.chemistry.opencmis.commons.definitions.TypeDefinitionContainer;
import org.apache.chemistry.opencmis.commons.definitions.TypeDefinitionList;
import org.apache.chemistry.opencmis.commons.enums.AclPropagation;
import org.apache.chemistry.opencmis.commons.enums.IncludeRelationships;
import org.apache.chemistry.opencmis.commons.enums.RelationshipDirection;
import org.apache.chemistry.opencmis.commons.enums.UnfileObject;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.server.CallContext;
import org.apache.chemistry.opencmis.commons.server.CmisService;
import org.apache.chemistry.opencmis.commons.server.ObjectInfo;
import org.apache.chemistry.opencmis.commons.spi.Holder;

import YukEcmWeb.cmis.serviceImpl.CmisAcl;
import YukEcmWeb.cmis.serviceImpl.CmisDocument;
import YukEcmWeb.cmis.serviceImpl.CmisFolder;
import YukEcmWeb.cmis.serviceImpl.CmisObject;
import YukEcmWeb.cmis.serviceImpl.CmisPolicy;
import YukEcmWeb.cmis.serviceImpl.CmisProperties;
import YukEcmWeb.cmis.serviceImpl.CmisQuery;
import YukEcmWeb.cmis.serviceImpl.CmisRealation;
import YukEcmWeb.cmis.serviceImpl.CmisRendition;
import YukEcmWeb.cmis.serviceImpl.CmisRepository;
import YukEcmWeb.cmis.serviceImpl.CmisType;


public class YukCmisService implements CmisService{	
	CallContext context;
		
	public void setCallContext(CallContext context) {
		//TODO check auth
		this.context = context;
	}

	public CallContext getCallContext() {
		return context;
	}

	@Override
	public List<RepositoryInfo> getRepositoryInfos(ExtensionsData extension) {
		return CmisRepository.getInstance().getRepositoryInfos(extension);
	}

	@Override
	public RepositoryInfo getRepositoryInfo(String repositoryId, ExtensionsData extension) {
		return CmisRepository.getInstance().getRepositoryInfo(repositoryId,extension);
	}

	@Override
	public TypeDefinitionList getTypeChildren(String repositoryId, String typeId, Boolean includePropertyDefinitions,
			BigInteger maxItems, BigInteger skipCount, ExtensionsData extension) {
		return CmisType.getInstance().getTypeChildren(repositoryId,typeId,includePropertyDefinitions,maxItems,skipCount,extension);
	}

	@Override
	public List<TypeDefinitionContainer> getTypeDescendants(String repositoryId, String typeId, BigInteger depth,
			Boolean includePropertyDefinitions, ExtensionsData extension) {
		return CmisType.getInstance().getTypeDescendants(repositoryId,typeId,depth,includePropertyDefinitions,extension);
	}

	@Override
	public TypeDefinition getTypeDefinition(String repositoryId, String typeId, ExtensionsData extension) {
		return CmisType.getInstance().getTypeDefinition(repositoryId,typeId,extension);
	}

	@Override
	public TypeDefinition createType(String repositoryId, TypeDefinition type, ExtensionsData extension) {
		return CmisType.getInstance().createType(repositoryId,type,extension);
	}

	@Override
	public TypeDefinition updateType(String repositoryId, TypeDefinition type, ExtensionsData extension) {
		return CmisType.getInstance().updateType(repositoryId,type,extension);
	}

	@Override
	public void deleteType(String repositoryId, String typeId, ExtensionsData extension) {
		CmisType.getInstance().deleteType(repositoryId,typeId,extension);
	}

	@Override
	public ObjectInFolderList getChildren(String repositoryId, String folderId, String filter, String orderBy,
			Boolean includeAllowableActions, IncludeRelationships includeRelationships, String renditionFilter,
			Boolean includePathSegment, BigInteger maxItems, BigInteger skipCount, ExtensionsData extension) {
		return CmisObject.getInstance().getChildren(repositoryId,folderId,filter,orderBy,
				includeAllowableActions,includeRelationships,renditionFilter,includePathSegment,
				maxItems,skipCount,extension);
	}

	@Override
	public List<ObjectInFolderContainer> getDescendants(String repositoryId, String folderId, BigInteger depth,
			String filter, Boolean includeAllowableActions, IncludeRelationships includeRelationships,
			String renditionFilter, Boolean includePathSegment, ExtensionsData extension) {
		return CmisObject.getInstance().getDescendants(repositoryId,folderId,depth,filter,includeAllowableActions,
				includeRelationships,renditionFilter,includePathSegment,extension);
	}

	@Override
	public List<ObjectInFolderContainer> getFolderTree(String repositoryId, String folderId, BigInteger depth,
			String filter, Boolean includeAllowableActions, IncludeRelationships includeRelationships,
			String renditionFilter, Boolean includePathSegment, ExtensionsData extension) {
		return CmisFolder.getInstance().getFolderTree(repositoryId,folderId,depth,filter,includeAllowableActions
				,includeRelationships,renditionFilter,includePathSegment,extension);
	}

	@Override
	public List<ObjectParentData> getObjectParents(String repositoryId, String objectId, String filter,
			Boolean includeAllowableActions, IncludeRelationships includeRelationships, String renditionFilter,
			Boolean includeRelativePathSegment, ExtensionsData extension) {
		return CmisObject.getInstance().getObjectParents(repositoryId,objectId,filter,includeAllowableActions,
				includeRelationships,renditionFilter,includeRelativePathSegment,extension);
	}

	@Override
	public ObjectData getFolderParent(String repositoryId, String folderId, String filter, ExtensionsData extension) {
		return CmisFolder.getInstance().getFolderParent(repositoryId,folderId,filter,extension);
	}

	@Override
	public ObjectList getCheckedOutDocs(String repositoryId, String folderId, String filter, String orderBy,
			Boolean includeAllowableActions, IncludeRelationships includeRelationships, String renditionFilter,
			BigInteger maxItems, BigInteger skipCount, ExtensionsData extension) {
		return CmisDocument.getInstance().getCheckedOutDocs(repositoryId,folderId,filter,orderBy,
				includeAllowableActions,includeRelationships,renditionFilter,maxItems,skipCount,
				extension);
	}

	@Override
	public String createDocument(String repositoryId, Properties properties, String folderId,
			ContentStream contentStream, VersioningState versioningState, List<String> policies, Acl addAces,
			Acl removeAces, ExtensionsData extension) {
		return CmisDocument.getInstance().createDocument(repositoryId,properties,folderId,
				contentStream,versioningState,policies,addAces,removeAces,extension);
	}

	@Override
	public String createDocumentFromSource(String repositoryId, String sourceId, Properties properties, String folderId,
			VersioningState versioningState, List<String> policies, Acl addAces, Acl removeAces,
			ExtensionsData extension) {
		return CmisDocument.getInstance().createDocumentFromSource(repositoryId,sourceId,properties,folderId,
				versioningState,policies,addAces,removeAces,extension);
	}

	@Override
	public String createFolder(String repositoryId, Properties properties, String folderId, List<String> policies,
			Acl addAces, Acl removeAces, ExtensionsData extension) {
		return CmisFolder.getInstance().createFolder(repositoryId,properties,folderId,policies,
				addAces,removeAces,extension);
	}

	@Override
	public String createRelationship(String repositoryId, Properties properties, List<String> policies, Acl addAces,
			Acl removeAces, ExtensionsData extension) {
		return CmisRealation.getInstance().createRelationship(repositoryId,properties,policies,addAces,
				removeAces,extension);
	}

	@Override
	public String createPolicy(String repositoryId, Properties properties, String folderId, List<String> policies,
			Acl addAces, Acl removeAces, ExtensionsData extension) {
		return CmisPolicy.getInstance().createPolicy(repositoryId,properties,folderId,policies,addAces,removeAces,
				extension);
	}

	@Override
	public String createItem(String repositoryId, Properties properties, String folderId, List<String> policies,
			Acl addAces, Acl removeAces, ExtensionsData extension) {
		return CmisObject.getInstance().createItem(repositoryId,properties,folderId,policies,
				addAces,removeAces,extension);
	}

	@Override
	public AllowableActions getAllowableActions(String repositoryId, String objectId, ExtensionsData extension) {
		return CmisPolicy.getInstance().getAllowableActions(repositoryId,objectId,extension);
	}

	@Override
	public ObjectData getObject(String repositoryId, String objectId, String filter, Boolean includeAllowableActions,
			IncludeRelationships includeRelationships, String renditionFilter, Boolean includePolicyIds,
			Boolean includeAcl, ExtensionsData extension) {
		return CmisObject.getInstance().getObject(repositoryId);
	}

	@Override
	public Properties getProperties(String repositoryId, String objectId, String filter, ExtensionsData extension) {
		return CmisProperties.getInstance().getProperties(repositoryId,objectId,filter,extension);
	}

	@Override
	public List<RenditionData> getRenditions(String repositoryId, String objectId, String renditionFilter,
			BigInteger maxItems, BigInteger skipCount, ExtensionsData extension) {
		return CmisRendition.getInstance().getRenditions(repositoryId,objectId,renditionFilter
				,maxItems,skipCount,extension);
	}

	@Override
	public ObjectData getObjectByPath(String repositoryId, String path, String filter, Boolean includeAllowableActions,
			IncludeRelationships includeRelationships, String renditionFilter, Boolean includePolicyIds,
			Boolean includeAcl, ExtensionsData extension) {
		return CmisObject.getInstance().getObjectByPath(repositoryId,path,filter,includeAllowableActions,
				includeRelationships,renditionFilter,includePolicyIds,includeAcl,extension);
	}

	@Override
	public ContentStream getContentStream(String repositoryId, String objectId, String streamId, BigInteger offset,
			BigInteger length, ExtensionsData extension) {
		return CmisDocument.getInstance().getContentStream(repositoryId,objectId,streamId,offset,length,extension);
	}

	@Override
	public void updateProperties(String repositoryId, Holder<String> objectId, Holder<String> changeToken,
			Properties properties, ExtensionsData extension) {
		CmisProperties.updateProperties(repositoryId,objectId,changeToken,properties,extension);
	}

	@Override
	public List<BulkUpdateObjectIdAndChangeToken> bulkUpdateProperties(String repositoryId,
			List<BulkUpdateObjectIdAndChangeToken> objectIdsAndChangeTokens, Properties properties,
			List<String> addSecondaryTypeIds, List<String> removeSecondaryTypeIds, ExtensionsData extension) {
		return CmisProperties.getInstance().bulkUpdateProperties(repositoryId,objectIdsAndChangeTokens,properties,
				addSecondaryTypeIds,removeSecondaryTypeIds,extension);
	}

	@Override
	public void moveObject(String repositoryId, Holder<String> objectId, String targetFolderId, String sourceFolderId,
			ExtensionsData extension) {
		CmisObject.getInstance().moveObject(repositoryId,objectId,targetFolderId,sourceFolderId,extension);
	}

	@Override
	public void deleteObject(String repositoryId, String objectId, Boolean allVersions, ExtensionsData extension) {
		CmisObject.getInstance().deleteObject(repositoryId,objectId,allVersions,extension);
	}

	@Override
	public FailedToDeleteData deleteTree(String repositoryId, String folderId, Boolean allVersions,
			UnfileObject unfileObjects, Boolean continueOnFailure, ExtensionsData extension) {
		return CmisFolder.getInstance().deleteTree(repositoryId,folderId,allVersions,
				unfileObjects,continueOnFailure,extension);
	}

	@Override
	public void setContentStream(String repositoryId, Holder<String> objectId, Boolean overwriteFlag,
			Holder<String> changeToken, ContentStream contentStream, ExtensionsData extension) {
		CmisDocument.getInstance().setContentStream(repositoryId,objectId,overwriteFlag,
				changeToken,contentStream,extension);
	}

	@Override
	public void deleteContentStream(String repositoryId, Holder<String> objectId, Holder<String> changeToken,
			ExtensionsData extension) {
		CmisDocument.getInstance().deleteContentStream(repositoryId,objectId,changeToken,extension);
	}

	@Override
	public void appendContentStream(String repositoryId, Holder<String> objectId, Holder<String> changeToken,
			ContentStream contentStream, boolean isLastChunk, ExtensionsData extension) {
		CmisDocument.getInstance().appendContentStream(repositoryId,objectId,changeToken,contentStream,
				isLastChunk,extension);
	}

	@Override
	public void checkOut(String repositoryId, Holder<String> objectId, ExtensionsData extension,
			Holder<Boolean> contentCopied) {
		CmisDocument.getInstance().checkOut(repositoryId,objectId,extension,contentCopied);
	}

	@Override
	public void cancelCheckOut(String repositoryId, String objectId, ExtensionsData extension) {
		CmisDocument.getInstance().cancelCheckOut(repositoryId,objectId,extension);
	}

	@Override
	public void checkIn(String repositoryId, Holder<String> objectId, Boolean major, Properties properties,
			ContentStream contentStream, String checkinComment, List<String> policies, Acl addAces, Acl removeAces,
			ExtensionsData extension) {
		CmisDocument.getInstance().checkIn(repositoryId,objectId,major,properties,
				contentStream,checkinComment,policies,addAces,removeAces,extension);
	}

	@Override
	public ObjectData getObjectOfLatestVersion(String repositoryId, String objectId, String versionSeriesId,
			Boolean major, String filter, Boolean includeAllowableActions, IncludeRelationships includeRelationships,
			String renditionFilter, Boolean includePolicyIds, Boolean includeAcl, ExtensionsData extension) {
		return CmisDocument.getInstance().getObjectOfLatestVersion(repositoryId,objectId,versionSeriesId,
				major,filter,includeAllowableActions,includeRelationships,renditionFilter,
				includePolicyIds,includeAcl,extension);
	}

	@Override
	public Properties getPropertiesOfLatestVersion(String repositoryId, String objectId, String versionSeriesId,
			Boolean major, String filter, ExtensionsData extension) {
		return CmisProperties.getInstance().getPropertiesOfLatestVersion(repositoryId,objectId,versionSeriesId,
				major,filter,extension);
	}

	@Override
	public List<ObjectData> getAllVersions(String repositoryId, String objectId, String versionSeriesId, String filter,
			Boolean includeAllowableActions, ExtensionsData extension) {
		return CmisDocument.getInstance().getAllVersions(repositoryId,objectId,versionSeriesId,filter,
				includeAllowableActions,extension);
	}

	@Override
	public ObjectList query(String repositoryId, String statement, Boolean searchAllVersions,
			Boolean includeAllowableActions, IncludeRelationships includeRelationships, String renditionFilter,
			BigInteger maxItems, BigInteger skipCount, ExtensionsData extension) {
		return CmisQuery.getInstance().query(renditionFilter,statement,searchAllVersions,
				includeAllowableActions,includeRelationships,renditionFilter,
				maxItems,skipCount,extension);
	}

	@Override
	public ObjectList getContentChanges(String repositoryId, Holder<String> changeLogToken, Boolean includeProperties,
			String filter, Boolean includePolicyIds, Boolean includeAcl, BigInteger maxItems,
			ExtensionsData extension) {
		return CmisRepository.getInstance().getContentChanges(repositoryId,changeLogToken,includeProperties,
				filter,includePolicyIds,includeAcl,maxItems,extension);
	}

	@Override
	public void addObjectToFolder(String repositoryId, String objectId, String folderId, Boolean allVersions,
			ExtensionsData extension) {
		CmisObject.getInstance().addObjectToFolder(repositoryId,objectId,folderId,allVersions,extension);
		
	}

	@Override
	public void removeObjectFromFolder(String repositoryId, String objectId, String folderId,
			ExtensionsData extension) {
		CmisObject.getInstance().removeObjectFromFolder(repositoryId,objectId,folderId,extension);
	}

	@Override
	public ObjectList getObjectRelationships(String repositoryId, String objectId, Boolean includeSubRelationshipTypes,
			RelationshipDirection relationshipDirection, String typeId, String filter, Boolean includeAllowableActions,
			BigInteger maxItems, BigInteger skipCount, ExtensionsData extension) {
		return CmisRealation.getInstance().getObjectRelationships(repositoryId,objectId,includeSubRelationshipTypes,
				relationshipDirection,typeId,flash(),includeAllowableActions,maxItems,
				skipCount,extension);
	}

	@Override
	public Acl getAcl(String repositoryId, String objectId, Boolean onlyBasicPermissions, ExtensionsData extension) {
		return CmisAcl.getInstance().getAcl(repositoryId,objectId,onlyBasicPermissions,extension);
	}

	@Override
	public Acl applyAcl(String repositoryId, String objectId, Acl addAces, Acl removeAces,
			AclPropagation aclPropagation, ExtensionsData extension) {
		return CmisAcl.getInstance().applyAcl(repositoryId,objectId,addAces,removeAces,aclPropagation,extension);
	}

	@Override
	public void applyPolicy(String repositoryId, String policyId, String objectId, ExtensionsData extension) {
		CmisPolicy.getInstance().applyPolicy(repositoryId,policyId,objectId,extension);		
	}

	@Override
	public void removePolicy(String repositoryId, String policyId, String objectId, ExtensionsData extension) {
		CmisPolicy.getInstance().removePolicy(repositoryId,policyId,objectId,extension);
	}

	@Override
	public List<ObjectData> getAppliedPolicies(String repositoryId, String objectId, String filter,
			ExtensionsData extension) {
		return CmisPolicy.getInstance().getAppliedPolicies(repositoryId,objectId,filter,extension);
	}

	@Override
	public String create(String repositoryId, Properties properties, String folderId, ContentStream contentStream,
			VersioningState versioningState, List<String> policies, ExtensionsData extension) {
		throw new UnsupportedOperationException("YukEcm unsupport this method");
	}

	@Override
	public void deleteObjectOrCancelCheckOut(String repositoryId, String objectId, Boolean allVersions,
			ExtensionsData extension) {
		CmisDocument.getInstance().deleteObjectOrCancelCheckOut(repositoryId,objectId,allVersions,extension);
	}

	@Override
	public Acl applyAcl(String repositoryId, String objectId, Acl aces, AclPropagation aclPropagation) {
		return CmisAcl.getInstance().applyAcl(repositoryId,objectId,aces,aclPropagation);
	}

	@Override
	public ObjectInfo getObjectInfo(String repositoryId, String objectId) {
		return CmisObject.getInstance().getObjectInfo(repositoryId,objectId);
	}

	@Override
	public void close() {
		
	}
}
