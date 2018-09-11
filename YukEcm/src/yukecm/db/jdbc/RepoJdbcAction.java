package yukecm.db.jdbc;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import yukcommon.model.Repository;
import yukcommon.model.subrepo.SubRepoPipe;
import yukcommon.model.subrepo.SubRepoStr;
import yukecm.config.BaseProperty;
import yukecm.db.RepoDbAction;

public class RepoJdbcAction extends AbsJdbcAction implements RepoDbAction{

	protected RepoJdbcAction() throws SQLException {
		super();
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.RepoDbAction#getRepository(java.lang.String)
	 */
	@Override
	public Repository getRepository(String id) throws SQLException {
		Repository repo = null;
		CallableStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareCall("{call YukGetRepo(?,?)}");
			statement.setString(1, id);
			statement.registerOutParameter(2,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(2);
			while (resultSet.next()){
				repo = new Repository();
				repo.setId(resultSet.getString(1)); 
				repo.setName(resultSet.getString(2)); 
			}
		}
		finally {
			closeResouce(resultSet, statement);
		}
		return repo;
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.RepoDbAction#getRepositoryByName(java.lang.String)
	 */
	@Override
	public Repository getRepositoryByName(String name) throws SQLException {
		Repository repo = null;
		CallableStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareCall("{call YukGetRepoByName(?,?)}");
			statement.setString(1, name);
			statement.registerOutParameter(2,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(2);
			while (resultSet.next()){
				repo = new Repository();
				repo.setId(resultSet.getString(1)); 
				repo.setName(resultSet.getString(2)); 
			}
		}finally {
			closeResouce(resultSet, statement);
		}
		return repo;
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.RepoDbAction#addRepo(yukcommon.model.Repository)
	 */
	@Override
	public void addRepo(Repository repo) throws SQLException {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukAddRepo(?,?)}");
			statement.setString(1, repo.getId());
			statement.setString(2, repo.getName());
			statement.executeUpdate();
		} finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.RepoDbAction#delRepo(yukcommon.model.Repository)
	 */
	@Override
	public void delRepo(Repository repo) throws SQLException {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukDelRepo(?)}");
			statement.setString(1, repo.getId());
			statement.executeUpdate();
		} finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.RepoDbAction#updRepo(yukcommon.model.Repository)
	 */
	@Override
	public void updRepo(Repository repo) throws SQLException {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukUpdRepo(?,?)}");
			statement.setString(1, repo.getId());
			statement.setString(2, repo.getName());
			statement.executeUpdate();
		} finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.RepoDbAction#addRepoStr(yukcommon.model.Repository, int, int)
	 */
	@Override
	public void addRepoStr(Repository repo,int pOrder,int gOrder) throws SQLException {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukAddRepoStorage(?,?,?,?)}");
			statement.setString(1, repo.getId());
			statement.setString(2, repo.getPutOrderList().get(0).getStrId());
			statement.setInt(3, pOrder);
			statement.setInt(4, gOrder);
			statement.executeUpdate();
		} finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.RepoDbAction#delRepoStr(yukcommon.model.Repository)
	 */
	@Override
	public void delRepoStr(Repository repo) throws SQLException {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukDelRepoStorage(?,?)}");
			statement.setString(1, repo.getId());
			statement.setString(2, repo.getPutOrderList().get(0).getStrId());
			statement.executeUpdate();
		} finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.RepoDbAction#getRepoStr(java.lang.String)
	 */
	@Override
	public Repository getRepoStr(String id) throws SQLException {
		Repository nRepo = null;
		CallableStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareCall("{call YukGetRepoStorage(?,?)}");
			statement.setString(1, id);
			statement.registerOutParameter(2,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(2);
			while (resultSet.next()){
				if(nRepo == null){
					nRepo = new Repository();
					nRepo.setId(resultSet.getString(1)); 
				}
				SubRepoStr str = new SubRepoStr();
				str.setStrId(resultSet.getString(2)); 
				str.setGetOrder(resultSet.getInt(4)); 
				str.setPutOrder(resultSet.getInt(3)); 
				nRepo.getPutOrderList().put(str.getPutOrder(), str);
				nRepo.getGetOrderList().put(str.getGetOrder(), str);
			}
		}finally {
			closeResouce(resultSet, statement);
		}
		return nRepo;
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.RepoDbAction#getRepoPipe(java.lang.String)
	 */
	@Override
	public Map<Integer, SubRepoPipe> getRepoPipe(String id) throws SQLException {
		Map<Integer, SubRepoPipe> map = new ConcurrentSkipListMap<Integer, SubRepoPipe>();
		CallableStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareCall("{call YukGetRepoPipe(?,?)}");
			statement.setString(1, id);
			statement.registerOutParameter(2,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(2);
			while (resultSet.next()){
				SubRepoPipe pipe = new SubRepoPipe();
				pipe.setOrder(resultSet.getInt(3)); 
				pipe.setPipeId(resultSet.getString(2));   
				map.put(pipe.getOrder(),pipe);
			}
		} finally {
			closeResouce(resultSet, statement);
		}
		return map;
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.RepoDbAction#addRepoPipe(java.lang.String, java.lang.String, int)
	 */
	@Override
	public void addRepoPipe(String repoId, String pipeId, int max) throws SQLException {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukAddRepoPipe(?,?,?)}");
			statement.setString(1, repoId);
			statement.setString(2, pipeId);
			statement.setInt(3, max);
			statement.executeUpdate();
		} finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.RepoDbAction#delRepoPipe(java.lang.String, java.lang.String)
	 */
	@Override
	public void delRepoPipe(String repoId, String pipeId) throws SQLException {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukDelRepoPipe(?,?)}");
			statement.setString(1, repoId);
			statement.setString(2, pipeId);
			statement.executeUpdate();
		} finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.RepoDbAction#findRepo(java.lang.String)
	 */
	@Override
	public Repository findRepo(String strId) throws SQLException {
		Repository nRepo = null;
		CallableStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareCall("{call YukFindRepoStorage(?,?)}");
			statement.setString(1, strId);
			statement.registerOutParameter(2,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(2);
			while (resultSet.next()){
				if(nRepo == null)
					nRepo = new Repository();
				nRepo.setId(resultSet.getString(1)); 
				SubRepoStr str = new SubRepoStr();
				str.setStrId(resultSet.getString(2)); 
				str.setPutOrder(resultSet.getInt(3)); 
				str.setGetOrder(resultSet.getInt(4)); 
				nRepo.getPutOrderList().put(str.getPutOrder(), str);
				nRepo.getGetOrderList().put(str.getGetOrder(), str);
			}
		} finally {
			closeResouce(resultSet, statement);
		}
		
		return nRepo;
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.RepoDbAction#getRepoList()
	 */
	@Override
	public List<Repository> getRepoList() throws SQLException {
		List<Repository> list = new ArrayList<Repository>();
		CallableStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareCall("{call YukgetRepoList(?)}");
			statement.registerOutParameter(1,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(1);
			while (resultSet.next()){
				Repository repo = new Repository();
				repo.setId(resultSet.getString(1)); 
				repo.setName(resultSet.getString(2)); 
				list.add(repo);
			}
		} finally {
			closeResouce(resultSet, statement);
		}
		return list;
	}

}
