package yukecm.db.jdbc;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import yukcommon.dic.type.PermissionType;
import yukcommon.model.Ace;
import yukcommon.util.JsonUtil;
import yukecm.config.BaseProperty;
import yukecm.db.AceDbAction;

public class AceJdbcAction extends AbsJdbcAction implements AceDbAction{

	protected AceJdbcAction() throws SQLException {
		super();
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.AceDbAction#getAceAllList()
	 */
	@Override
	public List<Ace> getAceAllList() throws SQLException {
		CallableStatement statement = null;
		ResultSet resultSet = null;
		List<Ace> list = new ArrayList<Ace>();
		try {
			statement = connection.prepareCall("{call YukGetAllAceList(?)}");
			statement.registerOutParameter(1,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(1);
			while (resultSet.next()){
				Ace nAce = new Ace();
				nAce.setId(resultSet.getString(1)); 
				nAce.setChildId(resultSet.getString(2));
				String temp = resultSet.getString(3);
				Map<PermissionType, Boolean> map = JsonUtil.fromJson(temp, JsonUtil.PERBMAP);
				nAce.setPermission(map);
				list.add(nAce);
			}
		}
		finally {
			closeResouce(resultSet, statement);
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.AceDbAction#getAce(yukcommon.model.Ace)
	 */
	@Override
	public Ace getAce(Ace ace) throws SQLException {
		Ace nAce = null;
		CallableStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareCall("{call YukGetAce(?,?,?)}");
			statement.setString(1, ace.getId());
			statement.setString(2, ace.getChildId());
			statement.registerOutParameter(3,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(3);
			while (resultSet.next()){
				nAce = new Ace();
				nAce.setId(resultSet.getString(1)); 
				nAce.setChildId(resultSet.getString(2));
				String temp = resultSet.getString(3);
				Map<PermissionType, Boolean> map = JsonUtil.fromJson(temp, JsonUtil.PERBMAP);
				nAce.setPermission(map);
			}
		}
		finally {
			closeResouce(resultSet, statement);
		}
		return nAce;
	}
	
	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.AceDbAction#getAceList(yukcommon.model.Ace)
	 */
	@Override
	public List<Ace> getAceList(Ace ace) throws SQLException {
		CallableStatement statement = null;
		ResultSet resultSet = null;
		List<Ace> list = new ArrayList<Ace>();
		try {
			statement = connection.prepareCall("{call YukGetAceList(?,?)}");
			statement.setString(1, ace.getId());
			statement.registerOutParameter(2,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(2);
			while (resultSet.next()){
				Ace nAce = new Ace();
				nAce.setId(resultSet.getString(1)); 
				nAce.setChildId(resultSet.getString(2));
				String temp = resultSet.getString(3);
				Map<PermissionType, Boolean> map = JsonUtil.fromJson(temp, JsonUtil.PERBMAP);
				nAce.setPermission(map);
				list.add(nAce);
			}
		}
		finally {
			closeResouce(resultSet, statement);
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.AceDbAction#addAce(yukcommon.model.Ace)
	 */
	@Override
	public void addAce(Ace ace) throws SQLException {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukAddAce(?,?,?)}");
			statement.setString(1, ace.getId());
			statement.setString(2, ace.getChildId());
			String temp = JsonUtil.toJson(ace.getPermissionSet());
			statement.setString(3, temp);
			statement.executeUpdate();
		} 
		finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.AceDbAction#delAce(yukcommon.model.Ace)
	 */
	@Override
	public void delAce(Ace ace) throws SQLException {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukDelAce(?,?)}");
			statement.setString(1, ace.getId());
			statement.setString(2, ace.getChildId());
			statement.executeUpdate();
		} 
		finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.AceDbAction#updAce(yukcommon.model.Ace)
	 */
	@Override
	public void updAce(Ace ace) throws SQLException {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukUpdAce(?,?,?)}");
			statement.setString(1, ace.getId());
			statement.setString(2, ace.getChildId());
			String temp = JsonUtil.toJson(ace.getPermissionSet());
			statement.setString(3, temp);
			statement.executeUpdate();
		} 
		finally {
			closeResouce(null, statement);
		}
		
	}
}
