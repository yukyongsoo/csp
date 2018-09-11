package yukecm.db;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import yukcommon.model.Folder;

public interface FolderDbAction extends AbsDbAction{

	void addFolder(Folder folder) throws SQLException, UnsupportedEncodingException;

	void delFolder(Folder folder) throws SQLException;

	void updFolder(Folder folder) throws SQLException, UnsupportedEncodingException;

	Folder getFolder(Folder folder) throws SQLException, UnsupportedEncodingException;

	List<Folder> getFolderList(Folder folder) throws SQLException, UnsupportedEncodingException;

	List<Folder> getFolderChildList(Folder folder) throws SQLException, UnsupportedEncodingException;

}