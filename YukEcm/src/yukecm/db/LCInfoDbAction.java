package yukecm.db;

import java.sql.SQLException;

import yukcommon.model.lifecycle.LifeCycleInfo;

public interface LCInfoDbAction extends AbsDbAction{

	void addLifeCycleInfo(LifeCycleInfo info) throws SQLException;

	void delLifeCycleInfo(String id) throws SQLException;

	LifeCycleInfo getLifeCycleInfo(String id) throws SQLException;

	void updLifeCycleInfo(LifeCycleInfo info) throws SQLException;

	void updLifeCycleInfoAddData(LifeCycleInfo info) throws SQLException;

	long updLifeCycleInfoRowNum(LifeCycleInfo info) throws SQLException;

}