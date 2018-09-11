package yukecm.handler;

import java.util.List;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import yukcommon.dic.EtcDic;
import yukcommon.dic.UriDic;
import yukcommon.model.Repository;
import yukcommon.model.User;
import yukcommon.net.AbsNetHandler;
import yukcommon.util.JsonUtil;
import yukecm.controller.RepositoryController;
import yukecm.controller.SecureController;

public class RepositoryHandler extends AbsNetHandler{
	@Override
	public void handleImpl(HttpRequest request, HttpResponse response, String uri,User user) throws Exception {
		String json = request.getFirstHeader(EtcDic.REPOSITORY).getValue();
		Repository repo = JsonUtil.fromJson(json, Repository.class);
		if (uri.equals(UriDic.ADDREPOSITORY)) {
			String id = RepositoryController.getInstance().addRepo(repo);
			response.addHeader(EtcDic.RETID, id);
		}
		else if (uri.equals(UriDic.DELREPOSITORY)) {
			RepositoryController.getInstance().delRepo(repo);
		}
		else if (uri.equals(UriDic.UPDREPOSITORY)) {
			RepositoryController.getInstance().updRepo(repo);
		}
		else if (uri.equals(UriDic.GETREPOSITORY)) {
			List<Repository> repoList = RepositoryController.getInstance().getRepoList();
			response.addHeader(EtcDic.REPOSITORY, JsonUtil.toJson(repoList));
		}
		else if (uri.equals(UriDic.ADDREPOSTR)) {
			Repository nRepo = RepositoryController.getInstance().addRepoStr(repo);
			String nJson = JsonUtil.toJson(nRepo);
			response.addHeader(EtcDic.REPOSITORY, nJson);
		}
		else if (uri.equals(UriDic.DELREPOSTR)) {
			RepositoryController.getInstance().delRepoStr(repo);
		}
		else if (uri.equals(UriDic.GETREPOSTR)) {
			Repository nRepo = RepositoryController.getInstance().getRepoStr(repo);
			String nJson = JsonUtil.toJson(nRepo);
			response.addHeader(EtcDic.REPOSITORY, nJson);
		}
		else if (uri.equals(UriDic.ADDREPOPIPE)) {
			Repository nRepo = RepositoryController.getInstance().addRepoPipe(repo);
			String nJson = JsonUtil.toJson(nRepo);
			response.addHeader(EtcDic.REPOSITORY, nJson);
		}
		else if (uri.equals(UriDic.DELREPOPIPE)) {
			RepositoryController.getInstance().delRepoPipe(repo);
		}
		else if (uri.equals(UriDic.GETREPOPIPE)) {
			RepositoryController.getInstance().getRepoPipeLists(repo);
			response.addHeader(EtcDic.REPOSITORY, JsonUtil.toJson(repo));
		}
	}

	@Override
	public User preCheck(HttpRequest request, HttpResponse response, String uri) throws Exception {
		User user = SecureController.getInstance().checkUser(request, response, uri);
		SecureController.getInstance().checkSystemPermission(request,response,user,uri);
		return user;
	}
}
