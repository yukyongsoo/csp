package yukecm.db;

import java.sql.SQLException;
import java.util.List;

import yukcommon.dic.type.RuleType;
import yukcommon.model.WorkingGroup;

public interface WorkingDbAction extends AbsDbAction{

	void addWork(WorkingGroup work) throws SQLException;

	void updWork(WorkingGroup work) throws SQLException;

	void delWork(WorkingGroup work) throws SQLException;

	WorkingGroup getWorking(String id) throws SQLException;

	WorkingGroup getWorkingByName(String name) throws SQLException;

	void addWorkRule(String id, String ruleid, RuleType type) throws SQLException;

	void delWorkRule(String id, String ruleid) throws SQLException;

	WorkingGroup getWorkRule(String workingId) throws SQLException;

	List<WorkingGroup> getWorkList() throws SQLException;

}