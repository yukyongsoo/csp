package yukecm.db.jdbc;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import yukcommon.model.Doc;
import yukecm.config.BaseProperty;
import yukecm.db.LCDbAction;
import yukecm.lifecycle.LifeCycleCallable;
import yukecm.lifecycle.des.DesFileCallable;
import yukecm.lifecycle.mig.MigFileCallable;

public class LCJdbcAction extends AbsJdbcAction implements LCDbAction{
	protected LCJdbcAction() throws SQLException {
		super();
	}
	//Docid,DocName,WorkId,createDate,DocLastVersion,IsCheckOut,MigId,MetaName,ParentFolderId,AclId,IsDerived
	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.LCDbAction#getRangeTarget(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<LifeCycleCallable> getRangeTarget(String workId,String migId,String start,String end) throws SQLException {
		CallableStatement statement = null;
		ResultSet resultSet = null;
		List<LifeCycleCallable> list = new ArrayList<LifeCycleCallable>();
		try {
			statement = connection.prepareCall("{call YukGetRangeTarget(?,?,?,?,?)}");
			statement.setString(1, workId);
			statement.setString(2, migId);
			statement.setString(3, start);
			statement.setString(4, end);
			statement.setFetchSize(1000);
			statement.registerOutParameter(5,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(5);
			while (resultSet.next()){
				Doc doc = new Doc();
				doc.setId(resultSet.getString(1)); 
				doc.setName(resultSet.getString(2));
				doc.setWorkId(resultSet.getString(3)); 
				doc.setCreateDate(resultSet.getString(4));  
				doc.setLastVersion(resultSet.getInt(5));   
				doc.setCheckOut(Boolean.parseBoolean(resultSet.getString(6)));  
				doc.setMigId(resultSet.getString(7));
				doc.setMetaName(resultSet.getString(8));
				doc.setFolderId(resultSet.getString(9));
				doc.setAclid(resultSet.getString(10));
				doc.setDerived(Boolean.parseBoolean(resultSet.getString(11)));
				list.add(new MigFileCallable(doc));
			}
		}
		finally {
			closeResouce(resultSet, statement);
		}
		return list;
	}
	
	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.LCDbAction#getRangeTargetPaging(java.lang.String, java.lang.String, java.lang.String, java.lang.String, long)
	 */
	@Override
	public  List<LifeCycleCallable> getRangeTargetPaging(String workId,String migId,String start,String end,long paging) throws SQLException{
		CallableStatement statement = null;
		ResultSet resultSet = null;
		List<LifeCycleCallable> list = new ArrayList<LifeCycleCallable>();
		if(start == null || start.length() < 1)
			start = "0";
		if(end == null || end.length() < 1)
			end = "29991231240000";
		try {
			statement = connection.prepareCall("{call YukGetRangeTargetPaging(?,?,?,?,?,?)}");
			statement.setString(1, workId);
			statement.setString(2, migId);
			statement.setString(3, start);
			statement.setString(4, end);
			statement.setLong(5, paging);
			statement.setFetchSize(1000);
			statement.registerOutParameter(6,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(6);
			while (resultSet.next()){
				Doc doc = new Doc();
				doc.setId(resultSet.getString(1)); 
				doc.setName(resultSet.getString(2));
				doc.setWorkId(resultSet.getString(3)); 
				doc.setCreateDate(resultSet.getString(4));  
				doc.setLastVersion(resultSet.getInt(5));   
				doc.setCheckOut(Boolean.parseBoolean(resultSet.getString(6)));  
				doc.setMigId(resultSet.getString(7));
				doc.setMetaName(resultSet.getString(8));
				doc.setFolderId(resultSet.getString(9));
				doc.setAclid(resultSet.getString(10));
				doc.setDerived(Boolean.parseBoolean(resultSet.getString(11)));
				list.add(new MigFileCallable(doc));
			}
		}
		finally {
			closeResouce(resultSet, statement);
		}
		return list;
	}
	
	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.LCDbAction#getRangeTargetCount(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public long getRangeTargetCount(String workId,String migId,String start,String end) throws SQLException {
		CallableStatement statement = null;
		if(start == null || start.length() < 1)
			start = "0";
		if(end == null || end.length() < 1)
			end = "29991231240000";
		try {
			statement = connection.prepareCall("{call YukRangeTargetCount(?,?,?,?,?)}");
			statement.setString(1, workId);
			statement.setString(2, migId);
			statement.setString(3, start);
			statement.setString(4, end);
			statement.registerOutParameter(5,Types.INTEGER);
			statement.execute();
			return statement.getLong(5);
		}
		finally {
			closeResouce(null, statement);
		}
	}
	
	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.LCDbAction#getDesTargetCount(java.lang.String, java.lang.String)
	 */
	@Override
	public long getDesTargetCount(String workId, String dateTime) throws SQLException {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukDesTargetCount(?,?,?)}");
			statement.setString(1, workId);
			statement.setString(2, dateTime);
			statement.registerOutParameter(3,Types.INTEGER);
			statement.execute();
			return statement.getLong(3);
		}
		finally {
			closeResouce(null, statement);
		}
	}
	
	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.LCDbAction#getDesTarget(java.lang.String, java.lang.String)
	 */
	@Override
	public  List<LifeCycleCallable> getDesTarget(String workId, String dateTime) throws SQLException {
		CallableStatement statement = null;
		ResultSet resultSet = null;
		List<LifeCycleCallable> list = new ArrayList<LifeCycleCallable>();
		try {
			statement = connection.prepareCall("{call YukDesTarget(?,?,?)}");
			statement.setString(1, workId);
			statement.setString(2, dateTime);
			statement.setFetchSize(1000);
			statement.registerOutParameter(3,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(3);
			while (resultSet.next()){
				Doc doc = new Doc();
				doc.setId(resultSet.getString(1)); 
				doc.setName(resultSet.getString(2));
				doc.setWorkId(resultSet.getString(3)); 
				doc.setCreateDate(resultSet.getString(4));  
				doc.setLastVersion(resultSet.getInt(5));   
				doc.setCheckOut(Boolean.parseBoolean(resultSet.getString(6)));  
				doc.setMigId(resultSet.getString(7));
				doc.setMetaName(resultSet.getString(8));
				doc.setFolderId(resultSet.getString(9));
				doc.setAclid(resultSet.getString(10));
				doc.setDerived(Boolean.parseBoolean(resultSet.getString(11)));
				list.add(new DesFileCallable(doc));
			}
		}
		finally {
			closeResouce(resultSet, statement);
		}
		return list;
	}
	
	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.LCDbAction#getDesTargetPaging(java.lang.String, java.lang.String, long)
	 */
	@Override
	public  List<LifeCycleCallable> getDesTargetPaging(String workId, String dateTime,long paging) throws SQLException {
		CallableStatement statement = null;
		ResultSet resultSet = null;
		List<LifeCycleCallable> list = new ArrayList<LifeCycleCallable>();
		try {
			statement = connection.prepareCall("{call YukDesTargetPaging(?,?,?,?)}");
			statement.setString(1, workId);
			statement.setString(2, dateTime);
			statement.setLong(3, paging);
			statement.setFetchSize(1000);
			statement.registerOutParameter(4,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(4);
			while (resultSet.next()){
				Doc doc = new Doc();
				doc.setId(resultSet.getString(1)); 
				doc.setName(resultSet.getString(2));
				doc.setWorkId(resultSet.getString(3)); 
				doc.setCreateDate(resultSet.getString(4));  
				doc.setLastVersion(resultSet.getInt(5));   
				doc.setCheckOut(Boolean.parseBoolean(resultSet.getString(6)));  
				doc.setMigId(resultSet.getString(7));
				doc.setMetaName(resultSet.getString(8));
				doc.setFolderId(resultSet.getString(9));
				doc.setAclid(resultSet.getString(10));
				doc.setDerived(Boolean.parseBoolean(resultSet.getString(11)));
				list.add(new DesFileCallable(doc));
			}
		}
		finally {
			closeResouce(resultSet, statement);
		}
		return list;
	}
}
