package yukecm.injecter.group;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import yukcommon.dic.type.OnErrorType;
import yukcommon.model.Group;
import yukecm.cache.Cache;
import yukecm.cache.YLC;
import yukecm.config.BaseProperty;
import yukecm.db.DbConnFac;
import yukecm.db.GroupDbAction;
import yukecm.injecter.InjecterUtil;


public class GroupInjector {
	private static class LazyHolder {
		private static final GroupInjector INSTANCE;
		static {
			try {
				INSTANCE = new GroupInjector();
			} catch (Exception e) {
				throw new ExceptionInInitializerError(e);
			} 
		}
	}

	public static GroupInjector getInstance() {
		return LazyHolder.INSTANCE;
	}
	
	private Cache<String, Group> groupMap;

	private GroupInjector() throws InterruptedException, SQLException {
		if(BaseProperty.getInstance().inMem){
			groupMap = YLC.makeCache("GROUP", new GroupJson());
			initGroup();
		}
	}

	private void initGroup() throws InterruptedException, SQLException {
		List<Group> groupList = getGroupList();
		for(Group group : groupList)
			groupMap.put(group.getId(), group);
	}

	private List<Group> getGroupList() throws SQLException {
		GroupDbAction action = null;
		try {
			action = DbConnFac.getInstance().getGroupDbAction();
			return action.getAllGroupList();
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public Group getGroup(Group group, OnErrorType type) throws SQLException {
		GroupDbAction action = null;
		Group nGroup = null;
		try {
			if (BaseProperty.getInstance().inMem) {
				nGroup = groupMap.get(group.getId()); 
				InjecterUtil.onErrorException(nGroup, type);
				return nGroup;
			}
			action = DbConnFac.getInstance().getGroupDbAction();
			nGroup = action.getGroup(group.getId());
			InjecterUtil.onErrorException(nGroup, type);
			return nGroup;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public void addGroup(Group group) throws SQLException, InterruptedException {
		GroupDbAction action = null;
		try {
			if (BaseProperty.getInstance().inMem)
				groupMap.put(group.getId(), group);
			action = DbConnFac.getInstance().getGroupDbAction();
			action.addGroup(group);
			action.commits();
		} catch (SQLException e) {
			if (BaseProperty.getInstance().inMem)
				groupMap.remove(group.getId());
			DbConnFac.staticRollBack(action);
			throw e;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public void delGroup(Group group) throws SQLException, InterruptedException {
		GroupDbAction action = null;
		Group rollback = null;
		try {
			if (BaseProperty.getInstance().inMem)
				rollback = groupMap.remove(group.getId());
			action = DbConnFac.getInstance().getGroupDbAction();
			action.delGroup(group);
			action.commits();
		} catch (SQLException e) {
			if (BaseProperty.getInstance().inMem)
				groupMap.put(rollback.getId(), rollback);
			DbConnFac.staticRollBack(action);
			throw e;
		} finally {
			DbConnFac.staticClose(action);
		}
		
	}

	public void updGroup(Group group) throws InterruptedException, SQLException {
		GroupDbAction action = null;
		Group rollback = null;
		try {
			if(BaseProperty.getInstance().inMem){
				rollback = groupMap.remove(group.getId());
				groupMap.put(group.getId(), group);
			}
			action = DbConnFac.getInstance().getGroupDbAction();
			action.updGroup(group);
			action.commits();
		} catch (SQLException e) {
			if(BaseProperty.getInstance().inMem)
				groupMap.put(rollback.getId(), rollback);
			DbConnFac.staticRollBack(action);
			throw e;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public List<Group> getGroupList(Group group, OnErrorType type) throws SQLException {
		GroupDbAction action = null;
		List<Group> list = new ArrayList<Group>();
		try {
			if (BaseProperty.getInstance().inMem) {
				for(Group nGroup : groupMap.values()) {
					if(nGroup.getParentId().isEmpty())
						list.add(nGroup);
				}
				InjecterUtil.onErrorListException(list, type);
				return list;
			}
			action = DbConnFac.getInstance().getGroupDbAction();
			list = action.getGroupList();
			InjecterUtil.onErrorListException(list, type);
			return list;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public List<Group> getGroupChildList(Group group, OnErrorType type) throws SQLException {
		GroupDbAction action = null;
		List<Group> list = new ArrayList<Group>();
		try {
			if (BaseProperty.getInstance().inMem) {
				for(Group nGroup : groupMap.values()) {
					if(nGroup.getParentId().equals(group.getId()))
						list.add(nGroup);
				}
				InjecterUtil.onErrorListException(list, type);
				return list;
			}
			action = DbConnFac.getInstance().getGroupDbAction();
			list = action.getGroupChildList(group.getId());
			InjecterUtil.onErrorListException(list, type);
			return list;
		} finally {
			DbConnFac.staticClose(action);
		}
	}
	
}
