package yukecm.db.jdbc;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import yukcommon.model.Pipe;
import yukecm.config.BaseProperty;
import yukecm.db.PipeDbAction;

public class PipeJdbcAction extends AbsJdbcAction implements PipeDbAction{

	protected PipeJdbcAction() throws SQLException {
		super();
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.PipeDbAction#getPipe(java.lang.String)
	 */
	@Override
	public Pipe getPipe(String pipeId) throws SQLException {
		Pipe pipe = null;
		CallableStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareCall("{call YukGetPipe(?,?)}");
			statement.setString(1, pipeId);
			statement.registerOutParameter(2,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(2);
			while (resultSet.next()){
				pipe = new Pipe();
				pipe.setId(resultSet.getString(1)); 
				pipe.setName(resultSet.getString(2));
				pipe.setClassName(resultSet.getString(3)); 
				pipe.setPassOnError(Boolean.parseBoolean(resultSet.getString(4)));  
			}
		}
		finally {
			closeResouce(resultSet, statement);
		}
		return pipe;
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.PipeDbAction#addPipe(yukcommon.model.Pipe)
	 */
	@Override
	public void addPipe(Pipe pipe) throws SQLException  {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukAddPipe(?,?,?,?)}");
			statement.setString(1, pipe.getId());
			statement.setString(2, pipe.getName());
			statement.setString(3, pipe.getClassName());
			statement.setString(4, Boolean.toString(pipe.isPassOnError()));
			statement.executeUpdate();
		}
		finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.PipeDbAction#delPipe(yukcommon.model.Pipe)
	 */
	@Override
	public void delPipe(Pipe pipe) throws SQLException  {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukDelPipe(?)}");
			statement.setString(1, pipe.getId());
			statement.executeUpdate();
		}
		finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.PipeDbAction#updPipe(yukcommon.model.Pipe)
	 */
	@Override
	public void updPipe(Pipe pipe) throws SQLException  {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukUpdPipe(?,?,?,?)}");
			statement.setString(1, pipe.getId());
			statement.setString(2, pipe.getName());
			statement.setString(3, pipe.getClassName());
			statement.setString(4, Boolean.toString(pipe.isPassOnError()));
			statement.executeUpdate();
		}
		finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.PipeDbAction#getPipeList()
	 */
	@Override
	public List<Pipe> getPipeList() throws SQLException {
		List<Pipe> list = new ArrayList<Pipe>();
		CallableStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareCall("{call YukGetPipeList(?)}");
			statement.registerOutParameter(1,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(1);
			while (resultSet.next()){
				Pipe pipe = new Pipe();
				pipe.setId(resultSet.getString(1)); 
				pipe.setName(resultSet.getString(2)); 
				pipe.setClassName(resultSet.getString(3));
				pipe.setPassOnError(Boolean.parseBoolean(resultSet.getString(4)));  
				list.add(pipe);
			}
		}
		finally {
			closeResouce(resultSet, statement);
		}
		return list;
	}
}
