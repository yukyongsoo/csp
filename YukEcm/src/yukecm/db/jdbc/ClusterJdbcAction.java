package yukecm.db.jdbc;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import yukcommon.model.Cluster;
import yukecm.config.BaseProperty;
import yukecm.db.ClusterDbAction;

public class ClusterJdbcAction extends AbsJdbcAction implements ClusterDbAction{

	protected ClusterJdbcAction() throws SQLException {
		super();
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.ClusterDbAction#init(int)
	 */
	@Override
	public Cluster init(int myApNum) throws SQLException {
		CallableStatement statement = null;
		ResultSet resultSet = null;
		try{
			statement = connection.prepareCall("{call YukGetClusterList(?)}");
			statement.registerOutParameter(1,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(1);
			while(resultSet.next()){
				String address = resultSet.getString(2);
				boolean state = Boolean.parseBoolean(resultSet.getString(3));
				if((resultSet.getInt(1) == myApNum)){
					if(!state)
						updClusterState(address, true);
					Cluster cluster = new Cluster();
					cluster.setAddress(address);
					cluster.setState(true);
					cluster.setApNum(myApNum);
					cluster.setMe(true);
					return cluster;
				}
			}
		}
		finally {
			closeResouce(resultSet, statement);
		}
		return null;
	}
	

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.ClusterDbAction#updClusterState(java.lang.String, boolean)
	 */
	@Override
	public void updClusterState(String address, boolean state) throws SQLException {
		CallableStatement statement = null;
		try{
			statement = connection.prepareCall("{call YukUpdCluster(?,?)}");
			statement.setString(1, address);
			statement.setString(2, Boolean.toString(state));
			statement.execute();
			connection.commit();
		}
		finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.ClusterDbAction#addClusterState(int, java.lang.String, boolean)
	 */
	@Override
	public void addClusterState(int apNum, String address,boolean state) throws SQLException{
		CallableStatement statement = null;
		try{
			statement = connection.prepareCall("{call YukAddCluster(?,?,?)}");
			statement.setInt(1, apNum);
			statement.setString(2, address);
			statement.setString(3, Boolean.toString(state));
			statement.execute();
			connection.commit();
		}
		finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.ClusterDbAction#removeClusterState(int)
	 */
	@Override
	public void removeClusterState(int apNum) throws SQLException{
		CallableStatement statement = null;
		try{
			statement = connection.prepareCall("{call YukDelCluster(?)}");
			statement.setInt(1, apNum);
			statement.execute();
			connection.commit();
		}
		finally {
			closeResouce(null, statement);
		}
	}


	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.ClusterDbAction#getLiveList()
	 */
	@Override
	public List<Cluster> getLiveList() throws SQLException {
		List<Cluster> list = new ArrayList<Cluster>();
		CallableStatement statement = null;
		ResultSet resultSet = null;
		try{
			statement = connection.prepareCall("{call YukGetClusterList(?)}");
			statement.registerOutParameter(1,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(1);
			while(resultSet.next()){
				Cluster model = new Cluster();
				model.setApNum(resultSet.getInt(1)); 
				model.setAddress(resultSet.getString(2)); 
				model.setState(Boolean.parseBoolean(resultSet.getString(3))); 
				if(model.isState()){
					if(BaseProperty.getInstance().apNum == model.getApNum())
						model.setMe(true);
					list.add(model);
				}
			}
		}
		finally {
			closeResouce(resultSet, statement);
		}
		return list;
	}
	
	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.ClusterDbAction#getList()
	 */
	@Override
	public List<Cluster> getList() throws SQLException {
		List<Cluster> list = new ArrayList<Cluster>();
		CallableStatement statement = null;
		ResultSet resultSet = null;
		try{
			statement = connection.prepareCall("{call YukGetClusterList(?)}");
			statement.registerOutParameter(1,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(1);
			while(resultSet.next()){
				Cluster model = new Cluster();
				model.setApNum(resultSet.getInt(1)); 
				model.setAddress(resultSet.getString(2)); 
				model.setState(Boolean.parseBoolean(resultSet.getString(3))); 
				if(BaseProperty.getInstance().apNum == model.getApNum())
					model.setMe(true);
				list.add(model);
			}
		}
		finally {
			closeResouce(resultSet, statement);
		}
		return list;
	}
	
	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.ClusterDbAction#getCluster(java.lang.String)
	 */
	@Override
	public Cluster getCluster(String address) throws SQLException {
		Cluster cluster = null;
		CallableStatement statement = null;
		ResultSet resultSet = null;
		try{
			statement = connection.prepareCall("{call YukGetCluster(?,?)}");
			statement.setString(1, address);
			statement.registerOutParameter(2,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(2);
			while(resultSet.next()){
				cluster = new Cluster();
				cluster.setApNum(resultSet.getInt(1)); 
				cluster.setAddress(resultSet.getString(2));
				cluster.setState(Boolean.parseBoolean(resultSet.getString(3))); 
				if(BaseProperty.getInstance().apNum == cluster.getApNum())
					cluster.setMe(true);
			}
		}
		finally {
			closeResouce(resultSet, statement);
		}
		return cluster;
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.ClusterDbAction#getClusterByApnum(int)
	 */
	@Override
	public Cluster getClusterByApnum(int apNum) throws SQLException {
		Cluster cluster = null;
		CallableStatement statement = null;
		ResultSet resultSet = null;
		try{
			statement = connection.prepareCall("{call YukGetClusterByApnum(?,?)}");
			statement.setInt(1, apNum);
			statement.registerOutParameter(2,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(2);
			while(resultSet.next()){
				cluster = new Cluster();
				cluster.setApNum(resultSet.getInt(1)); 
				cluster.setAddress(resultSet.getString(2)); 
				cluster.setState(Boolean.parseBoolean(resultSet.getString(3))); 
				if(BaseProperty.getInstance().apNum == cluster.getApNum())
					cluster.setMe(true);
			}
		}
		finally {
			closeResouce(resultSet, statement);
		}
		return cluster;
	}
}
