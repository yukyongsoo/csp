package yukecm.handler;

import java.util.List;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import yukcommon.dic.EtcDic;
import yukcommon.dic.UriDic;
import yukcommon.model.Cluster;
import yukcommon.model.User;
import yukcommon.net.AbsNetHandler;
import yukcommon.util.JsonUtil;
import yukecm.controller.ClusterController;
import yukecm.controller.SecureController;

public class ClusterHandler extends AbsNetHandler{
	@Override
	public void handleImpl(HttpRequest request, HttpResponse response, String uri,User user) throws Exception {
		String json = request.getFirstHeader(EtcDic.CLUSTER).getValue();
		Cluster cluster = JsonUtil.fromJson(json, Cluster.class);
		if (uri.equals(UriDic.ADDCLUSTER)) {
			ClusterController.getInstance().addClusterState(cluster.getApNum(), cluster.getAddress(), cluster.isState());
		}
		else if (uri.equals(UriDic.DELCLUSTER)) {
			ClusterController.getInstance().removeClusterState(cluster.getApNum());
		}
		else if (uri.equals(UriDic.GETCLUSTER)) {
			List<Cluster> liveList = ClusterController.getInstance().getList();
			response.addHeader(EtcDic.CLUSTER, JsonUtil.toJson(liveList));
		}
	}

	@Override
	public User preCheck(HttpRequest request, HttpResponse response, String uri) throws Exception {
		User user = SecureController.getInstance().checkUser(request, response, uri);
		SecureController.getInstance().checkSystemPermission(request,response,user,uri);
		return user;
	}
}
