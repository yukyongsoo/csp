package yukecm.controller;

import java.sql.SQLException;
import java.util.List;

import yukcommon.dic.UriDic;
import yukcommon.dic.type.OnErrorType;
import yukcommon.exception.WrongOperationException;
import yukcommon.model.Content;
import yukcommon.model.Doc;
import yukcommon.model.Folder;
import yukcommon.model.User;
import yukcommon.model.fileitem.IFileItem;
import yukecm.etc.EcmUtil;
import yukecm.injecter.docNcontent.DCinjetor;
import yukecm.injecter.workgroup.WorkInjector;

public class DocController {
	
	private static class LazyHolder {
	    private static final DocController doc = new DocController();
	}

	public static DocController getInstance(){
		return LazyHolder.doc;
	}

	private DocController(){
		
	}

	public String addDoc(Doc doc, IFileItem item, User user) throws Exception  {
		WorkInjector.getInstance().getWorking(doc.getWorkId(),OnErrorType.NOTEXIST);		
		doc.setId(EcmUtil.getId());
		doc.setCreateDate(EcmUtil.makeDate());
		doc.getContent().setCreator(user.getId());
		SecureController.getInstance().checkDocPermission(doc, user, UriDic.ADDDOC);	
		DCinjetor.getInstance().addDoc(doc);		
		try {
			ContentController.getInstance().addCon(doc, item);
		} catch (Exception e) {
			//TODO:: think deeper
			//delDoc(doc,user);
			throw e;
		}
		return doc.getId();
	}

	public Content getDoc(Doc doc, User user) throws Exception  {
		Doc ndoc = DCinjetor.getInstance().getDoc(doc,OnErrorType.NOTEXIST);
		SecureController.getInstance().checkDocPermission(ndoc,user,UriDic.GETDOC);
		return ContentController.getInstance().getCon(ndoc);
	}

	public void delDoc(Doc doc, User user) throws Exception   {
		Doc ndoc = DCinjetor.getInstance().getDoc(doc,OnErrorType.NOTEXIST);
		if(ndoc.isCheckOut())
			throw new WrongOperationException("current document is checkout state");
		SecureController.getInstance().checkDocPermission(ndoc,user,UriDic.DELDOC);
		ContentController.getInstance().delCon(ndoc);
		DCinjetor.getInstance().delDoc(ndoc);
	}
	
	public List<Doc> getFolderDocList(Doc doc, User user) throws SQLException {
		return DCinjetor.getInstance().getFolderDocList(doc,OnErrorType.NONE);
	}

	public void updDoc(Doc doc, IFileItem item, User user) throws Exception {
		Doc ndoc = DCinjetor.getInstance().getDoc(doc,OnErrorType.NOTEXIST);
		ContentController.getInstance().updCon(ndoc,item);
	}

	public void addDocToFolder(Doc doc, User user) throws Exception {
		Folder folder = new Folder();
		folder.setId(doc.getFolderId());
		FolderController.getInstance().getFolder(folder, user);
		Doc nDoc = DCinjetor.getInstance().getDoc(doc, OnErrorType.NOTEXIST);
		if(nDoc.isCheckOut())
			throw new WrongOperationException("current document is checkout state");
		nDoc.setFolderId(doc.getFolderId());
		SecureController.getInstance().checkDocPermission(nDoc, user, UriDic.ADDDOCTOFOLDER);
		DCinjetor.getInstance().updDoc(doc);
	}
}
