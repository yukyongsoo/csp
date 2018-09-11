package yukecm.db.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbcp.BasicDataSource;

import yukcommon.exception.AdpatorException;
import yukcommon.util.LoggerUtil;
import yukecm.config.BaseProperty;
import yukecm.db.AceDbAction;
import yukecm.db.AclDbAction;
import yukecm.db.ClusterDbAction;
import yukecm.db.ContentDbAction;
import yukecm.db.DbConnMngr;
import yukecm.db.DocDbAction;
import yukecm.db.FolderDbAction;
import yukecm.db.GroupDbAction;
import yukecm.db.LCDbAction;
import yukecm.db.LCInfoDbAction;
import yukecm.db.LCSettingDbAction;
import yukecm.db.MetaDbAction;
import yukecm.db.MetaSettingDbAction;
import yukecm.db.PipeDbAction;
import yukecm.db.RepoDbAction;
import yukecm.db.RuleDbAction;
import yukecm.db.StorageDbAction;
import yukecm.db.UserDbAction;
import yukecm.db.WorkingDbAction;
import yukecm.etc.EcmUtil;

public class JdbcConnMngr extends DbConnMngr{
	
	private static class LazyHolder {
		private static JdbcConnMngr manager;
	    static {
	    	try {
	    		manager = new JdbcConnMngr();
			} catch (AdpatorException e) {
				throw new ExceptionInInitializerError(e);
			}
	    }
	}

	public static JdbcConnMngr getInstance(){
		return LazyHolder.manager;
	}

	BasicDataSource dataSource;

	protected JdbcConnMngr(){
		dataSource = new BasicDataSource();
		dataSource.setDriverClassName(BaseProperty.getInstance().driver);
		dataSource.setUrl(BaseProperty.getInstance().url);
		dataSource.setUsername(BaseProperty.getInstance().user);
		dataSource.setPassword(BaseProperty.getInstance().pawd);
		dataSource.setDefaultAutoCommit(false);
		dataSource.setMaxActive(BaseProperty.getInstance().maxActive);
		dataSource.setMaxIdle(BaseProperty.getInstance().maxActive);
		dataSource.setMaxWait(30000);
		dataSource.setTestWhileIdle(true);
		dataSource.setTimeBetweenEvictionRunsMillis((long)1000 * 60 * 3);
		dataSource.setInitialSize(10);
	}

	@Override
	protected void closeManager() throws SQLException  {
		dataSource.close();
	}

	protected Connection getCon() throws SQLException  {
		if(dataSource.getNumActive() == BaseProperty.getInstance().maxActive)
			LoggerUtil.info(getClass(), "Your max active db count too high. you need higher value. current is" + dataSource.getNumActive() , null);
		try {
			LoggerUtil.trace(EcmUtil.getCaller(),"Your current db used count : " + dataSource.getNumActive(), null);
		} catch (ClassNotFoundException e) {
			LoggerUtil.trace(getClass(),"can't find caller Class.ignore this",null);
		}
		return dataSource.getConnection();
	}

	@Override
	protected void test(String query) throws SQLException   {
		Connection connection = getCon();
		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.execute(query);
		}
		finally {
			if(statement != null)
				statement.close();
			connection.close();
		}
	}

	@Override
	protected AceDbAction getAceDbAction() throws SQLException {
		return new AceJdbcAction();
	}

	@Override
	protected AclDbAction getAclDbAction() throws SQLException {
		return new AclJdbcAction();
	}

	@Override
	protected ClusterDbAction getClusterDbAction() throws SQLException {
		return new ClusterJdbcAction();
	}

	@Override
	protected ContentDbAction getContentDbAction() throws SQLException {
		return new ContentJdbcAction();
	}

	@Override
	protected DocDbAction getDocDbAction() throws SQLException {
		return new DocJdbcAction();
	}

	@Override
	protected FolderDbAction getFolderDbAction() throws SQLException {
		return new FolderJdbcAction();
	}

	@Override
	protected GroupDbAction getGroupDbAction() throws SQLException {
		return new GroupJdbcAction();
	}

	@Override
	protected LCDbAction getLcDbAction() throws SQLException {
		return new LCJdbcAction();
	}

	@Override
	protected LCInfoDbAction getLcInfoDbAction() throws SQLException {
		return new LCInfoJdbcAction();
	}

	@Override
	protected LCSettingDbAction getLCSettingDbAction() throws SQLException {
		return new LCSettingJdbcAction();
	}

	@Override
	protected MetaDbAction getMetaDbAction() throws SQLException {
		return new MetaJdbcAction();
	}

	@Override
	protected MetaSettingDbAction getMetaSettingDbAction() throws SQLException {
		return new MetaSettingJdbcAction();
	}

	@Override
	protected PipeDbAction getPipeDbAction() throws SQLException {
		return new PipeJdbcAction();
	}

	@Override
	protected RepoDbAction getRepoDbAction() throws SQLException {
		return new RepoJdbcAction();
	}

	@Override
	protected RuleDbAction getRuleDbAction() throws SQLException {
		return new RuleJdbcAction();
	}

	@Override
	protected StorageDbAction getStorageDbAction() throws SQLException {
		return new StorageJdbcAction();
	}

	@Override
	protected UserDbAction getUserDbAction() throws SQLException {
		return new UserJdbcAction();
	}

	@Override
	protected WorkingDbAction getWorkingDbAction() throws SQLException {
		return new WorkingJdbcAction();
	}
}
