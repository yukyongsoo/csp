package yukecm.injecter.docNcontent;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import yukcommon.dic.type.OnErrorType;
import yukcommon.model.Content;
import yukcommon.model.Doc;
import yukcommon.util.LoggerUtil;
import yukecm.db.ContentDbAction;
import yukecm.db.DbConnFac;
import yukecm.db.DocDbAction;
import yukecm.injecter.InjecterUtil;


public class DCinjetor {
	private static DCinjetor injecter;

	public static DCinjetor getInstance() {
		if(injecter == null)
			injecter = new DCinjetor();
		return injecter;
	}

	private DCinjetor() {

	}

	public void addDoc(Doc doc) throws SQLException {
		DocDbAction action = null;
		long start = System.currentTimeMillis();
		try {
			action = DbConnFac.getInstance().getDocDbAction();
			action.addDoc(doc);
			action.commits();
			LoggerUtil.traceTime(getClass(), "addDoc success. time is {}MS.DocId :" + doc.getId(), start);
		} catch (SQLException e) {
			DbConnFac.staticRollBack(action);
			LoggerUtil.traceTime(getClass(), "addDoc fail. time is {}MS.DocId :" + doc.getId(), start);
			throw e;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public Doc getDoc(Doc doc,OnErrorType type) throws SQLException {
		DocDbAction action = null;
		Doc nDoc = null;
		long start = System.currentTimeMillis();
		try {
			action = DbConnFac.getInstance().getDocDbAction();
			nDoc = action.getDoc(doc);
			InjecterUtil.onErrorException(nDoc, type);
			return nDoc;
		} finally {
			LoggerUtil.traceTime(getClass(), "getDoc time is {}MS.DocId :" + doc.getId(), start);
			DbConnFac.staticClose(action);
		}
	}

	public void delDoc(Doc doc) throws SQLException {
		DocDbAction action = null;
		long start = System.currentTimeMillis();
		try {
			action = DbConnFac.getInstance().getDocDbAction();
			action.delDoc(doc);
			action.commits();
			LoggerUtil.traceTime(getClass(), "delDoc success. time is {}MS.DocId :" + doc.getId(), start);
		} catch (SQLException e) {
			DbConnFac.staticRollBack(action);
			LoggerUtil.traceTime(getClass(), "delDoc fail. time is {}MS.DocId :" + doc.getId(), start);
			throw e;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public void updDoc(Doc doc) throws SQLException {
		DocDbAction action = null;
		long start = System.currentTimeMillis();
		try {
			action = DbConnFac.getInstance().getDocDbAction();
			action.updDoc(doc);
			action.commits();
			LoggerUtil.traceTime(getClass(), "updDoc success. time is {}MS.DocId :" + doc.getId(), start);
		} catch (SQLException e) {
			DbConnFac.staticRollBack(action);
			LoggerUtil.traceTime(getClass(), "updDoc fail. time is {}MS.DocId :" + doc.getId(), start);
			throw e;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public void addContent(Content content) throws UnsupportedEncodingException, SQLException {
		ContentDbAction action = null;
		long start = System.currentTimeMillis();
		try {
			action = DbConnFac.getInstance().getContentDbAction();
			action.addContent(content);
			action.commits();
			LoggerUtil.traceTime(getClass(), "addContent success. time is {}MS.DocId :" + content.getDocId(), start);
		} catch (SQLException e) {
			DbConnFac.staticRollBack(action);
			LoggerUtil.traceTime(getClass(), "addContent fail. time is {}MS.DocId :" + content.getDocId(), start);
			throw e;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public List<Content> getContent(Content content,OnErrorType type) throws SQLException, UnsupportedEncodingException {
		ContentDbAction action = null;
		long start = System.currentTimeMillis();
		try {
			action = DbConnFac.getInstance().getContentDbAction();
			List<Content> contentList = action.getContent(content);
			InjecterUtil.onErrorListException(contentList, type);
			return contentList;
		} finally {
			DbConnFac.staticClose(action);
			LoggerUtil.traceTime(getClass(), "getContent time is {}MS.DocId :" + content.getDocId(), start);
		}
	}
	
	public List<Content> getStorageContent(Content content,OnErrorType type) throws SQLException, UnsupportedEncodingException {
		ContentDbAction action = null;
		long start = System.currentTimeMillis();
		try {
			action = DbConnFac.getInstance().getContentDbAction();
			List<Content> storageContent = action.getStorageContent(content);
			InjecterUtil.onErrorListException(storageContent, type);
			return storageContent;
		} finally {
			DbConnFac.staticClose(action);
			LoggerUtil.traceTime(getClass(), "getStorageContent time is {}MS.DocId :" + content.getDocId(), start);
		}
	}
	

	public List<Content> getVersionContent(Content content, OnErrorType type) throws SQLException, UnsupportedEncodingException {
		ContentDbAction action = null;
		long start = System.currentTimeMillis();
		try {
			action = DbConnFac.getInstance().getContentDbAction();
			List<Content> versionContents = action.getVersionContent(content);
			InjecterUtil.onErrorListException(versionContents, type);
			return versionContents;
		} finally {
			DbConnFac.staticClose(action);
			LoggerUtil.traceTime(getClass(), "getVersionContent time is {}MS.DocId :" + content.getDocId(), start);
		}
	}
	
	public Content getSingleContent(Content content,OnErrorType type) throws SQLException, UnsupportedEncodingException {
		ContentDbAction action = null;
		long start = System.currentTimeMillis();
		try {
			action = DbConnFac.getInstance().getContentDbAction();
			Content singleContent = action.getSingleContent(content);
			InjecterUtil.onErrorException(singleContent, type);
			return singleContent;
		} finally {
			DbConnFac.staticClose(action);
			LoggerUtil.traceTime(getClass(), "getSingleContent time is {}MS.DocId :" + content.getDocId(), start);
		}
	}

	public void deleteContent(Content content) throws SQLException {
		ContentDbAction action = null;
		long start = System.currentTimeMillis();
		try {
			action = DbConnFac.getInstance().getContentDbAction();
			action.deleteContent(content);
			action.commits();
			LoggerUtil.traceTime(getClass(), "deleteContent success. time is {}MS.DocId :" + content.getDocId(), start);
		} catch (SQLException e) {
			DbConnFac.staticRollBack(action);
			LoggerUtil.traceTime(getClass(), "deleteContent fail. time is {}MS.DocId :" + content.getDocId(), start);
			throw e;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public void updateContent(Content content, String oldStrId) throws SQLException {
		ContentDbAction action = null;
		long start = System.currentTimeMillis();
		try {
			action = DbConnFac.getInstance().getContentDbAction();
			action.updateContent(content,oldStrId);
			action.commits();
			LoggerUtil.traceTime(getClass(), "updateContent success. time is {}MS.DocId :" + content.getDocId(), start);
		} catch (SQLException e) {
			DbConnFac.staticRollBack(action);
			LoggerUtil.traceTime(getClass(), "updateContent fail. time is {}MS.DocId :" + content.getDocId(), start);
			throw e;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public List<Doc> getFolderDocList(Doc doc, OnErrorType type) throws SQLException {
		DocDbAction action = null;
		try {
			action = DbConnFac.getInstance().getDocDbAction();
			List<Doc> list = action.getFolderDocList(doc);
			InjecterUtil.onErrorListException(list, type);
			return list;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

}
