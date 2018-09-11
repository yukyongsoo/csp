package yukecm.db;

import java.sql.SQLException;
import java.util.List;

import yukcommon.model.meta.MetaSetting;

public interface MetaSettingDbAction extends AbsDbAction{

	void addMetaSetting(MetaSetting setting) throws SQLException;

	MetaSetting getMetaSetting(MetaSetting setting) throws SQLException;

	void delMetaSetting(MetaSetting setting) throws SQLException;

	void updMetaSetting(MetaSetting nSetting) throws SQLException;

	List<MetaSetting> getMetaSettingList() throws SQLException;

}