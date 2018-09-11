package yukecm.injecter.user;

import java.sql.SQLException;
import java.util.List;

import yukcommon.dic.type.OnErrorType;
import yukcommon.model.User;
import yukecm.cache.Cache;
import yukecm.cache.YLC;
import yukecm.config.BaseProperty;
import yukecm.db.DbConnFac;
import yukecm.db.UserDbAction;
import yukecm.injecter.InjecterUtil;

public class UserInjector {
	private static UserInjector injecter;

	public static UserInjector getInstance() throws InterruptedException, SQLException{
		if(injecter == null)
			injecter = new UserInjector();
		return injecter;
	}

	private Cache<String, User> userMap;

	private UserInjector() throws InterruptedException, SQLException{
		if(BaseProperty.getInstance().inMem){
			userMap = YLC.makeCache("USER", new UserJson());
			initUser();
		}
	}

	public void initUser() throws InterruptedException,  SQLException {
		List<User> list = getUserList(OnErrorType.NOTEXIST);
		for (User user : list) {
			userMap.put(user.getId(), user);
		}
	}

	public List<User> getUserList(OnErrorType type) throws SQLException {
		UserDbAction action = null;
		List<User> list;
		try {
			action = DbConnFac.getInstance().getUserDbAction();
			list = action.getUserList();
			InjecterUtil.onErrorListException(list, type);
			return list;
		}
		finally {
			DbConnFac.staticClose(action);
		}
	}

	public User getUser(String id, OnErrorType type) throws  SQLException {
		UserDbAction action = null;
		User user;
		try {
			if(BaseProperty.getInstance().inMem) {
				user = userMap.get(id);
				InjecterUtil.onErrorException(user, type);
				return user;
			}
			action = DbConnFac.getInstance().getUserDbAction();
			user = action.getUser(id);
			InjecterUtil.onErrorException(user, type);
			return user;
		}
		finally {
			DbConnFac.staticClose(action);
		}
	}

	public void addUser(User user) throws InterruptedException, SQLException {
		UserDbAction action = null;
		try {
			if(BaseProperty.getInstance().inMem)
				userMap.put(user.getId(), user);
			action = DbConnFac.getInstance().getUserDbAction();
			action.addUser(user);
			action.commits();
		} catch (SQLException e) {
			if(BaseProperty.getInstance().inMem)
				userMap.remove(user.getId());
			DbConnFac.staticRollBack(action);
			throw e;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public void delUser(User user) throws InterruptedException, SQLException {
		UserDbAction action = null;
		User rollback = null;
		try {
			if(BaseProperty.getInstance().inMem)
				rollback = userMap.remove(user.getId());
			action = DbConnFac.getInstance().getUserDbAction();
			action.delUser(user);
			action.commits();
		} catch (SQLException e) {
			if(BaseProperty.getInstance().inMem)
				userMap.put(user.getId(),rollback);
			DbConnFac.staticRollBack(action);
			throw e;
		}  finally {
			DbConnFac.staticClose(action);
		}
	}

	public void updUser(User user) throws InterruptedException, SQLException {
		UserDbAction action = null;
		User rollback = null;
		try {
			if(BaseProperty.getInstance().inMem){
				rollback = userMap.remove(user.getId());
				userMap.put(user.getId(), user);
			}
			action = DbConnFac.getInstance().getUserDbAction();
			action.updUser(user);
			action.commits();
		} catch (SQLException e) {
			if(BaseProperty.getInstance().inMem)
				userMap.put(user.getId(),rollback);
			DbConnFac.staticRollBack(action);
			throw e;
		}  finally {
			DbConnFac.staticClose(action);
		}
	}

	public List<User> getGroupUser(String parentId, OnErrorType type) throws SQLException {
		UserDbAction action = null;
		List<User> list;
		try {
			action = DbConnFac.getInstance().getUserDbAction();
			list = action.getGroupUser(parentId);
			InjecterUtil.onErrorListException(list, type);
			return list;
		}
		finally {
			DbConnFac.staticClose(action);
		}
	}


}
