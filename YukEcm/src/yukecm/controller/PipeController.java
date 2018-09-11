package yukecm.controller;

import java.sql.SQLException;
import java.util.List;

import yukcommon.dic.type.OnErrorType;
import yukcommon.exception.NotSupportException;
import yukcommon.model.Pipe;
import yukcommon.model.Repository;
import yukcommon.model.subrepo.SubRepoPipe;
import yukecm.etc.EcmUtil;
import yukecm.injecter.pipe.PipeInjector;
import yukecm.injecter.repository.RepositoryInjector;

public class PipeController {
	
	private static class LazyHolder {
	    private static final PipeController pipe = new PipeController();
	}

	public static PipeController getInstance(){
		return LazyHolder.pipe;
	}

	private PipeController(){

	}

	public Pipe getPipe(String pipeId) throws SQLException, InterruptedException {
		return PipeInjector.getInstance().getPipe(pipeId,OnErrorType.NONE);
	}

	public String addPipe(Pipe pipe) throws SQLException, InterruptedException   {
		EcmUtil.setPipeAdaptor(pipe);
		pipe.setId(EcmUtil.getId()); 
		PipeInjector.getInstance().addPipe(pipe);
		return pipe.getId();
	}

	public void delPipe(Pipe pipe) throws SQLException,InterruptedException   {
		PipeInjector.getInstance().getPipe(pipe.getId(), OnErrorType.NOTEXIST);
		List<Repository> repoList = RepositoryInjector.getInstance().getRepoList();
		for(Repository repo : repoList){
			RepositoryController.getInstance().getRepoPipeLists(repo);
			for(SubRepoPipe subPipe : repo.getPipeMap().values()){
				if(subPipe.getPipeId().equals(pipe.getId()))
					throw new NotSupportException("Repository id :" + repo.getId() + " has this pipe. can't not delete");
			}
		}
		PipeInjector.getInstance().delPipe(pipe);
	}


	public void updPipe(Pipe pipe) throws SQLException, InterruptedException  {
		PipeInjector.getInstance().getPipe(pipe.getId(), OnErrorType.NOTEXIST);
		EcmUtil.setPipeAdaptor(pipe);
		PipeInjector.getInstance().updPipe(pipe);
	}

	public List<Pipe> getPipeList() throws SQLException, InterruptedException {
		return PipeInjector.getInstance().getPipeList();
	}
}
