package yukecm.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import yukcommon.dic.type.OnErrorType;
import yukcommon.model.Pair;
import yukcommon.model.Repository;
import yukcommon.model.Storage;
import yukcommon.model.subrepo.SubRepoPipe;
import yukecm.etc.EcmUtil;
import yukecm.injecter.pipe.PipeInjector;
import yukecm.injecter.repository.RepositoryInjector;
import yukecm.injecter.storage.StorageInjector;

public class RepositoryController {
	
	private static class LazyHolder {
	    private static final RepositoryController repo = new RepositoryController();
	}

	public static RepositoryController getInstance(){
		return LazyHolder.repo;
	}

	private RepositoryController(){

	}

	public Repository getRepo(String id) throws SQLException, InterruptedException   {
		return RepositoryInjector.getInstance().getRepo(id,OnErrorType.NONE);
	}

	public Repository getRepoByName(String name) throws SQLException,  InterruptedException   {
		return RepositoryInjector.getInstance().getRepoByName(name,OnErrorType.NONE);
	}

	public String addRepo(Repository repo) throws SQLException, InterruptedException  {
		RepositoryInjector.getInstance().getRepoByName(repo.getName(),OnErrorType.EXIST);
		repo.setId(EcmUtil.getId()); 
		RepositoryInjector.getInstance().addRepo(repo);
		return repo.getId();
	}

	public void delRepo(Repository repo) throws SQLException, InterruptedException   {
		RepositoryInjector.getInstance().getRepo(repo.getId(), OnErrorType.NOTEXIST);
		RepositoryInjector.getInstance().delRepo(repo);
	}

	public void updRepo(Repository repo) throws SQLException, InterruptedException   {
		RepositoryInjector.getInstance().getRepo(repo.getId(), OnErrorType.NOTEXIST);
		RepositoryInjector.getInstance().updRepo(repo);
	}

	public List<Repository> getRepoList() throws SQLException, InterruptedException {
		return RepositoryInjector.getInstance().getRepoList();
	}

	public Repository addRepoStr(Repository repo) throws SQLException, InterruptedException  {
		RepositoryInjector.getInstance().getRepo(repo.getId(), OnErrorType.NOTEXIST);
		Repository nRepo = getRepoStr(repo.getId());
		int put = 1;
		int get = 1;
		if(nRepo != null){
			put = EcmUtil.getMax(nRepo.getPutOrderList().keySet(),true);
			get = EcmUtil.getMax(nRepo.getGetOrderList().keySet(),true);
		}
		repo.getPutOrderList().get(0).setPutOrder(put); 
		repo.getPutOrderList().get(0).setGetOrder(get); 
		RepositoryInjector.getInstance().addRepoStr(repo,put,get);
		return repo;
	}

	public void delRepoStr(Repository repo) throws SQLException, InterruptedException   {
		RepositoryInjector.getInstance().getRepo(repo.getId(), OnErrorType.NOTEXIST);
		RepositoryInjector.getInstance().delRepoStr(repo);
	}

	public Repository getRepoStr(Repository repo) throws SQLException, InterruptedException   {
		return RepositoryInjector.getInstance().getRepoStr(repo.getId(),OnErrorType.NONE);
	}

	private Repository getRepoStr(String id) throws SQLException,InterruptedException   {
		return RepositoryInjector.getInstance().getRepoStr(id,OnErrorType.NOTEXIST);
	}

	public Pair<Repository, Storage> findRepo(String strId) throws SQLException,InterruptedException {
		Repository findRepo = RepositoryInjector.getInstance().findRepo(strId,OnErrorType.NOTEXIST);
		Storage storage = StorageInjector.getInstance().getStorage(strId,OnErrorType.NOTEXIST);
		if(storage.isUsed()) {
			Pair<Repository, Storage> pair = new Pair<Repository, Storage>();
			pair.set(findRepo, storage);
			return pair;
		}
		return null; 
	}

	public Repository addRepoPipe(Repository repo) throws SQLException, InterruptedException  {
		SubRepoPipe subRepoPipe = repo.getPipeMap().get(0);
		RepositoryInjector.getInstance().getRepo(repo.getId(), OnErrorType.NOTEXIST);
		PipeInjector.getInstance().getPipe(subRepoPipe.getPipeId(), OnErrorType.NOTEXIST);
		Map<Integer, SubRepoPipe> rulePipeList = getRepoPipeList(repo.getId());
		int max = EcmUtil.getMax(rulePipeList.keySet(), true);
		subRepoPipe.setOrder(max);
		RepositoryInjector.getInstance().addRepoPipe(repo.getId(),subRepoPipe);
		return repo;
	}

	public void delRepoPipe(Repository repo) throws SQLException, InterruptedException   {
		RepositoryInjector.getInstance().getRepo(repo.getId(), OnErrorType.NOTEXIST);
		SubRepoPipe subRepoPipe = repo.getPipeMap().get(0);
		PipeInjector.getInstance().getPipe(subRepoPipe.getPipeId(), OnErrorType.NOTEXIST);
		RepositoryInjector.getInstance().delRepoPipe(repo.getId(),subRepoPipe.getPipeId(),subRepoPipe.getOrder());
	}

	public void getRepoPipeLists(Repository repo) throws SQLException,  InterruptedException   {
		RepositoryInjector.getInstance().getRepo(repo.getId(), OnErrorType.NOTEXIST);
		repo.setPipeMap(getRepoPipeList(repo.getId())); 
	}

	private Map<Integer, SubRepoPipe> getRepoPipeList(String id) throws SQLException, InterruptedException  {
		return RepositoryInjector.getInstance().getRepoPipe(id,OnErrorType.NONE);
	}
}
