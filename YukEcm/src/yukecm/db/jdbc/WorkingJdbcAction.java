package yukecm.db.jdbc;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import yukcommon.dic.type.RuleType;
import yukcommon.exception.NotSupportException;
import yukcommon.model.WorkingGroup;
import yukecm.config.BaseProperty;
import yukecm.db.WorkingDbAction;

public class WorkingJdbcAction extends AbsJdbcAction implements WorkingDbAction{

	protected WorkingJdbcAction() throws SQLException   {
		super();
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.WorkingDbAction#addWork(yukcommon.model.WorkingGroup)
	 */
	@Override
	public void addWork(WorkingGroup work) throws SQLException   {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukAddWorking(?,?,?)}");
			statement.setString(1, work.getId());
			statement.setString(2, work.getName());
			statement.setString(3, Boolean.toString(work.isAudit()));
			statement.executeUpdate();
		}
		finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.WorkingDbAction#updWork(yukcommon.model.WorkingGroup)
	 */
	@Override
	public void updWork(WorkingGroup work) throws SQLException {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukUpdWorking(?,?,?)}");
			statement.setString(1, work.getId());
			statement.setString(2, work.getName());
			statement.setString(3, Boolean.toString(work.isAudit()));
			statement.executeUpdate();
		}
		finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.WorkingDbAction#delWork(yukcommon.model.WorkingGroup)
	 */
	@Override
	public void delWork(WorkingGroup work) throws SQLException {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukDelWorking(?)}");
			statement.setString(1, work.getId());
			statement.executeUpdate();
		}
		finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.WorkingDbAction#getWorking(java.lang.String)
	 */
	@Override
	public WorkingGroup getWorking(String id) throws SQLException   {
		WorkingGroup group = null;
		CallableStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareCall("{call YukGetWorking(?,?)}");
			statement.setString(1, id);
			statement.registerOutParameter(2,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(2);
			while (resultSet.next()){
				group = new WorkingGroup();
				group.setId(resultSet.getString(1)); 
				group.setName(resultSet.getString(2)); 
				group.setAudit(Boolean.parseBoolean(resultSet.getString(3))); 
			}
		}
		finally {
			closeResouce(resultSet, statement);
		}
		return group;
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.WorkingDbAction#getWorkingByName(java.lang.String)
	 */
	@Override
	public WorkingGroup getWorkingByName(String name) throws SQLException   {
		WorkingGroup group = null;
		CallableStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareCall("{call YukGetWorkingByName(?,?)}");
			statement.setString(1, name);
			statement.registerOutParameter(2,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(2);
			while (resultSet.next()){
				group = new WorkingGroup();
				group.setId(resultSet.getString(1)); 
				group.setName(resultSet.getString(2)); 
				group.setAudit(Boolean.parseBoolean(resultSet.getString(3))); 
			}
		}
		finally {
			closeResouce(resultSet, statement);
		}
		return group;
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.WorkingDbAction#addWorkRule(java.lang.String, java.lang.String, yukcommon.dic.type.RuleType)
	 */
	@Override
	public void addWorkRule(String id, String ruleid, RuleType type) throws SQLException   {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukAddWorkingRule(?,?,?)}");
			statement.setString(1, id);
			statement.setString(2, ruleid);
			statement.setInt(3, type.ordinal());
			statement.executeUpdate();
		}
		finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.WorkingDbAction#delWorkRule(java.lang.String, java.lang.String)
	 */
	@Override
	public void delWorkRule(String id, String ruleid) throws SQLException   {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukDelWorkingRule(?,?)}");
			statement.setString(1, id);
			statement.setString(2, ruleid);
			statement.executeUpdate();
		}
		finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.WorkingDbAction#getWorkRule(java.lang.String)
	 */
	@Override
	public WorkingGroup getWorkRule(String workingId) throws SQLException   {
		WorkingGroup group = new WorkingGroup();
		group.setId(workingId); 
		CallableStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareCall("{call YukGetWorkingRule(?,?)}");
			statement.setString(1, group.getId());
			statement.registerOutParameter(2,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(2);
			while (resultSet.next()){
				RuleType ruleType = RuleType.values()[resultSet.getInt(3)];
				if(RuleType.INITRULE == ruleType)
					group.getInitList().add(resultSet.getString(2));
				else if(RuleType.MIGRULE == ruleType)
					group.setMigId(resultSet.getString(2)); 
				else if(RuleType.DESRULE == ruleType)
					group.setDesId(resultSet.getString(2)); 
				else
					throw new NotSupportException("ruleType " + ruleType + " has Founded. It is not Supported");
			}
		}
		finally {
			closeResouce(resultSet, statement);
		}
		return group;
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.WorkingDbAction#getWorkList()
	 */
	@Override
	public List<WorkingGroup> getWorkList() throws SQLException {
		List<WorkingGroup> list = new ArrayList<WorkingGroup>();
		CallableStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareCall("{call YukGetWorkList(?)}");
			statement.registerOutParameter(1,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(1);
			while (resultSet.next()){
				WorkingGroup group = new WorkingGroup();
				group.setId(resultSet.getString(1)); 
				group.setName(resultSet.getString(2)); 
				group.setAudit(Boolean.parseBoolean(resultSet.getString(3)));
				list.add(group);
			}
		}
		finally {
			closeResouce(resultSet, statement);
		}
		return list;
	}
}
