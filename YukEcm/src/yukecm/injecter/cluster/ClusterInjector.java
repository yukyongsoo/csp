package yukecm.injecter.cluster;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import yukcommon.dic.type.OnErrorType;
import yukcommon.model.Cluster;
import yukecm.cache.Cache;
import yukecm.cache.YLC;
import yukecm.config.BaseProperty;
import yukecm.db.ClusterDbAction;
import yukecm.db.DbConnFac;
import yukecm.injecter.InjecterUtil;

public class ClusterInjector {
	private static class LazyHolder {
		private static final ClusterInjector INSTANCE;
		static {
			try {
				INSTANCE = new ClusterInjector();
			} catch (Exception e) {
				throw new ExceptionInInitializerError(e);
			} 
		}
	}

	public static ClusterInjector getInstance() {
		return LazyHolder.INSTANCE;
	}
	
	private Cache<String, Cluster> clusterCache;

	private ClusterInjector() throws SQLException, InterruptedException {
		if(BaseProperty.getInstance().inMem){
			clusterCache = YLC.makeCache("CLUSTER", new ClusterJson(),new ClusterInjectorHandler());
			initCluster();
		}
	}
	
	private void initCluster() throws SQLException,   InterruptedException {
		List<Cluster> clusterList = getList();
		for(Cluster cluster : clusterList)
			clusterCache.put(Integer.toString(cluster.getApNum()), cluster);
	}

	public void init(int myApNum) throws SQLException, InterruptedException {
		ClusterDbAction action = null;
		try {
			action = DbConnFac.getInstance().getClusterDbAction();
			Cluster cluster = action.init(myApNum);
			if(BaseProperty.getInstance().inMem && cluster != null) {
				clusterCache.put(Integer.toString(cluster.getApNum()), cluster);
			}
		}
		finally {
			DbConnFac.staticClose(action);
		}
	}
	

	public void updClusterState(String address, boolean state) throws SQLException, InterruptedException {
		ClusterDbAction action = null;
		Cluster rollback = null;
		String oldAddress = "";
		boolean oldState = false;
		try {
			if(BaseProperty.getInstance().inMem){
				rollback = getCluster(address,OnErrorType.NOTEXIST);
				oldAddress = rollback.getAddress();
				oldState = rollback.isState();
				rollback.setAddress(address);
				rollback.setState(state);
				clusterCache.put(Integer.toString(rollback.getApNum()), rollback);
			}
			action = DbConnFac.getInstance().getClusterDbAction();
			action.updClusterState(address, state);
			action.commits();
		} catch (SQLException e) {
			if(BaseProperty.getInstance().inMem) {
				rollback.setAddress(oldAddress);
				rollback.setState(oldState);
				clusterCache.put(Integer.toString(rollback.getApNum()), rollback);
			}
			DbConnFac.staticRollBack(action);
			throw e;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public void addClusterState(int apNum, String address,boolean state) throws SQLException, InterruptedException{
		ClusterDbAction action = null;
		Cluster cluster = new Cluster();
		cluster.setApNum(apNum);
		cluster.setAddress(address);
		cluster.setState(state);
		try {
			if (BaseProperty.getInstance().inMem)
				clusterCache.put(Integer.toString(cluster.getApNum()), cluster);
			action = DbConnFac.getInstance().getClusterDbAction();
			action.addClusterState(apNum, address, state);
			action.commits();
		} catch (SQLException e) {
			if (BaseProperty.getInstance().inMem)
				clusterCache.remove(Integer.toString(cluster.getApNum()));
			DbConnFac.staticRollBack(action);
			throw e;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public void removeClusterState(int apNum) throws SQLException, InterruptedException{
		ClusterDbAction action = null;
		Cluster rollback = null;
		try {
			if (BaseProperty.getInstance().inMem)
				rollback = clusterCache.remove(Integer.toString(apNum));
			action = DbConnFac.getInstance().getClusterDbAction();
			action.removeClusterState(apNum);
			action.commits();
		} catch (SQLException e) {
			if (BaseProperty.getInstance().inMem)
				clusterCache.put(Integer.toString(rollback.getApNum()), rollback);
			DbConnFac.staticRollBack(action);
			throw e;
		} finally {
			DbConnFac.staticClose(action);
		}
	}


	public List<Cluster> getLiveList() throws SQLException {
		ClusterDbAction action = null;
		try {
			if (BaseProperty.getInstance().inMem) {
				List<Cluster> list = new ArrayList<Cluster>();
				for(Cluster cluster : clusterCache.values()) {
					if(cluster.isState())
						list.add(cluster);
				}
				return list;
			}
			action = DbConnFac.getInstance().getClusterDbAction();
			return action.getLiveList();
		} finally {
			DbConnFac.staticClose(action);
		}
	}
	
	public List<Cluster> getList() throws SQLException {
		ClusterDbAction action = null;
		try {
			action = DbConnFac.getInstance().getClusterDbAction();
			return action.getList();
		} finally {
			DbConnFac.staticClose(action);
		}
	}
	
	public Cluster getCluster(String address,OnErrorType type) throws SQLException {
		Cluster cluster = null;
		if (BaseProperty.getInstance().inMem) {
			for (Cluster temp : clusterCache.values()) {
				if (temp.getAddress().equals(address)) {
					cluster = temp;
					break;
				}
			}
			InjecterUtil.onErrorException(cluster, type);
			return cluster;
		}
		cluster = getClusterInDb(address);
		InjecterUtil.onErrorException(cluster, type);
		return cluster;
	}
	
	private Cluster getClusterInDb(String address) throws SQLException {
		ClusterDbAction action = null;
		try {
			action = DbConnFac.getInstance().getClusterDbAction();
			return action.getCluster(address);
		} finally {
			DbConnFac.staticClose(action);
		}
	}
	
	public Cluster getMine(OnErrorType type) throws SQLException{
		return getClusterByApnum(BaseProperty.getInstance().apNum,type);
	}

	public Cluster getClusterByApnum(int apNum, OnErrorType type) throws SQLException {
		ClusterDbAction action = null;
		Cluster cluster = null;
		try {
			if (BaseProperty.getInstance().inMem) {
				List<Cluster> list = getList();
				for (Cluster model : list) {
					if (model.getApNum() == apNum) {
						cluster = model;
						break;
					}
				}
				InjecterUtil.onErrorException(cluster, type);
				return cluster;
			}
			action = DbConnFac.getInstance().getClusterDbAction();
			cluster = action.getClusterByApnum(apNum);
			InjecterUtil.onErrorException(cluster, type);
			return cluster;
		}
		finally {
			DbConnFac.staticClose(action);
		}
	}
}
