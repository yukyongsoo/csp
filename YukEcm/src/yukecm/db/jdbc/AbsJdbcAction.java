package yukecm.db.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import yukcommon.util.LoggerUtil;
import yukecm.db.AbsDbAction;

public abstract class AbsJdbcAction implements AbsDbAction {
	protected Connection connection;

	protected AbsJdbcAction() throws SQLException {
		JdbcConnMngr con = JdbcConnMngr.getInstance();
		this.connection = con.getCon();
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.AbsDbAction#commits()
	 */
	@Override
	public void commits() throws SQLException{
		this.connection.commit();
	}
	
	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.AbsDbAction#closeResouce(java.sql.ResultSet, java.sql.Statement)
	 */
	@Override
	public void closeResouce(ResultSet resultSet,Statement statement) {
		if (resultSet != null) try { resultSet.close(); } catch (SQLException e) {
			LoggerUtil.warn(getClass(), "Close ResultSet Fail", e);
		}
		if (statement != null) try { statement.close(); } catch (SQLException e) {
			LoggerUtil.warn(getClass(), "Close Statement Fail", e);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.AbsDbAction#close()
	 */
	@Override
	public void close() {
		if (this.connection != null) try {this.connection.close(); } catch (SQLException e) {
			LoggerUtil.warn(getClass(), "Close Connection Fail", e);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.AbsDbAction#rollBack()
	 */
	@Override
	public void rollBack() {
		if(this.connection != null) try { this.connection.rollback();} catch (SQLException e) {
			LoggerUtil.warn(getClass(), "RollBack Fail", e);
		}
	}
}
