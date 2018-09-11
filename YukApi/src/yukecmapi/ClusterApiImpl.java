package yukecmapi;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import yukcommon.dic.EtcDic;
import yukcommon.model.Cluster;
import yukcommon.net.NetClient;
import yukcommon.net.NetWrapper;
import yukcommon.util.JsonUtil;

public class ClusterApiImpl extends AbsApiImpl{
	protected ClusterApiImpl(NetClient client, NetWrapper wrap) {
		super(client, wrap);
	}

	public void addCluster(int apNum, String address, boolean state)  throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		Cluster cluster = new Cluster();
		cluster.setApNum(apNum);
		cluster.setAddress(address);
		cluster.setState(state);
		wrap.addHeader(EtcDic.CLUSTER, JsonUtil.toJson(cluster));
		client.excute(wrap);
	}

	public void removeCluster(int apNum) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		Cluster cluster = new Cluster();
		cluster.setApNum(apNum);
		wrap.addHeader(EtcDic.CLUSTER, JsonUtil.toJson(cluster));
		client.excute(wrap);
	}

	public List<Cluster> getCluster() throws URISyntaxException, IOException, InterruptedException, ExecutionException{
		Cluster cluster = new Cluster();
		wrap.addHeader(EtcDic.CLUSTER, JsonUtil.toJson(cluster));
		client.excute(wrap);
		String json = wrap.getHeaderValue(EtcDic.CLUSTER);
		return JsonUtil.fromJson(json, JsonUtil.LISTCLUSTER);
	}
}
