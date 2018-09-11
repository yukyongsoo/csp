package yukecm.db;

import java.sql.SQLException;

import yukcommon.exception.AdpatorException;
import yukecm.db.jdbc.JdbcConnMngr;

public class DbConnFac {
	
	private static class LazyHolder {
		private static DbConnFac fac;
	    static {
	    	try {
	    		fac = new DbConnFac();
			} catch (AdpatorException e) {
				throw new ExceptionInInitializerError(e);
			}
	    }
	}

	public static DbConnFac getInstance(){
		return LazyHolder.fac;
	}
	
	/**
	 * static helper for dbclose
	 * @param action
	 */
	public static void staticClose(AbsDbAction action) {
		if(action != null)
			action.close();
	}
	
	/**
	 * static helper for db rollback
	 * @param action
	 */
	public static void staticRollBack(AbsDbAction action) {
		if(action != null)
			action.rollBack();
	}
	
	DbConnMngr mngr;
	
	private DbConnFac() {
		mngr = JdbcConnMngr.getInstance();
	}

	public void test(String validQuery) throws SQLException {
		mngr.test(validQuery);
	}
	
	public AceDbAction getAceDbAction() throws SQLException{
		return mngr.getAceDbAction();
	}


	public LCDbAction getLCDbAction() throws SQLException {
		return mngr.getLcDbAction();
	}

	public AclDbAction getAclDbAction() throws SQLException {
		return mngr.getAclDbAction();
	}

	public ClusterDbAction getClusterDbAction() throws SQLException {
		return mngr.getClusterDbAction();
	}

	public DocDbAction getDocDbAction() throws SQLException {
		return mngr.getDocDbAction();
	}

	public ContentDbAction getContentDbAction() throws SQLException {
		return mngr.getContentDbAction();
	}

	public FolderDbAction getFolderDbAction() throws SQLException {
		return mngr.getFolderDbAction();
	}

	public GroupDbAction getGroupDbAction() throws SQLException {
		return mngr.getGroupDbAction();
	}

	public LCInfoDbAction getLCInfoDbAction() throws SQLException {
		return mngr.getLcInfoDbAction();
	}

	public LCSettingDbAction getLCSettingDbAction() throws SQLException {
		return mngr.getLCSettingDbAction();
	}

	public MetaDbAction getMetaDbAction() throws SQLException {
		return mngr.getMetaDbAction();
	}

	public MetaSettingDbAction getMetaSettingDbAction() throws SQLException {
		return mngr.getMetaSettingDbAction();
	}

	public PipeDbAction getPipeDbAction() throws SQLException {
		return mngr.getPipeDbAction();
	}

	public RepoDbAction getRepoDbAction() throws SQLException {
		return mngr.getRepoDbAction();
	}

	public RuleDbAction getRuleDbAction() throws SQLException {
		return mngr.getRuleDbAction();
	}
	
	public StorageDbAction getStorageDbAction() throws SQLException {
		return mngr.getStorageDbAction();
	}
	
	public UserDbAction getUserDbAction() throws SQLException {
		return mngr.getUserDbAction();
	}
	
	public WorkingDbAction getWorkingDbAction() throws SQLException {
		return mngr.getWorkingDbAction();
	}
}
