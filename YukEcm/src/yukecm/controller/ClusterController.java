package yukecm.controller;

import java.sql.SQLException;
import java.util.List;

import yukcommon.dic.type.OnErrorType;
import yukcommon.model.Cluster;
import yukecm.injecter.cluster.ClusterInjector;

public class ClusterController {
	private static class LazyHolder {
	    private static final ClusterController state = new ClusterController();
	}

	public static ClusterController getInstance(){
		return LazyHolder.state;
	}

	private ClusterController() {

	}

	public void init(int myApNum) throws SQLException, InterruptedException {
		ClusterInjector.getInstance().init(myApNum);
	}

	//you need change by apnum, address wiil be dup
	public void updClusterState(String address, boolean state) throws SQLException, InterruptedException {
		ClusterInjector.getInstance().getCluster(address,OnErrorType.NOTEXIST);			
		ClusterInjector.getInstance().updClusterState(address, state);
	}

	public void addClusterState(int apNum, String address,boolean state) throws SQLException, InterruptedException{
		ClusterInjector.getInstance().getClusterByApnum(apNum,OnErrorType.EXIST);			
		ClusterInjector.getInstance().addClusterState(apNum, address, state);
	}

	public void removeClusterState(int apNum) throws SQLException, InterruptedException {
		ClusterInjector.getInstance().getClusterByApnum(apNum,OnErrorType.NOTEXIST); 
		ClusterInjector.getInstance().removeClusterState(apNum);
	}

	public List<Cluster> getLiveList() throws SQLException {
		return ClusterInjector.getInstance().getLiveList();
	}
	
	public List<Cluster> getList() throws SQLException {
		return ClusterInjector.getInstance().getList();
	}
	
	public Cluster getCluster(String address) throws SQLException {
		return  ClusterInjector.getInstance().getCluster(address,OnErrorType.NONE);
	}
	
	public Cluster getCluster(int apNum) throws SQLException {
		return ClusterInjector.getInstance().getClusterByApnum(apNum,OnErrorType.NONE);
	}

	public Cluster getMine() throws SQLException {
		return ClusterInjector.getInstance().getMine(OnErrorType.NONE);
	}
}
