package yukecm.db.jdbc;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import yukcommon.dic.type.RuleType;
import yukcommon.model.lifecycle.LifeCycleSetting;
import yukecm.config.BaseProperty;
import yukecm.db.LCSettingDbAction;

public class LCSettingJdbcAction extends AbsJdbcAction implements LCSettingDbAction{
	protected LCSettingJdbcAction() throws SQLException {
		super();
	}

	//iLcId,iLcName,iLCType,iWorkId,iStartCron,iEndCron,iStartRange,iEndRange,iTcount,iLoopBack,iDbUpdate
	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.LCSettingDbAction#addLifeCycleSch(yukcommon.model.lifecycle.LifeCycleSetting)
	 */
	@Override
	public void addLifeCycleSch(LifeCycleSetting setting) throws SQLException{
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukAddLifeCycle(?,?,?,?,?,?,?,?,?,?)}");
			statement.setString(1, setting.getId());
			statement.setString(2, setting.getName());
			statement.setInt(3, setting.getType().ordinal());
			statement.setString(4, setting.getWorkId());
			statement.setString(5, setting.getStartingCron());
			statement.setString(6, setting.getEndingCron());
			statement.setString(7, setting.getStartingRange());
			statement.setString(8, setting.getEndRange());
			statement.setInt(9, setting.getThread());
			statement.setString(10, Boolean.toString(setting.isLoopBack()));
			statement.executeUpdate();
		}
		finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.LCSettingDbAction#delLifeCycleSch(java.lang.String)
	 */
	@Override
	public void delLifeCycleSch(String migId) throws SQLException{
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukDelLifeCycle(?)}");
			statement.setString(1, migId);
			statement.executeUpdate();
		}
		finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.LCSettingDbAction#getLifeCycleSch(java.lang.String)
	 */
	@Override
	public LifeCycleSetting getLifeCycleSch(String id) throws SQLException{
		LifeCycleSetting setting = null;
		CallableStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareCall("{call YukGetLifeCycle(?,?)}");
			statement.setString(1, id);
			statement.registerOutParameter(2,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(2);
			while (resultSet.next()){
				setting = new LifeCycleSetting();
				setting.setId(resultSet.getString(1)); 
				setting.setName(resultSet.getString(2)); 
				setting.setType(RuleType.values()[resultSet.getInt(3)]); 
				setting.setWorkId(resultSet.getString(4)); 
				setting.setStartingCron(resultSet.getString(5)); 
				setting.setEndingCron(resultSet.getString(6)); 
				setting.setStartingRange(resultSet.getString(7)); 
				setting.setEndRange(resultSet.getString(8)); 
				setting.setThread(resultSet.getInt(9));
				setting.setLoopBack(Boolean.parseBoolean(resultSet.getString(10))); 
			}
			return setting;
		}
		finally {
			closeResouce(resultSet, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.LCSettingDbAction#getLifeCycleList()
	 */
	@Override
	public List<LifeCycleSetting> getLifeCycleList() throws SQLException{
		List<LifeCycleSetting> list = new ArrayList<LifeCycleSetting>();
		CallableStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareCall("{call YukGetLifeCycleList(?)}");
			statement.registerOutParameter(1,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(1);
			while (resultSet.next()){
				LifeCycleSetting setting = new LifeCycleSetting();
				setting.setId(resultSet.getString(1)); 
				setting.setName(resultSet.getString(2));
				setting.setType(RuleType.values()[resultSet.getInt(3)]); 
				setting.setWorkId(resultSet.getString(4)); 
				setting.setStartingCron(resultSet.getString(5));
				setting.setEndingCron(resultSet.getString(6));
				setting.setStartingRange(resultSet.getString(7)); 
				setting.setEndRange(resultSet.getString(8)); 
				setting.setThread(resultSet.getInt(9));
				setting.setLoopBack(Boolean.parseBoolean(resultSet.getString(10))); 
				list.add(setting);
			}
			return list;
		}
		finally {
			closeResouce(resultSet, statement);
		}
	}
}
