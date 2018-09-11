package yukcommon.model;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import yukcommon.exception.NotNullAllowedException;
import yukcommon.exception.WrongOperationException;
import yukcommon.model.subrepo.SubRepoPipe;
import yukcommon.model.subrepo.SubRepoStr;

public class Repository extends AbsModel{
	private String id = "";
	private String name = "";
	
	private Map<Integer,SubRepoStr> putOrderList = new ConcurrentSkipListMap<Integer, SubRepoStr>();
	private Map<Integer,SubRepoStr> getOrderList = new ConcurrentSkipListMap<Integer, SubRepoStr>();

	private Map<Integer,SubRepoPipe> pipeMap = new ConcurrentSkipListMap<Integer,SubRepoPipe>();

	public String getId() {
		if(id.isEmpty())
			throw new WrongOperationException("repository id is empty");
		return id;
	}

	public void setId(String id) {
		if(id == null || id.isEmpty())
			throw new NotNullAllowedException("repository id can't be null");
		this.id = id;
	}

	public String getName() {
		if(name.isEmpty())
			throw new WrongOperationException("repository name is empty");
		return name;
	}

	public void setName(String name) {
		if(name == null || name.isEmpty())
			throw new NotNullAllowedException("repository name can't be null");
		this.name = name;
	}

	public Map<Integer, SubRepoStr> getPutOrderList() {
		return putOrderList;
	}

	public void setPutOrderList(Map<Integer, SubRepoStr> putOrderList) {
		if(putOrderList == null)
			throw new NotNullAllowedException("repository putOrderList can't be null");
		this.putOrderList = putOrderList;
	}

	public Map<Integer, SubRepoStr> getGetOrderList() {
		return getOrderList;
	}

	public void setGetOrderList(Map<Integer, SubRepoStr> getOrderList) {
		if(getOrderList == null)
			throw new NotNullAllowedException("repository getOrderList can't be null");
		this.getOrderList = getOrderList;
	}

	public Map<Integer, SubRepoPipe> getPipeMap() {
		return pipeMap;
	}

	public void setPipeMap(Map<Integer, SubRepoPipe> pipeMap) {
		if(pipeMap == null)
			throw new NotNullAllowedException("repository pipe can't be null");
		this.pipeMap = pipeMap;
	}
	
	@Override
	public String toString() {		
		return "Current Repository Data: id = " + id + ".name=" + name;
	}
	
}
