package yukecm.db.jdbc;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import yukcommon.dic.type.MetaSettingType;
import yukcommon.model.meta.MetaSetting;
import yukcommon.model.meta.SubMetaSettingData;
import yukcommon.util.JsonUtil;
import yukecm.config.BaseProperty;
import yukecm.db.MetaSettingDbAction;

public class MetaSettingJdbcAction extends AbsJdbcAction implements MetaSettingDbAction{

	protected MetaSettingJdbcAction() throws SQLException {
		super();
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.MetaSettingDbAction#addMetaSetting(yukcommon.model.meta.MetaSetting)
	 */
	@Override
	public void addMetaSetting(MetaSetting setting) throws SQLException {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukAddMetaSetting(?,?,?,?)}");
			statement.setString(1, setting.getName());
			statement.setString(2, setting.getType().toString());
			statement.setString(3, setting.getQuery());
			statement.setString(4, JsonUtil.toJson(setting.getMap()));
			statement.executeUpdate();
		}
		finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.MetaSettingDbAction#getMetaSetting(yukcommon.model.meta.MetaSetting)
	 */
	@Override
	public MetaSetting getMetaSetting(MetaSetting setting) throws SQLException {
		CallableStatement statement = null;
		ResultSet resultSet = null;
		MetaSetting nSetting = null;
		try {
			statement = connection.prepareCall("{call YukGetMetaSetting(?,?)}");
			statement.setString(1, setting.getName());
			statement.registerOutParameter(2,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(2);
			while (resultSet.next()){
				nSetting = new MetaSetting();
				nSetting.setName(resultSet.getString(1)); 
				nSetting.setType(MetaSettingType.valueOf(resultSet.getString(2))); 
				nSetting.setQuery(resultSet.getString(3)); 
				nSetting.setMap((Map<String, SubMetaSettingData>) JsonUtil.fromJson(resultSet.getString(4), JsonUtil.SMETAMAP)); 
			}
		}
		finally {
			closeResouce(resultSet, statement);
		}
		return nSetting;
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.MetaSettingDbAction#delMetaSetting(yukcommon.model.meta.MetaSetting)
	 */
	@Override
	public void delMetaSetting(MetaSetting setting) throws SQLException {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukDelMetaSetting(?)}");
			statement.setString(1, setting.getName());
			statement.executeUpdate();
		}
		finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.MetaSettingDbAction#updMetaSetting(yukcommon.model.meta.MetaSetting)
	 */
	@Override
	public void updMetaSetting(MetaSetting nSetting) throws SQLException {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukUpdMetaSetting(?,?,?,?)}");
			statement.setString(1, nSetting.getName());
			statement.setString(2, nSetting.getType().toString());
			statement.setString(3, nSetting.getQuery());
			statement.setString(4, JsonUtil.toJson(nSetting.getMap()));
			statement.executeUpdate();
		}
		finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.MetaSettingDbAction#getMetaSettingList()
	 */
	@Override
	public List<MetaSetting> getMetaSettingList() throws SQLException {
		List<MetaSetting> list = new ArrayList<MetaSetting>();
		CallableStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareCall("{call YukGetMetaSettingList(?)}");
			statement.registerOutParameter(1,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(1);
			while (resultSet.next()){
				MetaSetting nSetting = new MetaSetting();
				nSetting.setName(resultSet.getString(1)); 
				nSetting.setType(MetaSettingType.valueOf(resultSet.getString(2))); 
				nSetting.setQuery(resultSet.getString(3)); 
				nSetting.setMap((Map<String, SubMetaSettingData>) JsonUtil.fromJson(resultSet.getString(4), JsonUtil.SMETAMAP)); 
				list.add(nSetting);
			}
		}
		finally {
			closeResouce(resultSet, statement);
		}
		return list;
	}



}
