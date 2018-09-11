package yukecm.controller;

import java.sql.SQLException;

import yukcommon.dic.type.OnErrorType;
import yukcommon.exception.WrongOperationException;
import yukcommon.model.Doc;
import yukcommon.model.Version;
import yukcommon.model.fileitem.IFileItem;
import yukecm.injecter.docNcontent.DCinjetor;

public class VersionController {
	private static class LazyHolder {
	    private static final VersionController version = new VersionController();
	}

	public static VersionController getInstance(){
		return LazyHolder.version;
	}
	
	private VersionController() {
		
	}

	public void checkOut(Version version) throws SQLException {
		Doc doc = new Doc();
		doc.setId(version.getDocId());
		doc = DCinjetor.getInstance().getDoc(doc, OnErrorType.NOTEXIST);
		if(doc.isCheckOut())
			throw new WrongOperationException("document already checkout.");
		doc.setCheckOut(true);
		DCinjetor.getInstance().updDoc(doc);
	}

	public void checkIn(Version version, IFileItem item) throws SQLException {
		Doc doc = new Doc();
		doc.setId(version.getDocId());
		doc = DCinjetor.getInstance().getDoc(doc, OnErrorType.NOTEXIST);
		if(!doc.isCheckOut())
			throw new WrongOperationException("document not checkout State. You need Checkout first.");
		//calc version
		//add content 
		//update doc last version
	}

	public void cancleCheckOut(Version version) throws SQLException {
		Doc doc = new Doc();
		doc.setId(version.getDocId());
		doc = DCinjetor.getInstance().getDoc(doc, OnErrorType.NOTEXIST);
		if(!doc.isCheckOut())
			throw new WrongOperationException("document not checkout State. You need Checkout?");
		doc.setCheckOut(false);
		DCinjetor.getInstance().updDoc(doc);
	}

	public void reverseVersion(Version version) throws SQLException {
		Doc doc = new Doc();
		doc.setId(version.getDocId());
		doc = DCinjetor.getInstance().getDoc(doc, OnErrorType.NOTEXIST);
		if(doc.isCheckOut())
			throw new WrongOperationException("document already checkout. if you need revese Version than Cancle Checkout first.");
		//calc version
		//change target version to calc version
		//update doc last version
	}
}
