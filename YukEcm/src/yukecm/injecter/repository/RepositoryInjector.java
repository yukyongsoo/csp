package yukecm.injecter.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import yukcommon.dic.type.OnErrorType;
import yukcommon.model.Repository;
import yukcommon.model.subrepo.SubRepoPipe;
import yukcommon.model.subrepo.SubRepoStr;
import yukecm.cache.Cache;
import yukecm.cache.YLC;
import yukecm.config.BaseProperty;
import yukecm.db.DbConnFac;
import yukecm.db.RepoDbAction;
import yukecm.injecter.InjecterUtil;

public class RepositoryInjector {
	private static RepositoryInjector injecter;

	public static RepositoryInjector getInstance() throws InterruptedException, SQLException {
		if(injecter == null)
			injecter = new RepositoryInjector();
		return injecter;
	}

	private Cache<String, Repository> repoMap;

	private RepositoryInjector() throws InterruptedException, SQLException {
		if(BaseProperty.getInstance().inMem){
			repoMap = YLC.makeCache("REPO", new RepositoryJson());
			initRepository();
		}
	}

	public void initRepository() throws InterruptedException, SQLException {
		repoMap.clear();
		List<Repository> repoList = getRepoList();
		for(Repository repository : repoList){
			Map<Integer, SubRepoPipe> pipeMap = getRepoPipeInDB(repository.getId());
			repository.setPipeMap(pipeMap);
			Repository temp = getRepoStrInDb(repository.getId());
			if(temp != null){
				repository.setPutOrderList(temp.getPutOrderList());
				repository.setGetOrderList(temp.getGetOrderList());
			}
			repoMap.put(repository.getId(), repository);
		}
	}

	public List<Repository> getRepoList() throws SQLException {
		RepoDbAction action = null;
		try {
			action = DbConnFac.getInstance().getRepoDbAction();
			return action.getRepoList();
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public Repository getRepo(String id,OnErrorType type) throws SQLException {
		RepoDbAction action = null;
		Repository repository = null;
		try {
			if(BaseProperty.getInstance().inMem) {
				repository = repoMap.get(id);
				InjecterUtil.onErrorException(repository, type);
				return repository;
			}
			action = DbConnFac.getInstance().getRepoDbAction();
			repository = action.getRepository(id);
			InjecterUtil.onErrorException(repository, type);
			return repository;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public Repository getRepoByName(String name,OnErrorType type) throws SQLException {
		RepoDbAction action = null;
		Repository repository = null;
		try {
			if(BaseProperty.getInstance().inMem){
				for(Repository repo : repoMap.values()){
					if(repo.getName().equals(name)) {
						repository = repo;
						break;
						
					}
				}
				InjecterUtil.onErrorException(repository, type);
				return repository;
			}
			action = DbConnFac.getInstance().getRepoDbAction();
			repository = action.getRepositoryByName(name);
			InjecterUtil.onErrorException(repository, type);
			return repository;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public void addRepo(Repository repo) throws InterruptedException, SQLException {
		RepoDbAction action = null;
		try {
			if(BaseProperty.getInstance().inMem)
				repoMap.put(repo.getId(), repo);
			action = DbConnFac.getInstance().getRepoDbAction();
			action.addRepo(repo);
			action.commits();
		} catch (SQLException e) {
			if(BaseProperty.getInstance().inMem)
				repoMap.remove(repo.getId());
			DbConnFac.staticRollBack(action);
			throw e;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public void delRepo(Repository repo) throws InterruptedException, SQLException {
		RepoDbAction action = null;
		Repository rollback = null;
		try {
			if(BaseProperty.getInstance().inMem)
				rollback = repoMap.remove(repo.getId());
			action = DbConnFac.getInstance().getRepoDbAction();
			action.delRepo(repo);
			action.commits();
		} catch (SQLException e) {
			if(BaseProperty.getInstance().inMem)
				repoMap.put(repo.getId(), rollback);
			DbConnFac.staticRollBack(action);
			throw e;
		} finally {
			DbConnFac.staticClose(action);
		}

	}

	public void updRepo(Repository repo) throws InterruptedException, SQLException  {
		RepoDbAction action = null;
		Repository rollback = null;
		try {
			if(BaseProperty.getInstance().inMem){
				rollback = repoMap.remove(repo.getId());
				repoMap.put(repo.getId(), repo);
			}
			action = DbConnFac.getInstance().getRepoDbAction();
			action.updRepo(repo);
			action.commits();
		} catch (SQLException e) {
			if(BaseProperty.getInstance().inMem)
				repoMap.put(repo.getId(), rollback);
			DbConnFac.staticRollBack(action);
			throw e;
		} finally {
			DbConnFac.staticClose(action);
		}

	}

	public Repository getRepoStr(String id, OnErrorType type) throws SQLException {
		Repository repo = null;
		if (BaseProperty.getInstance().inMem) {
			repo = repoMap.get(id);
			if (repo == null)
				InjecterUtil.onErrorException(null, type);
			return repo;
		}
		repo = getRepoStrInDb(id);
		InjecterUtil.onErrorException(repo, type);
		return repo;
	}
	
	private Repository getRepoStrInDb(String id) throws SQLException{
		RepoDbAction action = null;
		try {
			action = DbConnFac.getInstance().getRepoDbAction();
			return action.getRepoStr(id);
		} finally {
			DbConnFac.staticClose(action);
		}
	}
	
	public void addRepoStr(Repository repo, int put, int get) throws SQLException, InterruptedException {
		RepoDbAction action = null;
		try {
			if(BaseProperty.getInstance().inMem){
				Repository temp = repoMap.get(repo.getId());
				SubRepoStr subRepoStr = repo.getPutOrderList().get(0);
				temp.getPutOrderList().put(put, subRepoStr);
				temp.getGetOrderList().put(get, subRepoStr);
				repoMap.put(repo.getId(), temp);
			}
			action = DbConnFac.getInstance().getRepoDbAction();
			action.addRepoStr(repo, put, get);
			action.commits();
		} catch (SQLException e) {
			if(BaseProperty.getInstance().inMem){
				Repository temp = repoMap.get(repo.getId());
				temp.getPutOrderList().remove(put);
				temp.getGetOrderList().remove(get);
				repoMap.put(repo.getId(), temp);
			}
			DbConnFac.staticRollBack(action);
			throw e;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public void delRepoStr(Repository repo) throws SQLException, InterruptedException {
		RepoDbAction action = null;
		int rollbackPut = 0;
		int rollbackGet = 0;
		SubRepoStr rollbackId = null;
		try {
			if(BaseProperty.getInstance().inMem){
				Repository temp = repoMap.get(repo.getId());
				rollbackId = repo.getPutOrderList().get(0);
				for(int key : temp.getPutOrderList().keySet()){
					SubRepoStr subRepoStr = temp.getPutOrderList().get(key);
					if(subRepoStr.getStrId().equals(rollbackId.getStrId())){
						temp.getPutOrderList().remove(key);
						rollbackPut = key;
					}
				}
				for(int key : temp.getGetOrderList().keySet()){
					SubRepoStr subRepoStr =  temp.getGetOrderList().get(key);
					if(subRepoStr.getStrId().equals(rollbackId.getStrId())){
						temp.getGetOrderList().remove(key);
						rollbackGet = key;
					}
				}
				repoMap.put(repo.getId(), temp);
			}
			action = DbConnFac.getInstance().getRepoDbAction();
			action.delRepoStr(repo);
			action.commits();
		} catch (SQLException e) {
			if(BaseProperty.getInstance().inMem){
				Repository temp = repoMap.get(repo.getId());
				temp.getGetOrderList().put(rollbackGet, rollbackId);
				temp.getPutOrderList().put(rollbackPut, rollbackId);
				repoMap.put(repo.getId(), temp);
			}
			DbConnFac.staticRollBack(action);
			throw e;
		} finally {
			DbConnFac.staticClose(action);
		}

	}

	public Repository findRepo(String strId,OnErrorType type) throws SQLException {
		RepoDbAction action = null;
		Repository repository = null;
		try {
			if(BaseProperty.getInstance().inMem){
				for(Repository repo : repoMap.values()){
					for(Integer i : repo.getGetOrderList().keySet()){
						SubRepoStr subRepoStr = repo.getGetOrderList().get(i);
						if(subRepoStr.getStrId().equals(strId)) {
							repository = repo;
							break;
						}
					}
				}
				InjecterUtil.onErrorException(repository, type);
				return repository;
			}
			action = DbConnFac.getInstance().getRepoDbAction();
			repository = action.findRepo(strId); 
			InjecterUtil.onErrorException(repository, type);
			return repository;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public void addRepoPipe(String id, SubRepoPipe subRepoPipe) throws SQLException, InterruptedException {
		RepoDbAction action = null;
		 Repository rollback = null;
		 try {
			 if(BaseProperty.getInstance().inMem){
				 rollback = repoMap.get(id);
				 rollback.getPipeMap().put(subRepoPipe.getOrder(), subRepoPipe);
				 repoMap.put(id, rollback);
			 }
			action = DbConnFac.getInstance().getRepoDbAction();
			action.addRepoPipe(id, subRepoPipe.getPipeId(), subRepoPipe.getOrder());
			action.commits();
		} catch (SQLException e) {
			if(BaseProperty.getInstance().inMem){
				rollback.getPipeMap().remove(subRepoPipe.getOrder());
				repoMap.put(id, rollback);
			}
			DbConnFac.staticRollBack(action);
			throw e;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public void delRepoPipe(String id, String pipeKey, int order) throws InterruptedException, SQLException {
		RepoDbAction action = null;
		 Repository rollback = null;
		 int rollbackOrder= 0;
		 try {
			 if(BaseProperty.getInstance().inMem){
				 rollback = repoMap.get(id);
				 for(int key : rollback.getPipeMap().keySet()){
					 SubRepoPipe subRepoPipe = rollback.getPipeMap().get(key);
					 if(subRepoPipe.getPipeId().equals(pipeKey) && (subRepoPipe.getOrder() == order)){
						 rollbackOrder = key;
						 rollback.getPipeMap().remove(key);
					 }
				 }
				 repoMap.put(id, rollback);
			 }
			 action = DbConnFac.getInstance().getRepoDbAction();
			 action.delRepoPipe(id,pipeKey);
			 action.commits();
		} catch (SQLException e) {
			if(BaseProperty.getInstance().inMem){
				SubRepoPipe subRepoPipe = new SubRepoPipe();
				subRepoPipe.setOrder(rollbackOrder);
				subRepoPipe.setPipeId(pipeKey);
				rollback.getPipeMap().put(rollbackOrder, subRepoPipe);
				repoMap.put(id, rollback);
			}
			DbConnFac.staticRollBack(action);
			throw e;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public Map<Integer, SubRepoPipe> getRepoPipe(String id,OnErrorType type) throws SQLException {
		Map<Integer, SubRepoPipe> map;
		if (BaseProperty.getInstance().inMem) {
			Repository repo = repoMap.get(id);
			if(repo == null || repo.getPipeMap().isEmpty())
				InjecterUtil.onErrorException(null, type);
			return repo.getPipeMap();
		}
		map = getRepoPipeInDB(id);
		InjecterUtil.onErrorException(map, type);
		return map;
	}
	
	private Map<Integer, SubRepoPipe> getRepoPipeInDB(String id) throws SQLException{
		RepoDbAction action = null;
		try {
			action = DbConnFac.getInstance().getRepoDbAction();
			return action.getRepoPipe(id);
		}
		finally{
			DbConnFac.staticClose(action);
		}
	}
}
