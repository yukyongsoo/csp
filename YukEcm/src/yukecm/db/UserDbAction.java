package yukecm.db;

import java.sql.SQLException;
import java.util.List;

import yukcommon.model.User;

public interface UserDbAction extends AbsDbAction{

	User getUser(String userName) throws SQLException;

	void addUser(User user) throws SQLException;

	void delUser(User user) throws SQLException;

	void updUser(User user) throws SQLException;

	List<User> getUserList() throws SQLException;

	List<User> getGroupUser(String parentId) throws SQLException;

}