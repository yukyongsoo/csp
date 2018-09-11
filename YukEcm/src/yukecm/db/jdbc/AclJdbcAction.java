package yukecm.db.jdbc;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import yukcommon.model.Acl;
import yukecm.config.BaseProperty;
import yukecm.db.AclDbAction;

public class AclJdbcAction extends AbsJdbcAction implements AclDbAction{
	protected AclJdbcAction() throws SQLException {
		super();
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.AcldbAction#getAcl(java.lang.String)
	 */
	@Override
	public Acl getAcl(String aclid) throws SQLException  {
		Acl acl = null;
		CallableStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareCall("{call YukGetAcl(?,?)}");
			statement.setString(1, aclid);
			statement.registerOutParameter(2,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(2);
			while (resultSet.next()){
				acl = new Acl();
				acl.setId(resultSet.getString(1)); 
				acl.setName(resultSet.getString(2)); 
			}
		} finally {
			closeResouce(resultSet, statement);
		}
		return acl;
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.AcldbAction#addAcl(yukcommon.model.Acl)
	 */
	@Override
	public void addAcl(Acl acl) throws SQLException {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukAddAcl(?,?)}");
			statement.setString(1, acl.getId());
			statement.setString(2, acl.getName());
			statement.executeUpdate();
		} finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.AcldbAction#delAcl(yukcommon.model.Acl)
	 */
	@Override
	public void delAcl(Acl acl) throws SQLException  {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukDelAcl(?)}");
			statement.setString(1, acl.getId());
			statement.executeUpdate();
		} finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.AcldbAction#updAcl(yukcommon.model.Acl)
	 */
	@Override
	public void updAcl(Acl acl) throws SQLException {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukUpdAcl(?,?)}");
			statement.setString(1, acl.getId());
			statement.setString(2, acl.getName());
			statement.executeUpdate();
			
		} finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.AcldbAction#getAclByName(java.lang.String)
	 */
	@Override
	public Acl getAclByName(String aclName) throws SQLException {
		Acl acl = null;
		CallableStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareCall("{call YukGetAclByName(?,?)}");
			statement.setString(1, aclName);
			statement.registerOutParameter(2,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(2);
			while (resultSet.next()){
				acl = new Acl();
				acl.setId(resultSet.getString(1)); 
				acl.setName(resultSet.getString(2));
			}
		} finally {
			closeResouce(resultSet, statement);
		}
		return acl;
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.AcldbAction#getAclList()
	 */
	@Override
	public List<Acl> getAclList() throws SQLException {
		List<Acl> list = new ArrayList<Acl>();
		CallableStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareCall("{call YukGetAclList(?)}");
			statement.registerOutParameter(1,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(1);
			while (resultSet.next()){
				Acl acl = new Acl();
				acl.setId(resultSet.getString(1));
				acl.setName(resultSet.getString(2));
				list.add(acl);
			}
		} finally {
			closeResouce(resultSet, statement);
		}
		return list;
	}
}
