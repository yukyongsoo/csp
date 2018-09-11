package yukecm.db;

import java.sql.SQLException;

public abstract class DbConnMngr {
	protected abstract  void test(String query) throws SQLException;
	protected abstract  void closeManager() throws SQLException;
	protected abstract  AceDbAction getAceDbAction() throws SQLException;
	protected abstract  AclDbAction getAclDbAction() throws SQLException;
	protected abstract  ClusterDbAction getClusterDbAction() throws SQLException;
	protected abstract  ContentDbAction getContentDbAction() throws SQLException;
	protected abstract  DocDbAction getDocDbAction() throws SQLException;
	protected abstract  FolderDbAction getFolderDbAction() throws SQLException;
	protected abstract  GroupDbAction getGroupDbAction() throws SQLException;
	protected abstract  LCDbAction getLcDbAction() throws SQLException;
	protected abstract  LCInfoDbAction getLcInfoDbAction() throws SQLException;
	protected abstract  LCSettingDbAction getLCSettingDbAction() throws SQLException;
	protected abstract  MetaDbAction getMetaDbAction() throws SQLException;
	protected abstract  MetaSettingDbAction getMetaSettingDbAction() throws SQLException;
	protected abstract  PipeDbAction getPipeDbAction() throws SQLException;
	protected abstract  RepoDbAction getRepoDbAction() throws SQLException;
	protected abstract  RuleDbAction getRuleDbAction() throws SQLException;
	protected abstract  StorageDbAction getStorageDbAction() throws SQLException;
	protected abstract  UserDbAction getUserDbAction() throws SQLException;
	protected abstract  WorkingDbAction getWorkingDbAction() throws SQLException;
}
