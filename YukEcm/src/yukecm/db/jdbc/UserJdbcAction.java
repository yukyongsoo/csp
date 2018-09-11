package yukecm.db.jdbc;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import yukcommon.dic.type.UserManageType;
import yukcommon.model.User;
import yukecm.config.BaseProperty;
import yukecm.db.UserDbAction;

public class UserJdbcAction  extends AbsJdbcAction implements UserDbAction{
	protected UserJdbcAction() throws SQLException {
		super();
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.UserDbcAction#getUser(java.lang.String)
	 */
	@Override
	public User getUser(String userName) throws SQLException   {
		User user = null;
		CallableStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareCall("{call YukGetUser(?,?)}");
			statement.setString(1, userName);
			statement.registerOutParameter(2,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(2);
			while (resultSet.next()){
				user = new User();
				user.setId(resultSet.getString(1)); 
				user.setName(resultSet.getString(2)); 
				user.setPassword(resultSet.getString(3)); 
				user.setParentId(resultSet.getString(4));
				user.setType(UserManageType.valueOf(resultSet.getString(5)));
			}
		}
		finally {
			closeResouce(resultSet, statement);
		}
		return user;
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.UserDbcAction#addUser(yukcommon.model.User)
	 */
	@Override
	public void addUser(User user) throws SQLException   {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukAddUser(?,?,?,?,?)}");
			statement.setString(1, user.getId());
			statement.setString(2, user.getName());
			statement.setString(3, user.getPassword());
			statement.setString(4, user.getParentId());
			statement.setString(5, user.getType().toString());
			statement.executeUpdate();
		} 
		finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.UserDbcAction#delUser(yukcommon.model.User)
	 */
	@Override
	public void delUser(User user) throws SQLException   {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukDelUser(?)}");
			statement.setString(1, user.getId());
			statement.executeUpdate();
		} 
		finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.UserDbcAction#updUser(yukcommon.model.User)
	 */
	@Override
	public void updUser(User user) throws SQLException   {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukUpdUser(?,?,?,?,?)}");
			statement.setString(1, user.getId());
			statement.setString(2, user.getName());
			statement.setString(3, user.getPassword());
			statement.setString(4, user.getParentId());
			statement.setString(5, user.getType().toString());
			statement.executeUpdate();
		} 
		finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.UserDbcAction#getUserList()
	 */
	@Override
	public List<User> getUserList() throws SQLException {
		List<User> list = new ArrayList<User>();
		CallableStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareCall("{call YukGetUserList(?)}");
			statement.registerOutParameter(1,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(1);
			while (resultSet.next()){
				User user = new User();
				user.setId(resultSet.getString(1)); 
				user.setName(resultSet.getString(2)); 
				user.setPassword(resultSet.getString(3)); 
				user.setParentId(resultSet.getString(4)); 
				user.setType(UserManageType.valueOf(resultSet.getString(5)));
				list.add(user);
			}
		} 
		finally {
			closeResouce(resultSet, statement);
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.UserDbcAction#getGroupUser(java.lang.String)
	 */
	@Override
	public List<User> getGroupUser(String parentId) throws SQLException {
		List<User> list = new ArrayList<User>();
		CallableStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareCall("{call YukGetGroupUserList(?,?)}");
			statement.setString(1, parentId);
			statement.registerOutParameter(2,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(2);
			while (resultSet.next()){
				User user = new User();
				user.setId(resultSet.getString(1)); 
				user.setName(resultSet.getString(2)); 
				user.setPassword(resultSet.getString(3)); 
				user.setParentId(resultSet.getString(4)); 
				user.setType(UserManageType.valueOf(resultSet.getString(5)));
				list.add(user);
			}
		} 
		finally {
			closeResouce(resultSet, statement);
		}
		return list;
	}
}
