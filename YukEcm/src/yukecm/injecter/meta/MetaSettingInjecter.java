package yukecm.injecter.meta;

import java.sql.SQLException;
import java.util.List;

import yukcommon.dic.type.OnErrorType;
import yukcommon.model.meta.MetaSetting;
import yukecm.cache.Cache;
import yukecm.cache.YLC;
import yukecm.config.BaseProperty;
import yukecm.db.DbConnFac;
import yukecm.db.MetaSettingDbAction;
import yukecm.injecter.InjecterUtil;

public class MetaSettingInjecter {
	private static MetaSettingInjecter injecter;

	public static MetaSettingInjecter getInstance() throws SQLException, InterruptedException {
		if(injecter == null)
			injecter = new MetaSettingInjecter();
		return injecter;
	}

	private Cache<String, MetaSetting> settingMap;

	private MetaSettingInjecter() throws SQLException, InterruptedException{
		if(BaseProperty.getInstance().inMem){
			settingMap = YLC.makeCache("META", new MetaSettingJson());
			initMeta();
		}
	}

	public void initMeta() throws SQLException, InterruptedException {
		settingMap.clear();
		List<MetaSetting> list = getMetaSettingList();
		for (MetaSetting setting : list) {
			settingMap.put(setting.getName(), setting);
		}
	}

	public void addMetaSetting(MetaSetting setting) throws SQLException, InterruptedException {
		MetaSettingDbAction action = null;
		try {
			if(BaseProperty.getInstance().inMem){
				settingMap.put(setting.getName(), setting);
			}
			action = DbConnFac.getInstance().getMetaSettingDbAction();
			action.addMetaSetting(setting);
			action.commits();
		} catch (SQLException e) {
			if(BaseProperty.getInstance().inMem){
				settingMap.remove(setting.getName());
			}
			DbConnFac.staticRollBack(action);
			throw e;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public MetaSetting getMetaSetting(MetaSetting setting, OnErrorType type) throws SQLException {
		MetaSettingDbAction action = null;
		MetaSetting nSetting = null;
		try {
			if(BaseProperty.getInstance().inMem){
				nSetting = settingMap.get(setting.getName());
				InjecterUtil.onErrorException(nSetting, type);
				return nSetting;
			}			
			action = DbConnFac.getInstance().getMetaSettingDbAction();
			nSetting = action.getMetaSetting(setting);
			InjecterUtil.onErrorException(nSetting, type);
			return nSetting;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public List<MetaSetting> getMetaSettingList() throws SQLException {
		MetaSettingDbAction action = null;
		try {
			action = DbConnFac.getInstance().getMetaSettingDbAction();
			return action.getMetaSettingList();
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public void delMetaSetting(MetaSetting setting) throws SQLException, InterruptedException {
		MetaSettingDbAction action = null;
		MetaSetting rollback = null;
		try {
			if(BaseProperty.getInstance().inMem){
				rollback = settingMap.remove(setting.getName());
			}
			action = DbConnFac.getInstance().getMetaSettingDbAction();
			action.delMetaSetting(setting);
			action.commits();
		} catch (SQLException e) {
			if(BaseProperty.getInstance().inMem){
				settingMap.put(rollback.getName(), rollback);
			}
			DbConnFac.staticRollBack(action);
			throw e;
		} finally {
			DbConnFac.staticClose(action);
		}

	}

	public void updMetaSubSetting(MetaSetting nSetting) throws SQLException, InterruptedException {
		MetaSettingDbAction action = null;
		MetaSetting rollback = null;
		try {
			if(BaseProperty.getInstance().inMem){
				rollback = settingMap.remove(nSetting.getName());
				settingMap.put(nSetting.getName(), nSetting);
			}
			action = DbConnFac.getInstance().getMetaSettingDbAction();
			action.updMetaSetting(nSetting);
			action.commits();
		} catch (SQLException e) {
			if(BaseProperty.getInstance().inMem){
				settingMap.put(rollback.getName(), rollback);
			}
			DbConnFac.staticRollBack(action);
			throw e;
		} finally {
			DbConnFac.staticClose(action);
		}
	}
}
