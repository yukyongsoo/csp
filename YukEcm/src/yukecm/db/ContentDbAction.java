package yukecm.db;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import yukcommon.model.Content;

public interface ContentDbAction extends AbsDbAction{

	List<Content> getContent(Content content) throws UnsupportedEncodingException, SQLException;

	List<Content> getStorageContent(Content content) throws SQLException, UnsupportedEncodingException;

	Content getSingleContent(Content content) throws SQLException, UnsupportedEncodingException;

	List<Content> getVersionContent(Content content) throws SQLException, UnsupportedEncodingException;

	void addContent(Content content) throws SQLException, UnsupportedEncodingException;

	void deleteContent(Content content) throws SQLException;

	void updateContent(Content content, String oldStrId) throws SQLException;

}