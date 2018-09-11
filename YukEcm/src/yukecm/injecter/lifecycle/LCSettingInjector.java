package yukecm.injecter.lifecycle;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.quartz.SchedulerException;

import yukcommon.dic.type.OnErrorType;
import yukcommon.model.lifecycle.LifeCycleSetting;
import yukecm.cache.Cache;
import yukecm.cache.YLC;
import yukecm.config.BaseProperty;
import yukecm.db.DbConnFac;
import yukecm.db.LCSettingDbAction;
import yukecm.injecter.InjecterUtil;
import yukecm.lifecycle.LifeCycleManager;

public class LCSettingInjector {
	private static LCSettingInjector injecter;

	public static LCSettingInjector getInstance() throws SQLException, InterruptedException, SchedulerException {
		if(injecter == null)
			injecter = new LCSettingInjector();
		return injecter;
	}

	private LCSettingInjector() throws SQLException, InterruptedException, SchedulerException {
		settingMap = YLC.makeCache("LCSetting", new LCSettingJson(), new LCSettingInjectorHandler());
		List<LifeCycleSetting> cycleList = getLifeCycleListByDb();
		for(LifeCycleSetting setting : cycleList){
			settingMap.put(setting.getId(), setting);
			LifeCycleManager.getInstance().addLifeCycleSch(setting);
		}
	}

	private Cache<String, LifeCycleSetting> settingMap;

	public void addLifeCycleSch(LifeCycleSetting setting) throws SQLException, InterruptedException{
		LCSettingDbAction action = null;
		try{
			if(BaseProperty.getInstance().inMem)
				settingMap.put(setting.getId(), setting);
			action = DbConnFac.getInstance().getLCSettingDbAction();
			action.addLifeCycleSch(setting);
			action.commits();
		}
		catch(SQLException e){
			if(BaseProperty.getInstance().inMem)
				settingMap.remove(setting.getId());
			DbConnFac.staticRollBack(action);
			throw e;
		}
		finally {
			DbConnFac.staticClose(action);
		}
	}

	public void delLifeCycleSch(LifeCycleSetting setting) throws SQLException, InterruptedException{
		LCSettingDbAction action = null;
		LifeCycleSetting rollback = null;
		try{
			if(BaseProperty.getInstance().inMem)
				rollback = settingMap.remove(setting.getId());
			action = DbConnFac.getInstance().getLCSettingDbAction();
			action.delLifeCycleSch(setting.getId());
			action.commits();

		}
		catch(SQLException e){
			if(BaseProperty.getInstance().inMem)
				settingMap.put(rollback.getId(), rollback);
			DbConnFac.staticRollBack(action);
			throw e;
		}
		finally {
			DbConnFac.staticClose(action);
		}
	}

	public LifeCycleSetting getLifeCycleSch(LifeCycleSetting setting,OnErrorType type) throws SQLException {
		LCSettingDbAction action = null;
		LifeCycleSetting nSetting = null;
		try{
			if(BaseProperty.getInstance().inMem){
				nSetting = settingMap.get(setting.getId());
				InjecterUtil.onErrorException(nSetting, type);
				return nSetting;
			}
			action = DbConnFac.getInstance().getLCSettingDbAction();
			nSetting = action.getLifeCycleSch(setting.getId());
			InjecterUtil.onErrorException(nSetting, type);
			return nSetting;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public LifeCycleSetting getLifeCycleSchByWork(LifeCycleSetting setting,OnErrorType type) throws SQLException{
		List<LifeCycleSetting> lifeCycleList = getLifeCycleList();
		LifeCycleSetting nSet = null;
		for(LifeCycleSetting set : lifeCycleList){
			if(set.getWorkId().equals(setting.getWorkId())) {
				nSet = set;
				break;
			}
		}
		InjecterUtil.onErrorException(nSet, type);
		return nSet;
	}

	public LifeCycleSetting getLifeCycleSchByName(LifeCycleSetting setting, OnErrorType type) throws SQLException{
		List<LifeCycleSetting> lifeCycleList = getLifeCycleList();
		LifeCycleSetting nSetting = null;
		for(LifeCycleSetting set : lifeCycleList){
			if(set.getName().equals(setting.getName())) {
				nSetting = set;
				break;
			}
		}
		InjecterUtil.onErrorException(nSetting, type);
		return nSetting;
	}

	public List<LifeCycleSetting> getLifeCycleList() throws SQLException {
		if(BaseProperty.getInstance().inMem){
			return new ArrayList<LifeCycleSetting>(settingMap.values());
		}
		return getLifeCycleListByDb();
	}

	private List<LifeCycleSetting> getLifeCycleListByDb() throws SQLException {
		LCSettingDbAction action = null;
		try{
			action = DbConnFac.getInstance().getLCSettingDbAction();
			return action.getLifeCycleList();
		} finally {
			DbConnFac.staticClose(action);
		}
	}
}
