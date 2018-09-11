package yukecmapi;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import yukcommon.dic.EtcDic;
import yukcommon.model.Repository;
import yukcommon.model.subrepo.SubRepoPipe;
import yukcommon.model.subrepo.SubRepoStr;
import yukcommon.net.NetClient;
import yukcommon.net.NetWrapper;
import yukcommon.util.JsonUtil;

public class RepoApiImpl extends AbsApiImpl {
	protected RepoApiImpl(NetClient client, NetWrapper wrap) {
		super(client, wrap);
	}

	public String addRepository(String name) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		Repository repo = new Repository();
		repo.setName(name);
		wrap.addHeader(EtcDic.REPOSITORY, JsonUtil.toJson(repo));
		client.excute(wrap);
		return wrap.getHeaderValue(EtcDic.RETID);
	}

	public void delRepository(String id) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		Repository repo = new Repository();
		repo.setId(id);
		wrap.addHeader(EtcDic.REPOSITORY, JsonUtil.toJson(repo));
		client.excute(wrap);
	}

	public void updRepository(String id, String name) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		Repository repo = new Repository();
		repo.setId(id);
		repo.setName(name);
		wrap.addHeader(EtcDic.REPOSITORY, JsonUtil.toJson(repo));
		client.excute(wrap);
	}

	public Repository addRepoStr(String repoId, String strId) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		Repository repo = new Repository();
		repo.setId(repoId);
		SubRepoStr str= new SubRepoStr();
		str.setStrId(strId);
		repo.getPutOrderList().put(0, str);
		wrap.addHeader(EtcDic.REPOSITORY, JsonUtil.toJson(repo));
		client.excute(wrap);
		String json = wrap.getHeaderValue(EtcDic.REPOSITORY);
		return JsonUtil.fromJson(json, Repository.class);
	}

	public void delRepoStr(String repoId, String strId) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		Repository repo = new Repository();
		repo.setId(repoId);
		SubRepoStr str= new SubRepoStr();
		str.setStrId(strId);
		repo.getPutOrderList().put(0, str);
		wrap.addHeader(EtcDic.REPOSITORY, JsonUtil.toJson(repo));
		client.excute(wrap);
	}

	public Repository addRepoPipe(String repoId, String pipeId) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		Repository repo = new Repository();
		repo.setId(repoId);
		SubRepoPipe pipe = new SubRepoPipe();
		pipe.setPipeId(pipeId);
		repo.getPipeMap().put(0, pipe);
		wrap.addHeader(EtcDic.REPOSITORY, JsonUtil.toJson(repo));
		client.excute(wrap);
		String json = wrap.getHeaderValue(EtcDic.REPOSITORY);
		return JsonUtil.fromJson(json, Repository.class);
	}

	public void delRepoPipe(String repoId, SubRepoPipe subRepoPipe) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		Repository repo = new Repository();
		repo.setId(repoId);
		repo.getPipeMap().put(0, subRepoPipe);
		wrap.addHeader(EtcDic.REPOSITORY, JsonUtil.toJson(repo));
		client.excute(wrap);
	}

	public List<Repository> getRepository() throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		Repository repo = new Repository();
		wrap.addHeader(EtcDic.REPOSITORY, JsonUtil.toJson(repo));
		client.excute(wrap);
		String json = wrap.getHeaderValue(EtcDic.REPOSITORY);
		return JsonUtil.fromJson(json, JsonUtil.LISTREPO);
	}

	public Repository getRepoStr(String repoId) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		return getRepo(repoId);
	}

	public Repository getRepoPipe(String repoId) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		return getRepo(repoId);
	}
	
	private Repository getRepo(String repoId) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		Repository repo = new Repository();
		repo.setId(repoId);
		wrap.addHeader(EtcDic.REPOSITORY, JsonUtil.toJson(repo));
		client.excute(wrap);
		String json = wrap.getHeaderValue(EtcDic.REPOSITORY);
		return JsonUtil.fromJson(json, Repository.class);
	}

}
