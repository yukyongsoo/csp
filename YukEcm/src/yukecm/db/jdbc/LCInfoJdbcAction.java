package yukecm.db.jdbc;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import yukcommon.model.lifecycle.LifeCycleInfo;
import yukecm.config.BaseProperty;
import yukecm.db.LCInfoDbAction;

public class LCInfoJdbcAction extends AbsJdbcAction implements LCInfoDbAction{

	protected LCInfoJdbcAction() throws SQLException {
		super();
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.LCInfoDbAction#addLifeCycleInfo(yukcommon.model.lifecycle.LifeCycleInfo)
	 */
	@Override
	public void addLifeCycleInfo(LifeCycleInfo info) throws SQLException {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukAddLifeCycleInfo(?,?,?,?,?,?,?,?)}");
			statement.setString(1, info.getId());
			statement.setString(2, info.getState());
			statement.setInt(3, info.getNextRowNum());
			statement.setString(4, info.getStart());
			statement.setString(5, info.getEnd());
			statement.setLong(6, info.getTotalTarget());
			statement.setLong(7, info.getError());
			statement.setLong(8, info.getExcuted());
			statement.executeUpdate();
		}
		finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.LCInfoDbAction#delLifeCycleInfo(java.lang.String)
	 */
	@Override
	public void delLifeCycleInfo(String id) throws SQLException {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukDelLifeCycleInfo(?)}");
			statement.setString(1, id);
			statement.executeUpdate();
		}
		finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.LCInfoDbAction#getLifeCycleInfo(java.lang.String)
	 */
	@Override
	public LifeCycleInfo getLifeCycleInfo(String id) throws SQLException {
		LifeCycleInfo nInfo = null;
		CallableStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareCall("{call YukGetLifeCycleInfo(?,?)}");
			statement.setString(1, id);
			statement.registerOutParameter(2,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(2);
			while (resultSet.next()){
				nInfo = new LifeCycleInfo();
				nInfo.setId(resultSet.getString(1)); 
				nInfo.setState(resultSet.getString(2)); 
				nInfo.setNextRowNum(resultSet.getInt(3)); 
				nInfo.setStart(resultSet.getString(4)); 
				nInfo.setEnd(resultSet.getString(5)); 
				nInfo.setTotalTarget(resultSet.getLong(6)); 
				nInfo.setError(resultSet.getLong(7)); 
				nInfo.setExcuted(resultSet.getLong(8)); 
			}
			return nInfo;
		}
		finally {
			closeResouce(resultSet, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.LCInfoDbAction#updLifeCycleInfo(yukcommon.model.lifecycle.LifeCycleInfo)
	 */
	@Override
	public void updLifeCycleInfo(LifeCycleInfo info) throws SQLException {
    	CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukUpdLifeCycleInfo(?,?,?,?,?,?,?,?)}");
			statement.setString(1, info.getId());
			statement.setString(2, info.getState());
			statement.setInt(3, info.getNextRowNum());
			statement.setString(4, info.getStart());
			statement.setString(5, info.getEnd());
			statement.setLong(6, info.getTotalTarget());
			statement.setLong(7, info.getExcuted());
			statement.setLong(8, info.getError());
			statement.executeUpdate();
		}
		finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.LCInfoDbAction#updLifeCycleInfoAddData(yukcommon.model.lifecycle.LifeCycleInfo)
	 */
	@Override
	public void updLifeCycleInfoAddData(LifeCycleInfo info) throws SQLException {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukUpdLcInfoAddData(?,?,?)}");
			statement.setString(1, info.getId());
			statement.setLong(2, info.getError());
			statement.setLong(3, info.getExcuted());
			statement.executeUpdate();
		}
		finally {
			closeResouce(null, statement);
		}
	}
	
	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.LCInfoDbAction#updLifeCycleInfoRowNum(yukcommon.model.lifecycle.LifeCycleInfo)
	 */
	@Override
	public long updLifeCycleInfoRowNum(LifeCycleInfo info) throws SQLException {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YUKUPDLCINFOROWNUM(?,?)}");
			statement.setString(1, info.getId());
			statement.registerOutParameter(2,Types.INTEGER);
			statement.executeUpdate();
			return statement.getLong(2);
		}
		finally {
			closeResouce(null, statement);
		}
	}
}
