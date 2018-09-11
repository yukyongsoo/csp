package yukecmapi;

import java.util.List;

import yukcommon.dic.UriDic;
import yukcommon.model.Cluster;

public class ClusterApi extends AbsApi{
	public ClusterApi(EcmConnection conn) {
		super(conn);
	}

	public void addCluster(int apNum, String address,boolean state) throws Exception{
		ClusterApiImpl clusterApi = EcmApiFactory.getClusterApi(conn,UriDic.ADDCLUSTER);
		clusterApi.addCluster(apNum, address,state);
	}
	
	public void removeCluster(int apNum) throws Exception{
		ClusterApiImpl clusterApi = EcmApiFactory.getClusterApi(conn,UriDic.DELCLUSTER);
		clusterApi.removeCluster(apNum);
	}
	
	public List<Cluster> getCluster() throws Exception{
		ClusterApiImpl clusterApi = EcmApiFactory.getClusterApi(conn,UriDic.GETCLUSTER);
		return clusterApi.getCluster();
	}
}
