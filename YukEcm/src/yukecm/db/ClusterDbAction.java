package yukecm.db;

import java.sql.SQLException;
import java.util.List;

import yukcommon.model.Cluster;

public interface ClusterDbAction extends AbsDbAction{

	Cluster init(int myApNum) throws SQLException;

	void updClusterState(String address, boolean state) throws SQLException;

	void addClusterState(int apNum, String address, boolean state) throws SQLException;

	void removeClusterState(int apNum) throws SQLException;

	List<Cluster> getLiveList() throws SQLException;

	List<Cluster> getList() throws SQLException;

	Cluster getCluster(String address) throws SQLException;

	Cluster getClusterByApnum(int apNum) throws SQLException;

}