package yukecm.injecter.meta;

import java.sql.SQLException;
import java.util.List;

import yukcommon.dic.type.MetaSettingType;
import yukcommon.exception.NotSupportException;
import yukcommon.model.meta.MetaData;
import yukcommon.model.meta.MetaSetting;
import yukcommon.model.meta.SubMetaSettingData;
import yukecm.db.DbConnFac;
import yukecm.db.MetaDbAction;

public class MetaInjecter {
	private static MetaInjecter injecter;

	public static MetaInjecter getInstance() {
		if(injecter == null)
			injecter = new MetaInjecter();
		return injecter;
	}

	private MetaInjecter() {

	}

	public void addMeta(MetaData meta, MetaSetting setting) throws SQLException{
		checkRightType(setting, MetaSettingType.ADD);
		MetaDbAction action = null;
		try {
			action = DbConnFac.getInstance().getMetaDbAction();
			action.makePrepare(setting.getQuery());
			for(String key : setting.getMap().keySet()){
				SubMetaSettingData subSetting = setting.getMap().get(key);
				Object value = meta.getMeta(key);
				if(value != null)
					action.addParam(subSetting.getOrder(),value,subSetting.getType());
			}
			action.excute();
		} catch (SQLException e) {
			DbConnFac.staticRollBack(action);
			throw e;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public void delMeta(MetaData meta, MetaSetting setting) throws SQLException{
		checkRightType(setting, MetaSettingType.DEL);
		MetaDbAction action = null;
		try {
			action = DbConnFac.getInstance().getMetaDbAction();
			action.makePrepare(setting.getQuery());
			for(String key : setting.getMap().keySet()){
				SubMetaSettingData subSetting = setting.getMap().get(key);
				Object value = meta.getMeta(key);
				if(value != null)
					action.addParam(subSetting.getOrder(),value,subSetting.getType());
			}
			action.excute();
		} catch (SQLException e) {
			DbConnFac.staticRollBack(action);
			throw e;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public List<MetaData> getMeta(MetaData meta, MetaSetting setting) throws SQLException {
		checkRightType(setting, MetaSettingType.GET);
		MetaDbAction action = null;
		try {
			action = DbConnFac.getInstance().getMetaDbAction();
			action.makePrepare(setting.getQuery());
			for(String key : setting.getMap().keySet()){
				SubMetaSettingData subSetting = setting.getMap().get(key);
				Object value = meta.getMeta(key);
				if(value != null)
					action.addParam(subSetting.getOrder(),value,subSetting.getType());
			}
			return action.mapping();
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	private void checkRightType(MetaSetting setting,MetaSettingType type) {
		if(setting.getType() != type)
			throw new NotSupportException("This MetaSetting Is not Right Type." + setting.getType().toString());
	}


}
