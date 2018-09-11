package yukecm.db.jdbc;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import yukcommon.dic.type.StorageType;
import yukcommon.model.Storage;
import yukecm.config.BaseProperty;
import yukecm.db.StorageDbAction;

public class StorageJdbcAction extends AbsJdbcAction implements StorageDbAction{

	protected StorageJdbcAction() throws SQLException {
		super();
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.StorageDbAction#addStorage(yukcommon.model.Storage)
	 */
	@Override
	public void addStorage(Storage storage) throws SQLException {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukAddStorage(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			statement.setString(1, storage.getId());
			statement.setString(2, storage.getName());
			statement.setString(3, storage.getType().toString());
			statement.setString(4, storage.getClassName());
			statement.setString(5, storage.getBaseDir());
			statement.setString(6, Boolean.toString(storage.isUsed()));
			statement.setString(7, Boolean.toString(storage.isReadOnly()));
			statement.setString(8, storage.getAddress());
			statement.setString(9, storage.getWormId());
			statement.setString(10, storage.getWormPass());
			statement.setString(11, storage.getTag());
			statement.setString(12, storage.getClip());
			statement.setInt(13, storage.getThreadCount());
			statement.executeUpdate();
		} finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.StorageDbAction#delStorage(java.lang.String)
	 */
	@Override
	public void delStorage(String id) throws SQLException {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukDelStorage(?)}");
			statement.setString(1, id);
			statement.executeUpdate();
		} finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.StorageDbAction#updStorage(yukcommon.model.Storage)
	 */
	@Override
	public void updStorage(Storage storage) throws SQLException {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukUpdStorage(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			statement.setString(1, storage.getId());
			statement.setString(2, storage.getName());
			statement.setString(3, storage.getType().toString());
			statement.setString(4, storage.getClassName());
			statement.setString(5, storage.getBaseDir());
			statement.setString(6, Boolean.toString(storage.isUsed()));
			statement.setString(7, Boolean.toString(storage.isReadOnly()));
			statement.setString(8, storage.getAddress());
			statement.setString(9, storage.getWormId());
			statement.setString(10, storage.getWormPass());
			statement.setString(11, storage.getTag());
			statement.setString(12, storage.getClip());
			statement.setInt(13, storage.getThreadCount());
			statement.executeUpdate();
		} finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.StorageDbAction#getStorageByName(java.lang.String)
	 */
	@Override
	public Storage getStorageByName(String name) throws SQLException {
		Storage storage = null;
		CallableStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareCall("{call YukGetStorageByName(?,?)}");
			statement.setString(1, name);
			statement.registerOutParameter(2,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(2);
			while (resultSet.next()){
				storage = new Storage();
				storage.setId(resultSet.getString(1)); 
				storage.setName(resultSet.getString(2)); 
				storage.setType(StorageType.valueOf(resultSet.getString(3))); 
				storage.setClassName(resultSet.getString(4)); 
				storage.setBaseDir(resultSet.getString(5)); 
				storage.setUsed(Boolean.parseBoolean(resultSet.getString(6)));
				storage.setReadOnly(Boolean.parseBoolean(resultSet.getString(7)));
				storage.setAddress(resultSet.getString(8)); 
				storage.setWormId(resultSet.getString(9)); 
				storage.setWormPass(resultSet.getString(10)); 
				storage.setTag(resultSet.getString(11)); 
				storage.setClip(resultSet.getString(12)); 
				storage.setThreadCount(resultSet.getInt(13));
			}
		} finally {
			closeResouce(resultSet, statement);
		}
		return storage;
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.StorageDbAction#getStorage(java.lang.String)
	 */
	@Override
	public Storage getStorage(String id) throws SQLException {
		Storage storage = null;
		CallableStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareCall("{call YukGetStorage(?,?)}");
			statement.setString(1, id);
			statement.registerOutParameter(2,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(2);
			while (resultSet.next()){
				storage = new Storage();
				storage.setId(resultSet.getString(1)); 
				storage.setName(resultSet.getString(2)); 
				storage.setType(StorageType.valueOf(resultSet.getString(3))); 
				storage.setClassName(resultSet.getString(4)); 
				storage.setBaseDir(resultSet.getString(5)); 
				storage.setUsed(Boolean.parseBoolean(resultSet.getString(6)));
				storage.setReadOnly(Boolean.parseBoolean(resultSet.getString(7)));
				storage.setAddress(resultSet.getString(8)); 
				storage.setWormId(resultSet.getString(9)); 
				storage.setWormPass(resultSet.getString(10)); 
				storage.setTag(resultSet.getString(11)); 
				storage.setClip(resultSet.getString(12)); 
				storage.setThreadCount(resultSet.getInt(13));
			}
		}  finally {
			closeResouce(resultSet, statement);
		}
		return storage;
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.StorageDbAction#getStorageList()
	 */
	@Override
	public List<Storage> getStorageList() throws SQLException {
		List<Storage> list = new ArrayList<Storage>();
		CallableStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareCall("{call YukGetStorageList(?)}");
			statement.registerOutParameter(1,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(1);
			while (resultSet.next()){
				Storage storage = new Storage();
				storage.setId(resultSet.getString(1)); 
				storage.setName(resultSet.getString(2)); 
				storage.setType(StorageType.valueOf(resultSet.getString(3))); 
				storage.setClassName(resultSet.getString(4)); 
				storage.setBaseDir(resultSet.getString(5)); 
				storage.setUsed(Boolean.parseBoolean(resultSet.getString(6)));
				storage.setReadOnly(Boolean.parseBoolean(resultSet.getString(7)));
				storage.setAddress(resultSet.getString(8)); 
				storage.setWormId(resultSet.getString(9)); 
				storage.setWormPass(resultSet.getString(10)); 
				storage.setTag(resultSet.getString(11)); 
				storage.setClip(resultSet.getString(12)); 
				storage.setThreadCount(resultSet.getInt(13));
				list.add(storage);
			}
		} finally {
			closeResouce(resultSet, statement);
		}
		return list;
	}
}
