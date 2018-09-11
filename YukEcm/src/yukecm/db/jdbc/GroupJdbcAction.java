package yukecm.db.jdbc;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import yukcommon.model.Group;
import yukecm.config.BaseProperty;
import yukecm.db.GroupDbAction;

public class GroupJdbcAction extends AbsJdbcAction implements GroupDbAction{

	protected GroupJdbcAction() throws SQLException {
		super();
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.GroupDbAction#getGroupList()
	 */
	@Override
	public List<Group> getGroupList() throws SQLException {
		CallableStatement statement = null;
		ResultSet resultSet = null;
		List<Group> list = new ArrayList<Group>();
		try {
			statement = connection.prepareCall("{call YukGetGroupList(?)}");
			statement.registerOutParameter(1,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(1);
			while (resultSet.next()){
				Group group = new Group();
				group.setId(resultSet.getString(1));
				group.setName(resultSet.getString(2));
				group.setParentId(resultSet.getString(3));
				list.add(group);
			}
		}
		finally {
			closeResouce(resultSet, statement);
		}
		return list;
	}
	
	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.GroupDbAction#getAllGroupList()
	 */
	@Override
	public List<Group> getAllGroupList() throws SQLException {
		CallableStatement statement = null;
		ResultSet resultSet = null;
		List<Group> list = new ArrayList<Group>();
		try {
			statement = connection.prepareCall("{call YukGetAllGroupList(?)}");
			statement.registerOutParameter(1,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(1);
			while (resultSet.next()){
				Group group = new Group();
				group.setId(resultSet.getString(1));
				group.setName(resultSet.getString(2));
				group.setParentId(resultSet.getString(3));
				list.add(group);
			}
		}
		finally {
			closeResouce(resultSet, statement);
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.GroupDbAction#getGroup(java.lang.String)
	 */
	@Override
	public Group getGroup(String id) throws SQLException {
		Group group = null;
		CallableStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareCall("{call YukGetGroup(?,?)}");
			statement.setString(1, id);
			statement.registerOutParameter(2,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(2);
			while (resultSet.next()){
				group = new Group();
				group.setId(resultSet.getString(1));
				group.setName(resultSet.getString(2));
				group.setParentId(resultSet.getString(3));
			}
		}
		finally {
			closeResouce(resultSet, statement);
		}
		return group;
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.GroupDbAction#addGroup(yukcommon.model.Group)
	 */
	@Override
	public void addGroup(Group group) throws SQLException {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukAddGroup(?,?,?)}");
			statement.setString(1, group.getId());
			statement.setString(2, group.getName());
			statement.setString(3, group.getParentId());
			statement.executeUpdate();
		} finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.GroupDbAction#delGroup(yukcommon.model.Group)
	 */
	@Override
	public void delGroup(Group group) throws SQLException {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukDelGroup(?)}");
			statement.setString(1, group.getId());
			statement.executeUpdate();
		} finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.GroupDbAction#updGroup(yukcommon.model.Group)
	 */
	@Override
	public void updGroup(Group group) throws SQLException {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukUpdGroup(?,?,?)}");
			statement.setString(1, group.getId());
			statement.setString(2, group.getName());
			statement.setString(3, group.getParentId());
			statement.executeUpdate();
		} finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.GroupDbAction#getGroupChildList(java.lang.String)
	 */
	@Override
	public List<Group> getGroupChildList(String id) throws SQLException {
		CallableStatement statement = null;
		ResultSet resultSet = null;
		List<Group> list = new ArrayList<Group>();
		try {
			statement = connection.prepareCall("{call YukGetGroupChildList(?,?)}");
			statement.setString(1, id);
			statement.registerOutParameter(2,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(2);
			while (resultSet.next()){
				Group group = new Group();
				group.setId(resultSet.getString(1));
				group.setName(resultSet.getString(2));
				group.setParentId(resultSet.getString(3));
				list.add(group);
			}
		}
		finally {
			closeResouce(resultSet, statement);
		}
		return list;
	}
}
