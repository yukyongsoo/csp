package yukecm.db.jdbc;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import yukcommon.model.Doc;
import yukecm.config.BaseProperty;
import yukecm.db.DocDbAction;

public class DocJdbcAction extends AbsJdbcAction implements DocDbAction{

	protected DocJdbcAction() throws SQLException   {
		super();
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.DocDbAction#addDoc(yukcommon.model.Doc)
	 */
	@Override
	public void addDoc(Doc doc) throws SQLException  {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukAddDoc(?,?,?,?,?,?,?,?,?,?,?)}");
			statement.setString(1, doc.getId());
			statement.setString(2, doc.getName());
			statement.setString(3, doc.getWorkId());
			statement.setString(4, doc.getCreateDate());
			statement.setInt(5, doc.getLastVersion());
			statement.setString(6, Boolean.toString(doc.isCheckOut()));
			statement.setString(7, doc.getMigId());
			statement.setString(8, doc.getMetaName());
			statement.setString(9, doc.getFolderId());
			statement.setString(10, doc.getAclid());
			statement.setString(11, Boolean.toString(doc.isDerived()));
			statement.executeUpdate();
		} finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.DocDbAction#getDoc(yukcommon.model.Doc)
	 */
	@Override
	public Doc getDoc(Doc doc) throws SQLException {
		Doc ndoc = null;
		CallableStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareCall("{call YukGetDoc(?,?)}");
			statement.setString(1, doc.getId());
			statement.registerOutParameter(2,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(2);
			while (resultSet.next()){
				ndoc = new Doc();
				ndoc.setId(resultSet.getString(1)); 
				ndoc.setName(resultSet.getString(2));
				ndoc.setWorkId(resultSet.getString(3)); 
				ndoc.setCreateDate(resultSet.getString(4));  
				ndoc.setLastVersion(resultSet.getInt(5));   
				ndoc.setCheckOut(Boolean.parseBoolean(resultSet.getString(6)));  
				ndoc.setMigId(resultSet.getString(7)); 
				ndoc.setMetaName(resultSet.getString(8));
				ndoc.setFolderId(resultSet.getString(9)); 
				ndoc.setAclid(resultSet.getString(10)); 
				ndoc.setDerived(Boolean.parseBoolean(resultSet.getString(11)));
			}
		} finally {
			closeResouce(resultSet, statement);
		}
		return ndoc;
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.DocDbAction#delDoc(yukcommon.model.Doc)
	 */
	@Override
	public void delDoc(Doc doc) throws SQLException {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukDelDoc(?)}");
			statement.setString(1, doc.getId());
			statement.executeUpdate();
		} finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.DocDbAction#updDoc(yukcommon.model.Doc)
	 */
	@Override
	public void updDoc(Doc doc) throws SQLException {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukUpdDoc(?,?,?,?,?,?,?,?,?)}");
			statement.setString(1, doc.getId());
			statement.setString(2, doc.getName());
			statement.setInt(3, doc.getLastVersion());
			statement.setString(4, Boolean.toString(doc.isCheckOut()));
			statement.setString(5, doc.getMigId());
			statement.setString(6, doc.getMetaName());
			statement.setString(7, doc.getFolderId());
			statement.setString(8, doc.getAclid());
			statement.setString(9, Boolean.toString(doc.isDerived()));
			statement.executeUpdate();
		}  finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.DocDbAction#getFolderDocList(yukcommon.model.Doc)
	 */
	@Override
	public List<Doc> getFolderDocList(Doc doc) throws SQLException {
		List<Doc> list = new ArrayList<Doc>();
		CallableStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareCall("{call YukGetFolderDoc(?,?)}");
			statement.setString(1, doc.getFolderId());
			statement.registerOutParameter(2,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(2);
			while (resultSet.next()){
				Doc ndoc = new Doc();
				ndoc.setId(resultSet.getString(1)); 
				ndoc.setName(resultSet.getString(2));
				ndoc.setWorkId(resultSet.getString(3)); 
				ndoc.setCreateDate(resultSet.getString(4));  
				ndoc.setLastVersion(resultSet.getInt(5));   
				ndoc.setCheckOut(Boolean.parseBoolean(resultSet.getString(6)));  
				ndoc.setMigId(resultSet.getString(7)); 
				ndoc.setMetaName(resultSet.getString(8));
				ndoc.setFolderId(resultSet.getString(9)); 
				ndoc.setAclid(resultSet.getString(10)); 
				ndoc.setDerived(Boolean.parseBoolean(resultSet.getString(11)));
				list.add(ndoc);
			}
		} finally {
			closeResouce(resultSet, statement);
		}
		return list;
	}
}
