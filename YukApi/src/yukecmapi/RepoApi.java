package yukecmapi;

import java.util.List;

import yukcommon.dic.UriDic;
import yukcommon.model.Repository;
import yukcommon.model.subrepo.SubRepoPipe;

public class RepoApi extends AbsApi{
	public RepoApi(EcmConnection conn) {
		super(conn);
	}

	public List<Repository> getRepository() throws Exception {
		RepoApiImpl impl = EcmApiFactory.getRepoApi(conn,UriDic.GETREPOSITORY);
		return impl.getRepository();
	}
	
	public String addRepository(String name) throws Exception{
		RepoApiImpl impl = EcmApiFactory.getRepoApi(conn,UriDic.ADDREPOSITORY);
		return impl.addRepository(name);
	}

	public void delRepository(String id) throws Exception{
		RepoApiImpl impl = EcmApiFactory.getRepoApi(conn,UriDic.DELREPOSITORY);
		impl.delRepository(id);
	}

	public void updRepository(String id, String name) throws Exception{
		RepoApiImpl impl = EcmApiFactory.getRepoApi(conn,UriDic.UPDREPOSITORY);
		impl.updRepository(id, name);
	}
	
	public Repository getRepoStr(String repoId) throws Exception{
		RepoApiImpl impl = EcmApiFactory.getRepoApi(conn,UriDic.GETREPOSTR);
		return impl.getRepoStr(repoId);
	}

	public Repository addRepoStr(String repoId, String strId) throws Exception{
		RepoApiImpl impl = EcmApiFactory.getRepoApi(conn,UriDic.ADDREPOSTR);
		return impl.addRepoStr(repoId, strId);
	}

	public void delRepoStr(String repoId, String strId) throws Exception{
		RepoApiImpl impl = EcmApiFactory.getRepoApi(conn,UriDic.DELREPOSTR);
		impl.delRepoStr(repoId, strId);
	}
	
	public Repository getRepoPipe(String repoId) throws Exception{
		RepoApiImpl impl = EcmApiFactory.getRepoApi(conn,UriDic.GETREPOPIPE);
		return impl.getRepoPipe(repoId);
	}

	public Repository addRepoPipe(String repoId, String pipeId) throws Exception{
		RepoApiImpl impl = EcmApiFactory.getRepoApi(conn,UriDic.ADDREPOPIPE);
		return impl.addRepoPipe(repoId, pipeId);
	}

	public void delRepoPipe(String repoId, SubRepoPipe subRepoPipe) throws Exception{
		RepoApiImpl impl = EcmApiFactory.getRepoApi(conn,UriDic.DELREPOPIPE);
		impl.delRepoPipe(repoId, subRepoPipe);
	}
	
	
}
