package yukecm.db;

import java.sql.SQLException;
import java.util.List;

import yukcommon.model.Group;

public interface GroupDbAction extends AbsDbAction{

	List<Group> getGroupList() throws SQLException;

	List<Group> getAllGroupList() throws SQLException;

	Group getGroup(String id) throws SQLException;

	void addGroup(Group group) throws SQLException;

	void delGroup(Group group) throws SQLException;

	void updGroup(Group group) throws SQLException;

	List<Group> getGroupChildList(String id) throws SQLException;

}