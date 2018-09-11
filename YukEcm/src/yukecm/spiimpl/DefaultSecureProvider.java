package yukecm.spiimpl;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import yukcommon.dic.EtcDic;
import yukcommon.dic.UriDic;
import yukcommon.dic.type.OnErrorType;
import yukcommon.dic.type.PermissionType;
import yukcommon.dic.type.UserManageType;
import yukcommon.exception.NotExistException;
import yukcommon.exception.WrongOperationException;
import yukcommon.exception.WrongPasswordException;
import yukcommon.model.Ace;
import yukcommon.model.Credential;
import yukcommon.model.Doc;
import yukcommon.model.Folder;
import yukcommon.model.User;
import yukcommon.spi.SecureProvider;
import yukcommon.util.JsonUtil;
import yukecm.controller.AceController;
import yukecm.controller.UserController;
import yukecm.injecter.folder.FolderInjector;

public class DefaultSecureProvider implements SecureProvider{
	@Override
	public User checkUser(HttpRequest request, HttpResponse response, String uri) throws Exception {
		String json = request.getFirstHeader(EtcDic.CREDENTIAL).getValue();
		Credential credential = JsonUtil.fromJson(json, Credential.class);
		User user = UserController.getInstance().getUser(credential.getId());
		if(user == null)
			throw new NotExistException("current user not exist." + credential.getId());
		if(!user.getPassword().equals(credential.getPassword()))
			throw new WrongPasswordException("User : " + user.getId() +  "'s Password Not Correct");
		return user;
	}

	@Override
	public void checkSystemPermission(HttpRequest request, HttpResponse response, User user, String uri) throws Exception {
		if(uri.contains(UriDic.CHECKUSER))
			return;
		else if(uri.contains("acl") && user.getType().equals(UserManageType.PERMISSION))
				return;
		else if(user.getType().equals(UserManageType.ADMIN) || user.getType().equals(UserManageType.SYSTEM))
			return;
		throw new WrongOperationException("You Have not permission.");
	}
	
	@Override
	public void checkFolderPermission(Folder folder, User user, String uri) throws Exception {
		if(!(user.getType().equals(UserManageType.ADMIN) || user.getType().equals(UserManageType.DOCADMIN))) {
			if(uri.equals(UriDic.GETFOLDER)) {
				checkUserGroupPermission(folder.getAclId(),user,PermissionType.GETFOLDER);
			}
			else if(uri.equals(UriDic.DELFOLDER)) {
				checkUserGroupPermission(folder.getAclId(),user,PermissionType.DELFOLDER);
			}
			else if(uri.equals(UriDic.ADDFOLDER)) {
				checkParentFolderPermission(folder,user,PermissionType.ADDFOLDER);
			}
			else if(uri.equals(UriDic.UPDFOLDER)) {
				checkUserGroupPermission(folder.getAclId(),user,PermissionType.UPDFOLDER);
			}
		}
	}
	
	private void checkParentFolderPermission(Folder folder,User user,PermissionType type) throws Exception {
		Folder parent = new Folder();
		parent.setId(folder.getParentId());
		parent = FolderInjector.getInstance().getFolder(parent, OnErrorType.NONE);
		checkUserGroupPermission(parent.getAclId(), user, type);
	}
	
	@Override
	public void checkDocPermission(Doc doc, User user, String uri) throws Exception {
		if(!(user.getType().equals(UserManageType.ADMIN) || user.getType().equals(UserManageType.DOCADMIN))) {	
			if(uri.equals(UriDic.GETDOC)) {
				checkDocSubPermission(doc,user,PermissionType.GETFILE);
			}
			else if(uri.equals(UriDic.DELDOC)) {
				checkDocSubPermission(doc,user,PermissionType.DELFILE);
			}
			else if(uri.equals(UriDic.ADDDOC)) {
				Folder folder = new Folder();
				folder.setId(doc.getFolderId());
				Folder nFolder = FolderInjector.getInstance().getFolder(folder, OnErrorType.NONE);
				checkUserGroupPermission(nFolder.getAclId(),user,PermissionType.ADDFILE);
			}
			else if(uri.equals(UriDic.UPDDOC)) {
				checkUserGroupPermission(doc.getAclid(),user,PermissionType.UPDFILE);
			}
			else if(uri.equals(UriDic.ADDDOCTOFOLDER)) {
				Folder folder = new Folder();
				folder.setId(doc.getFolderId());
				Folder nFolder = FolderInjector.getInstance().getFolder(folder, OnErrorType.NONE);
				checkUserGroupPermission(nFolder.getAclId(),user,PermissionType.ADDFILE);
			}
			//TODO :: version 
		}
	}
	
	private void checkDocSubPermission(Doc doc, User user, PermissionType type) throws Exception {
		if(doc.isDerived()) {
			Folder folder = new Folder();
			folder.setId(doc.getFolderId());
			Folder nFolder = FolderInjector.getInstance().getFolder(folder, OnErrorType.NONE);
			checkUserGroupPermission(nFolder.getAclId(), user, type);
		}
		else {
			checkUserGroupPermission(doc.getAclid(), user, type);
		}
	}

	private void checkUserGroupPermission(String aclId,User user,PermissionType type) throws Exception {		
		if(!checkAclPermisson(aclId,user.getId(),type)) {
			if(!checkAclPermisson(aclId,user.getParentId(),type))
				throw new WrongOperationException("you have not permission");
		}
	}
	
	private boolean checkAclPermisson(String aclId, String id, PermissionType type) throws Exception {
		Ace ace = AceController.getInstance().getAce(aclId,id);
		if(ace == null)
			return false;
		if(ace.getHasPermission().contains(type) || ace.getHasPermission().contains(PermissionType.FILE)
				|| ace.getHasPermission().contains(PermissionType.FOLDER))
			return true;
		return false;
	}

}
