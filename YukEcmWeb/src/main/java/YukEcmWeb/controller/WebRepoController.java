package YukEcmWeb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import YukEcmWeb.SessionBean;
import yukcommon.dic.EtcDic;
import yukcommon.dic.UriDic;
import yukcommon.model.Repository;
import yukcommon.util.JsonUtil;
import yukecmapi.RepoApi;

@RestController
public class WebRepoController {
	@Autowired(required = true)
    private SessionBean session;
	
	@RequestMapping(UriDic.GETREPOSITORY)
	public List<Repository> getRepository() throws Exception {
		RepoApi api = new RepoApi(session.getConn());
		return api.getRepository();
	}
	
	@RequestMapping(UriDic.ADDREPOSITORY)
	public String addRepository(@RequestHeader(EtcDic.REPOSITORY) String json) throws Exception {
		Repository repo = JsonUtil.fromJson(json, Repository.class);
		RepoApi api = new RepoApi(session.getConn());
		return api.addRepository(repo.getName());
	}
	
	@RequestMapping(UriDic.UPDREPOSITORY)
	public void updRepository(@RequestHeader(EtcDic.REPOSITORY) String json) throws Exception {
		Repository repo = JsonUtil.fromJson(json, Repository.class);
		RepoApi api = new RepoApi(session.getConn());
		api.updRepository(repo.getId(), repo.getName());
	}
	
	@RequestMapping(UriDic.DELREPOSITORY)
	public void delRepository(@RequestHeader(EtcDic.REPOSITORY) String json) throws Exception {
		Repository repo = JsonUtil.fromJson(json, Repository.class);
		RepoApi api = new RepoApi(session.getConn());
		api.delRepository(repo.getId());
	}
	
	@RequestMapping(UriDic.GETREPOSTR)
	public Repository getRepoStr(@RequestHeader(EtcDic.REPOSITORY) String json) throws Exception {
		Repository repo = JsonUtil.fromJson(json, Repository.class);
		RepoApi api = new RepoApi(session.getConn());
		return api.getRepoStr(repo.getId());
	}
	
	@RequestMapping(UriDic.ADDREPOSTR)
	public Repository addRepoStr(@RequestHeader(EtcDic.REPOSITORY) String json) throws Exception {
		Repository repo = JsonUtil.fromJson(json, Repository.class);
		RepoApi api = new RepoApi(session.getConn());
		return api.addRepoStr(repo.getId(), repo.getPutOrderList().get(0).getStrId());
	}
	
	@RequestMapping(UriDic.DELREPOSTR)
	public void delRepoStr(@RequestHeader(EtcDic.REPOSITORY) String json) throws Exception {
		Repository repo = JsonUtil.fromJson(json, Repository.class);
		RepoApi api = new RepoApi(session.getConn());
		api.delRepoStr(repo.getId(), repo.getPutOrderList().get(0).getStrId());
	}
	
	@RequestMapping(UriDic.GETREPOPIPE)
	public Repository getRepoPipe(@RequestHeader(EtcDic.REPOSITORY) String json) throws Exception {
		Repository repo = JsonUtil.fromJson(json, Repository.class);
		RepoApi api = new RepoApi(session.getConn());
		return api.getRepoPipe(repo.getId());
	}
	
	@RequestMapping(UriDic.ADDREPOPIPE)
	public Repository addRepoPipe(@RequestHeader(EtcDic.REPOSITORY) String json) throws Exception {
		Repository repo = JsonUtil.fromJson(json, Repository.class);
		RepoApi api = new RepoApi(session.getConn());
		return api.addRepoPipe(repo.getId(), repo.getPipeMap().get(0).getPipeId());
	}
	
	@RequestMapping(UriDic.DELREPOPIPE)
	public void delRepoPipe(@RequestHeader(EtcDic.REPOSITORY) String json) throws Exception {
		Repository repo = JsonUtil.fromJson(json, Repository.class);
		RepoApi api = new RepoApi(session.getConn());
		api.delRepoPipe(repo.getId(), repo.getPipeMap().get(0));
	}
}
