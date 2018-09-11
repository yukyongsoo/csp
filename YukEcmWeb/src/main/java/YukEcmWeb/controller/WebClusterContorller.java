package YukEcmWeb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import YukEcmWeb.SessionBean;
import yukcommon.dic.EtcDic;
import yukcommon.dic.UriDic;
import yukcommon.model.Cluster;
import yukcommon.util.JsonUtil;
import yukecmapi.ClusterApi;


@RestController
public class WebClusterContorller {
	@Autowired(required = true)
    private SessionBean session;
	
	@RequestMapping(UriDic.GETCLUSTER)
	public List<Cluster> getCluster() throws Exception {
		ClusterApi api = new ClusterApi(session.getConn());
		return api.getCluster();
	}
	
	@RequestMapping(UriDic.ADDCLUSTER)
	public void addCluster(@RequestHeader(EtcDic.CLUSTER) String json) throws Exception {
		Cluster cluster = JsonUtil.fromJson(json, Cluster.class);
		ClusterApi api = new ClusterApi(session.getConn());
		api.addCluster(cluster.getApNum(), cluster.getAddress(), cluster.isState());
	}
	
	@RequestMapping(UriDic.DELCLUSTER)
	public void delCluster(@RequestHeader(EtcDic.CLUSTER) String json) throws Exception {
		Cluster cluster = JsonUtil.fromJson(json, Cluster.class);
		ClusterApi api = new ClusterApi(session.getConn());
		api.removeCluster(cluster.getApNum());
	}
}
