package yukecm.injecter.workgroup;

import java.sql.SQLException;
import java.util.List;

import yukcommon.dic.type.OnErrorType;
import yukcommon.dic.type.RuleType;
import yukcommon.exception.NotSupportException;
import yukcommon.model.WorkingGroup;
import yukecm.cache.Cache;
import yukecm.cache.YLC;
import yukecm.config.BaseProperty;
import yukecm.db.DbConnFac;
import yukecm.db.WorkingDbAction;
import yukecm.injecter.InjecterUtil;

public class WorkInjector {
	private static WorkInjector injecter;

	public static WorkInjector getInstance() throws InterruptedException, SQLException {
		if(injecter == null)
			injecter = new WorkInjector();
		return injecter;
	}

	private Cache<String, WorkingGroup> workMap;

	private WorkInjector() throws InterruptedException, SQLException{
		if(BaseProperty.getInstance().inMem)
			workMap = YLC.makeCache("WORK", new WorkJson());
		initWork();
	}

	public void initWork() throws InterruptedException, SQLException {
		workMap.clear();
		List<WorkingGroup> list = getWorkList(OnErrorType.NONE);
		for (WorkingGroup group : list) {
			if(BaseProperty.getInstance().inMem) {
				WorkingGroup temp = getWorkRuleListInDB(group.getId());
				group.setInitList(temp.getInitList());
				group.setMigId(temp.getMigId());
				group.setDesId(temp.getDesId());
				workMap.put(group.getId(), group);
			}
		}
	}

	public List<WorkingGroup> getWorkList(OnErrorType type) throws SQLException {
		WorkingDbAction action = null;
		List<WorkingGroup> list;
		try {
			action = DbConnFac.getInstance().getWorkingDbAction();
			list = action.getWorkList();
			InjecterUtil.onErrorListException(list, type);
			return list;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public WorkingGroup getWorkingByName(String name,OnErrorType type) throws SQLException {
		WorkingDbAction action = null;
		WorkingGroup workingGroup = null;
		try {
			if(BaseProperty.getInstance().inMem){
				for(WorkingGroup group : workMap.values()){
					if(group.getName().equals(name)) {
						workingGroup = group;
						break;
					}
				}
				InjecterUtil.onErrorException(workingGroup, type);
				return workingGroup;
			}
			action = DbConnFac.getInstance().getWorkingDbAction();
			workingGroup = action.getWorkingByName(name);
			InjecterUtil.onErrorException(workingGroup, type);
			return workingGroup;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public WorkingGroup getWorking(String id,OnErrorType type) throws SQLException {
		WorkingDbAction action = null;
		WorkingGroup group = null;
		try {
			if(BaseProperty.getInstance().inMem){
				group = workMap.get(id);
				InjecterUtil.onErrorException(group, type);
				return group;
			}
			action = DbConnFac.getInstance().getWorkingDbAction();
			group = action.getWorking(id);
			InjecterUtil.onErrorException(group, type);
			return group;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public void addWork(WorkingGroup work) throws SQLException, InterruptedException {
		WorkingDbAction action = null;
		try {
			if(BaseProperty.getInstance().inMem)
				workMap.put(work.getId(), work);
			action = DbConnFac.getInstance().getWorkingDbAction();
			action.addWork(work);
			action.commits();
		} catch (SQLException e) {
			if(BaseProperty.getInstance().inMem)
				workMap.remove(work.getId());
			DbConnFac.staticRollBack(action);
			throw e;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public void updWork(WorkingGroup work) throws SQLException, InterruptedException {
		WorkingDbAction action = null;
		WorkingGroup remove = null;
		try {
			if(BaseProperty.getInstance().inMem){
				remove = workMap.remove(work.getId());
				work.setMigId(remove.getMigId());
				work.setInitList(remove.getInitList());
				work.setDesId(remove.getDesId());
				workMap.put(work.getId(), work);
			}
			action = DbConnFac.getInstance().getWorkingDbAction();
			action.updWork(work);
			action.commits();
		} catch (SQLException e) {
			if(BaseProperty.getInstance().inMem){
				workMap.put(work.getId(),remove);
			}
			DbConnFac.staticRollBack(action);
			throw e;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public void delWork(WorkingGroup work) throws SQLException, InterruptedException {
		WorkingDbAction action = null;
		WorkingGroup remove = null;
		try {
			if(BaseProperty.getInstance().inMem)
				remove = workMap.remove(work.getId());
			action = DbConnFac.getInstance().getWorkingDbAction();
			action.delWork(work);
			action.commits();
		} catch (SQLException e) {
			if(BaseProperty.getInstance().inMem)
				workMap.put(work.getId(), remove);
			DbConnFac.staticRollBack(action);
			throw e;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public void addWorkRule(String id, String ruleid, RuleType type) throws SQLException, InterruptedException {
		WorkingDbAction action = null;
		WorkingGroup workingGroup = null;
		String rollback = "";
		try {
			if(BaseProperty.getInstance().inMem){
				workingGroup = workMap.get(id);
				if(RuleType.INITRULE == type)
					workingGroup.getInitList().add(ruleid);
				else if(RuleType.MIGRULE == type) {
					rollback = workingGroup.getMigId();
					workingGroup.setMigId(ruleid);
				}
				else if(RuleType.DESRULE == type) {
					rollback = workingGroup.getDesId();
					workingGroup.setDesId(ruleid);
				}
				else
					throw new NotSupportException("Not Support Rule Type");
				workMap.put(id, workingGroup);
			}
			action = DbConnFac.getInstance().getWorkingDbAction();
			action.addWorkRule(id,ruleid,type);
			action.commits();
		} catch (SQLException e) {
			if(BaseProperty.getInstance().inMem){
				if(RuleType.INITRULE == type) {
					workingGroup.getInitList().remove(ruleid);
				}
				else if(RuleType.MIGRULE == type){
					workingGroup.setMigId(rollback);
				}
				else if(RuleType.DESRULE == type){
					workingGroup.setDesId(rollback);
				}
				workMap.put(id, workingGroup);
			}
			DbConnFac.staticRollBack(action);
			throw e;
		}
		finally {
			DbConnFac.staticClose(action);
		}
	}

	public void delWorkRule(String id, String ruleid, RuleType type) throws InterruptedException, SQLException {
		WorkingDbAction action = null;
		WorkingGroup workingGroup = null;
		String rollback = "";
		try {
			if(BaseProperty.getInstance().inMem){
				workingGroup = workMap.get(id);
				if(RuleType.INITRULE == type)
					workingGroup.getInitList().remove(ruleid);
				if(RuleType.MIGRULE == type){
					rollback = workingGroup.getMigId();
					workingGroup.setMigId(null);
				}
				if(RuleType.DESRULE == type){
					rollback = workingGroup.getDesId();
					workingGroup.setDesId(null);
				}
				workMap.put(id, workingGroup);
			}
			action = DbConnFac.getInstance().getWorkingDbAction();
			action.delWorkRule(id,ruleid);
			action.commits();
		} catch (SQLException e) {
			if(BaseProperty.getInstance().inMem){
				if(RuleType.INITRULE == type)
					workingGroup.getInitList().add(ruleid);
				else if(RuleType.MIGRULE == type)
					workingGroup.setMigId(rollback);
				else if(RuleType.DESRULE == type)
					workingGroup.setDesId(rollback);
				workMap.put(id, workingGroup);
			}
			DbConnFac.staticRollBack(action);
			throw e;
		}
		finally {
			DbConnFac.staticClose(action);
		}
	}

	public WorkingGroup getWorkRuleList(String workingId, OnErrorType type) throws SQLException {
		WorkingGroup nGroup = null;
		if (BaseProperty.getInstance().inMem) {
			nGroup = workMap.get(workingId);
			InjecterUtil.onErrorException(nGroup, type);
			return nGroup;
		}
		nGroup = getWorkRuleListInDB(workingId);
		InjecterUtil.onErrorException(nGroup, type);
		return nGroup;
	}

	private WorkingGroup getWorkRuleListInDB(String workingId) throws SQLException{
		WorkingDbAction action = null;
		try {
			action = DbConnFac.getInstance().getWorkingDbAction();
			return action.getWorkRule(workingId);
		}
		finally {
			DbConnFac.staticClose(action);
		}
	}





}
