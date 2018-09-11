package yukecm.db;

import java.sql.SQLException;
import java.util.List;

import yukcommon.model.lifecycle.LifeCycleSetting;

public interface LCSettingDbAction extends AbsDbAction{

	//iLcId,iLcName,iLCType,iWorkId,iStartCron,iEndCron,iStartRange,iEndRange,iTcount,iLoopBack,iDbUpdate
	void addLifeCycleSch(LifeCycleSetting setting) throws SQLException;

	void delLifeCycleSch(String migId) throws SQLException;

	LifeCycleSetting getLifeCycleSch(String id) throws SQLException;

	List<LifeCycleSetting> getLifeCycleList() throws SQLException;

}