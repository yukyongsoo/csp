package yukecm.controller;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import yukcommon.dic.UriDic;
import yukcommon.dic.type.OnErrorType;
import yukcommon.model.Doc;
import yukcommon.model.Folder;
import yukcommon.model.User;
import yukecm.etc.EcmUtil;
import yukecm.injecter.docNcontent.DCinjetor;
import yukecm.injecter.folder.FolderInjector;

public class FolderController {
	private static class LazyHolder {
	    private static final FolderController folder = new FolderController();
	}

	public static FolderController getInstance(){
		return LazyHolder.folder;
	}

	private FolderController(){

	}

	public String addFolder(Folder folder, User user) throws Exception   {
		folder.setId(EcmUtil.getId());  
		if(folder.getAclId().isEmpty()) {
			Folder parent = new Folder();
			parent.setId(folder.getParentId());
			parent = FolderInjector.getInstance().getFolder(folder, OnErrorType.NONE);
			folder.setAclId(parent.getAclId());
			folder.setDerived(true);
		}
		SecureController.getInstance().checkFolderPermission(folder, user, UriDic.ADDFOLDER);
		FolderInjector.getInstance().addFolder(folder);
		return folder.getId();
	}

	public void delFolder(Folder folder, User user) throws Exception  {
		Folder nFolder = FolderInjector.getInstance().getFolder(folder,OnErrorType.NOTEXIST);
		SecureController.getInstance().checkFolderPermission(nFolder,user, UriDic.DELFOLDER);
		recusiveCheckFolderPermission(nFolder,user,UriDic.DELFOLDER);
		//TODO recusive
		FolderInjector.getInstance().delFolder(nFolder);
	}

	public void updFolder(Folder folder, User user) throws Exception{
		Folder nFolder = FolderInjector.getInstance().getFolder(folder,OnErrorType.NOTEXIST);
		SecureController.getInstance().checkFolderPermission(nFolder, user, UriDic.UPDFOLDER);
		recusiveCheckFolderPermission(nFolder,user,UriDic.UPDFOLDER);
		//TODO recusive
		FolderInjector.getInstance().updFolder(nFolder);
	}

	public Folder getFolder(Folder folder, User user) throws Exception {
		Folder nFolder = FolderInjector.getInstance().getFolder(folder,OnErrorType.NOTEXIST);
		SecureController.getInstance().checkFolderPermission(nFolder,user,UriDic.GETFOLDER);
		return nFolder;
	}

	public List<Folder> getFolderList(Folder folder, User user) throws UnsupportedEncodingException, SQLException {
		return FolderInjector.getInstance().getFolderList(folder,OnErrorType.NONE);
	}

	public List<Folder> getFolderChildList(Folder folder, User user) throws UnsupportedEncodingException, SQLException {
		return FolderInjector.getInstance().getFolderChildList(folder,OnErrorType.NONE);
	}
	
	private void recusiveCheckFolderPermission(Folder folder,User user,String uri) throws Exception {
		List<Folder> folderChildList = getFolderChildList(folder, user);
		for(Folder child : folderChildList) {
			recusiveCheckFolderPermission(child,user,uri);
		}
		SecureController.getInstance().checkFolderPermission(folder,user, uri);
		Doc doc = new Doc();
		doc.setFolderId(folder.getId());
		List<Doc> folderDocList = DCinjetor.getInstance().getFolderDocList(doc, OnErrorType.NONE);
		for(Doc childDoc : folderDocList) {
			SecureController.getInstance().checkDocPermission(childDoc, user, UriDic.DELDOC);
		}
	}
}
