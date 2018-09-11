package yukecm.db.jdbc;

import java.io.UnsupportedEncodingException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import yukcommon.model.Content;
import yukcommon.util.LoggerUtil;
import yukecm.config.BaseProperty;
import yukecm.db.ContentDbAction;

public class ContentJdbcAction extends AbsJdbcAction implements ContentDbAction{

	protected ContentJdbcAction() throws SQLException {
		super();
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.ContentDbAction#getContent(yukcommon.model.Content)
	 */
	@Override
	public List<Content> getContent(Content content) throws UnsupportedEncodingException, SQLException   {
		List<Content> contents = new ArrayList<Content>();
		CallableStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareCall("{call YukGetContent(?,?)}");
			statement.setString(1, content.getDocId());
			statement.registerOutParameter(2,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(2);
			while (resultSet.next()){
				Content nContent = new Content();
				nContent.setApNum(resultSet.getInt(1)); 
				nContent.setDocId(resultSet.getString(2)); 
				nContent.setDocVersion(resultSet.getInt(3)); 
				nContent.setLoc(resultSet.getString(4));
				nContent.setName(resultSet.getString(5));
				nContent.setSize(resultSet.getLong(6));
				nContent.setCreator(resultSet.getString(7));
				nContent.setType(resultSet.getString(8)); 
				nContent.setStrId(resultSet.getString(9)); 
				contents.add(nContent);
			}
		}  finally {
			closeResouce(resultSet, statement);
		}
		return contents;
	}


	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.ContentDbAction#getStorageContent(yukcommon.model.Content)
	 */
	@Override
	public List<Content> getStorageContent(Content content) throws SQLException, UnsupportedEncodingException {
		List<Content> contents = new ArrayList<Content>();
		CallableStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareCall("{call YukGetStorageContent(?,?,?)}");
			statement.setString(1, content.getDocId());
			statement.setString(2, content.getStrId());
			statement.registerOutParameter(3,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(3);
			while (resultSet.next()){
				Content nCon = new Content();
				nCon.setApNum(resultSet.getInt(1));
				nCon.setDocId(resultSet.getString(2)); 
				nCon.setDocVersion(resultSet.getInt(3)); 
				nCon.setLoc(resultSet.getString(4));
				nCon.setName(resultSet.getString(5));
				nCon.setSize(resultSet.getLong(6));
				nCon.setCreator(resultSet.getString(7));
				nCon.setType(resultSet.getString(8)); 
				nCon.setStrId(resultSet.getString(9)); 
				contents.add(nCon);
			}
		}  finally {
			closeResouce(resultSet, statement);
		}
		return contents;
	}
	
	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.ContentDbAction#getSingleContent(yukcommon.model.Content)
	 */
	@Override
	public Content getSingleContent(Content content) throws SQLException, UnsupportedEncodingException {
		Content nCon = null;
		CallableStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareCall("{call YukGetSingleContent(?,?,?,?)}");
			statement.setString(1, content.getDocId());
			statement.setString(2, content.getStrId());
			statement.setInt(3, content.getDocVersion());
			statement.registerOutParameter(4,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(4);
			while (resultSet.next()){
				nCon = new Content();
				nCon.setApNum(resultSet.getInt(1)); 
				nCon.setDocId(resultSet.getString(2)); 
				nCon.setDocVersion(resultSet.getInt(3)); 
				nCon.setLoc(resultSet.getString(4));
				nCon.setName(resultSet.getString(5));
				nCon.setSize(resultSet.getLong(6));
				nCon.setCreator(resultSet.getString(7));
				nCon.setType(resultSet.getString(8)); 
				nCon.setStrId(resultSet.getString(9)); 
			}
		}  finally {
			closeResouce(resultSet, statement);
		}
		return nCon;
	}
	
	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.ContentDbAction#getVersionContent(yukcommon.model.Content)
	 */
	@Override
	public List<Content> getVersionContent(Content content) throws SQLException, UnsupportedEncodingException {
		List<Content> contents = new ArrayList<Content>();
		CallableStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareCall("{call YukGetVersionContent(?,?,?)}");
			statement.setString(1, content.getDocId());
			statement.setInt(2, content.getDocVersion());
			statement.registerOutParameter(3,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(3);
			while (resultSet.next()){
				Content nCon = new Content();
				nCon.setApNum(resultSet.getInt(1));
				nCon.setDocId(resultSet.getString(2)); 
				nCon.setDocVersion(resultSet.getInt(3)); 
				nCon.setLoc(resultSet.getString(4));
				nCon.setName(resultSet.getString(5));
				nCon.setSize(resultSet.getLong(6));
				nCon.setCreator(resultSet.getString(7));
				nCon.setType(resultSet.getString(8)); 
				nCon.setStrId(resultSet.getString(9)); 
				contents.add(nCon);
			}
		}  finally {
			closeResouce(resultSet, statement);
		}
		return contents;
	}


	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.ContentDbAction#addContent(yukcommon.model.Content)
	 */
	@Override
	public void addContent(Content content) throws SQLException, UnsupportedEncodingException   {
		CallableStatement statement = null;
		try{
			statement = connection.prepareCall("{call YukAddContent(?,?,?,?,?,?,?,?,?)}");
			statement.setInt(1, content.getApNum());
			statement.setString(2, content.getDocId());
			statement.setInt(3, content.getDocVersion());
			statement.setString(4, content.getLoc());
			statement.setString(5, content.getName());
			statement.setLong(6, content.getSize());
			statement.setString(7, content.getCreator());
			statement.setString(8, content.getType());
			statement.setString(9, content.getStrId());
			statement.executeUpdate();
		}
		finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.ContentDbAction#deleteContent(yukcommon.model.Content)
	 */
	@Override
	public void deleteContent(Content content) throws SQLException   {
		CallableStatement statement = null;
		try{
			statement = connection.prepareCall("{call YukDelContent(?,?,?)}");
			statement.setString(1, content.getDocId());
			statement.setString(2, content.getStrId());
			statement.setInt(3, content.getDocVersion());
			statement.executeUpdate();
		}
		catch(SQLException e){
			LoggerUtil.error(getClass(), "Content deleted . but db delete fail. it is dummy File.detail Data is"
					+"CotentID :" + content.getDocId() + "CotentVersion :" + content.getDocVersion()
					+"Content StrId " + content.getStrId(), e);
			throw e;
		}
		finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.ContentDbAction#updateContent(yukcommon.model.Content, java.lang.String)
	 */
	@Override
	public void updateContent(Content content, String oldStrId) throws SQLException  {
		CallableStatement statement = null;
		try{
			statement = connection.prepareCall("{call YukUpdContent(?,?,?,?,?,?,?,?)}");
			statement.setString(1, content.getDocId());
			statement.setInt(2, content.getApNum());
			statement.setString(3, content.getLoc());
			statement.setString(4, content.getCreator());
			statement.setLong(5, content.getSize());
			statement.setString(6, content.getStrId());
			statement.setString(7, oldStrId);
			statement.setInt(8, content.getDocVersion());
			statement.executeUpdate();
		}
		finally {
			closeResouce(null, statement);
		}
	}


}
