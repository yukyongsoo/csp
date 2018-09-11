package yukecm.db;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import yukcommon.model.Repository;
import yukcommon.model.subrepo.SubRepoPipe;

public interface RepoDbAction extends AbsDbAction{

	Repository getRepository(String id) throws SQLException;

	Repository getRepositoryByName(String name) throws SQLException;

	void addRepo(Repository repo) throws SQLException;

	void delRepo(Repository repo) throws SQLException;

	void updRepo(Repository repo) throws SQLException;

	void addRepoStr(Repository repo, int pOrder, int gOrder) throws SQLException;

	void delRepoStr(Repository repo) throws SQLException;

	Repository getRepoStr(String id) throws SQLException;

	Map<Integer, SubRepoPipe> getRepoPipe(String id) throws SQLException;

	void addRepoPipe(String repoId, String pipeId, int max) throws SQLException;

	void delRepoPipe(String repoId, String pipeId) throws SQLException;

	Repository findRepo(String strId) throws SQLException;

	List<Repository> getRepoList() throws SQLException;

}