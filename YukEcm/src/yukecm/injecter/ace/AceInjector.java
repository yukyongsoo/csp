package yukecm.injecter.ace;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import yukcommon.dic.type.OnErrorType;
import yukcommon.model.Ace;
import yukecm.cache.Cache;
import yukecm.cache.YLC;
import yukecm.config.BaseProperty;
import yukecm.db.AceDbAction;
import yukecm.db.DbConnFac;
import yukecm.injecter.InjecterUtil;

public class AceInjector {
	private static class LazyHolder {
		private static final AceInjector INSTANCE;
		static {
			try {
				INSTANCE = new AceInjector();
			} catch (Exception e) {
				throw new ExceptionInInitializerError(e);
			} 
		}
	}

	public static AceInjector getInstance() {
		return LazyHolder.INSTANCE;
	}
	
	//key :: acl , <aceId, ace>
	private Cache<String, Map<String, Ace>> aceMap;

	private AceInjector() throws InterruptedException, SQLException {
		if(BaseProperty.getInstance().inMem){
			aceMap = YLC.makeCache("ACE", new AceJson());
			initAce();
		}
	}

	private void initAce() throws InterruptedException, SQLException {
		List<Ace> list = getAllAceList();
		for(Ace ace : list) {
			Map<String, Ace> map = aceMap.get(ace.getId());
			if(map == null)
				map = new ConcurrentHashMap<String, Ace>();
			map.put(ace.getChildId(), ace);
			aceMap.put(ace.getId(), map);
		}
	}

	private List<Ace> getAllAceList() throws SQLException {
		AceDbAction action = null;
		action = DbConnFac.getInstance().getAceDbAction();
		try {
			return action.getAceAllList();
		} finally {
			action.close();
		}
	}

	public Ace getAce(Ace ace, OnErrorType type) throws SQLException {
		AceDbAction action = null;
		Ace nAce = null;
		try {
			if (BaseProperty.getInstance().inMem) {
				Map<String, Ace> map = aceMap.get(ace.getId());
				InjecterUtil.onErrorException(map, OnErrorType.NOTEXIST);
				nAce = map.get(ace.getChildId());
				InjecterUtil.onErrorException(nAce, type);
				return nAce;
			}
			action = DbConnFac.getInstance().getAceDbAction();
			nAce = action.getAce(ace);
			InjecterUtil.onErrorException(nAce, type);
			return nAce;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public void addAce(Ace ace) throws InterruptedException, SQLException {
		AceDbAction action = null;
		try {
			if (BaseProperty.getInstance().inMem) {
				Map<String, Ace> map = aceMap.get(ace.getId());
				if(map == null)
					map = new ConcurrentHashMap<String, Ace>();
				map.put(ace.getChildId(), ace);
				aceMap.put(ace.getId(), map);
			}
			action = DbConnFac.getInstance().getAceDbAction();
			action.addAce(ace);
			action.commits();
		} catch (SQLException e) {
			if (BaseProperty.getInstance().inMem) {
				Map<String, Ace> map = aceMap.get(ace.getId());
				map.remove(ace.getChildId());
				aceMap.put(ace.getId(), map);
			}
			DbConnFac.staticRollBack(action);
			throw e;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public List<Ace> getAceList(Ace ace, OnErrorType type) throws SQLException {
		AceDbAction action = null;
		List<Ace> list = new ArrayList<Ace>();
		try {
			if (BaseProperty.getInstance().inMem) {
				Map<String, Ace> map = aceMap.get(ace.getId());
				list.addAll(map.values());
				InjecterUtil.onErrorListException(list, type);
				return list;
			}
			action = DbConnFac.getInstance().getAceDbAction();
			list = action.getAceList(ace);
			InjecterUtil.onErrorListException(list, type);
			return list;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public void delAce(Ace ace) throws SQLException, InterruptedException {
		AceDbAction action = null;
		Ace remove = null;
		try {
			if (BaseProperty.getInstance().inMem) {
				Map<String, Ace> map = aceMap.get(ace.getId());
				remove = map.remove(ace.getChildId());
				aceMap.put(ace.getId(), map);
			}
			action = DbConnFac.getInstance().getAceDbAction();
			action.delAce(ace);
			action.commits();
		} catch (SQLException e) {
			if (BaseProperty.getInstance().inMem) {
				Map<String, Ace> map = aceMap.get(ace.getId());
				map.put(remove.getChildId(),remove);
				aceMap.put(ace.getId(), map);
			}
			DbConnFac.staticRollBack(action);
			throw e;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public void updAce(Ace ace) throws SQLException, InterruptedException {
		AceDbAction action = null;
		Ace remove = null;
		try {
			if (BaseProperty.getInstance().inMem) {
				Map<String, Ace> map = aceMap.get(ace.getId());
				remove = map.remove(ace.getChildId());
				map.put(ace.getChildId(), ace);
				aceMap.put(ace.getId(), map);
			}
			action = DbConnFac.getInstance().getAceDbAction();
			action.updAce(ace);
			action.commits();
		} catch (SQLException e) {
			if (BaseProperty.getInstance().inMem) {
				Map<String, Ace> map = aceMap.get(ace.getId());
				map.put(remove.getChildId(), remove);
				aceMap.put(ace.getId(),map);
			}
			DbConnFac.staticRollBack(action);
			throw e;
		} finally {
			DbConnFac.staticClose(action);
		}
		
	}
}	
