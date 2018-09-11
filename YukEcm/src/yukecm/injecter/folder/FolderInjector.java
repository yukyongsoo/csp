package yukecm.injecter.folder;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import yukcommon.dic.type.OnErrorType;
import yukcommon.model.Folder;
import yukecm.db.DbConnFac;
import yukecm.db.FolderDbAction;
import yukecm.injecter.InjecterUtil;

public class FolderInjector {
	private static FolderInjector injecter;

	public static FolderInjector getInstance() {
		if(injecter == null)
			injecter = new FolderInjector();
		return injecter;
	}
	
	private FolderInjector() {
		
	}

	public void addFolder(Folder folder) throws UnsupportedEncodingException, SQLException {
		FolderDbAction action = null;
		try {
			action = DbConnFac.getInstance().getFolderDbAction();
			action.addFolder(folder);
			action.commits();
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public Folder getFolder(Folder folder, OnErrorType type) throws SQLException, UnsupportedEncodingException {
		FolderDbAction action = null;
		Folder nFolder = null;
		try {
			action = DbConnFac.getInstance().getFolderDbAction();
			nFolder = action.getFolder(folder);
			InjecterUtil.onErrorException(nFolder, type);
			return nFolder;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public void delFolder(Folder folder) throws SQLException {
		FolderDbAction action = null;
		try {
			action = DbConnFac.getInstance().getFolderDbAction();
			action.delFolder(folder);
			action.commits();
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public void updFolder(Folder folder) throws SQLException, UnsupportedEncodingException {
		FolderDbAction action = null;
		try {
			action = DbConnFac.getInstance().getFolderDbAction();
			action.updFolder(folder);
			action.commits();
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public List<Folder> getFolderList(Folder folder, OnErrorType type) throws UnsupportedEncodingException, SQLException {
		FolderDbAction action = null;
		try {
			action = DbConnFac.getInstance().getFolderDbAction();
			List<Folder> list = action.getFolderList(folder);
			InjecterUtil.onErrorListException(list, type);
			return list;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public List<Folder> getFolderChildList(Folder folder, OnErrorType type) throws SQLException, UnsupportedEncodingException {
		FolderDbAction action = null;
		try {
			action = DbConnFac.getInstance().getFolderDbAction();
			List<Folder> list = action.getFolderChildList(folder);
			InjecterUtil.onErrorListException(list, type);
			return list;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	
}
