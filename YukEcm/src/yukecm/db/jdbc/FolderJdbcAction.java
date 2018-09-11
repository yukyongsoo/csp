package yukecm.db.jdbc;

import java.io.UnsupportedEncodingException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import yukcommon.model.Folder;
import yukecm.config.BaseProperty;
import yukecm.db.FolderDbAction;

public class FolderJdbcAction extends AbsJdbcAction implements FolderDbAction{

	protected FolderJdbcAction() throws SQLException {
		super();
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.FolderDbAction#addFolder(yukcommon.model.Folder)
	 */
	@Override
	public void addFolder(Folder folder) throws SQLException, UnsupportedEncodingException {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukAddFolder(?,?,?,?,?,?)}");
			statement.setString(1, folder.getId());
			statement.setString(2, folder.getName());
			statement.setString(3, folder.getParentId());
			statement.setString(4, folder.getDescr());
			statement.setString(5, folder.getAclId());
			statement.setString(6, Boolean.toString(folder.isDerived()));
			statement.executeUpdate();
		}
		finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.FolderDbAction#delFolder(yukcommon.model.Folder)
	 */
	@Override
	public void delFolder(Folder folder) throws SQLException {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukDelFolder(?)}");
			statement.setString(1, folder.getId());
			statement.executeUpdate();
		}
		finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.FolderDbAction#updFolder(yukcommon.model.Folder)
	 */
	@Override
	public void updFolder(Folder folder) throws SQLException, UnsupportedEncodingException {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukUpdFolder(?,?,?,?,?,?)}");
			statement.setString(1, folder.getId());
			statement.setString(2, folder.getParentId());
			statement.setString(3, folder.getName());
			statement.setString(4, folder.getDescr());
			statement.setString(5, folder.getAclId());
			statement.setString(6, Boolean.toString(folder.isDerived()));
			statement.executeUpdate();
		}
		finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.FolderDbAction#getFolder(yukcommon.model.Folder)
	 */
	@Override
	public Folder getFolder(Folder folder) throws SQLException, UnsupportedEncodingException {
		CallableStatement statement = null;
		ResultSet resultSet = null;
		Folder nFolder  = null;
		try {
			statement = connection.prepareCall("{call YukGetFolder(?,?)}");
			statement.setString(1, folder.getId());
			statement.registerOutParameter(2,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(2);
			while (resultSet.next()){
				nFolder = new Folder();
				nFolder.setId(resultSet.getString(1));
				nFolder.setParentId(resultSet.getString(2));
				nFolder.setName(resultSet.getString(3));
				nFolder.setDescr(resultSet.getString(4));
				nFolder.setAclId(resultSet.getString(5));
				nFolder.setDerived(Boolean.parseBoolean(resultSet.getString(6)));
			}
		}
		finally {
			closeResouce(resultSet, statement);
		}
		return nFolder;
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.FolderDbAction#getFolderList(yukcommon.model.Folder)
	 */
	@Override
	public List<Folder> getFolderList(Folder folder) throws SQLException, UnsupportedEncodingException {
		CallableStatement statement = null;
		ResultSet resultSet = null;
		List<Folder> list = new ArrayList<Folder>();
		try {
			statement = connection.prepareCall("{call YukGetFolderList(?)}");
			statement.registerOutParameter(1,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(1);
			while (resultSet.next()){
				Folder nFolder = new Folder();
				nFolder.setId(resultSet.getString(1));
				nFolder.setParentId(resultSet.getString(2));
				nFolder.setName(resultSet.getString(3));
				nFolder.setDescr(resultSet.getString(4));
				nFolder.setAclId(resultSet.getString(5));
				nFolder.setDerived(Boolean.parseBoolean(resultSet.getString(6)));
				list.add(nFolder);
			}
		}
		finally {
			closeResouce(resultSet, statement);
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.FolderDbAction#getFolderChildList(yukcommon.model.Folder)
	 */
	@Override
	public List<Folder> getFolderChildList(Folder folder) throws SQLException, UnsupportedEncodingException {
		CallableStatement statement = null;
		ResultSet resultSet = null;
		List<Folder> list = new ArrayList<Folder>();
		try {
			statement = connection.prepareCall("{call YukGetFolderChildList(?,?)}");
			statement.setString(1, folder.getId());
			statement.registerOutParameter(2,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(2);
			while (resultSet.next()){
				Folder nFolder = new Folder();
				nFolder.setId(resultSet.getString(1));
				nFolder.setParentId(resultSet.getString(2));
				nFolder.setName(resultSet.getString(3));
				nFolder.setDescr(resultSet.getString(4));
				nFolder.setAclId(resultSet.getString(5));
				nFolder.setDerived(Boolean.parseBoolean(resultSet.getString(6)));
				list.add(nFolder);
			}
		}
		finally {
			closeResouce(resultSet, statement);
		}
		return list;
	}

}
