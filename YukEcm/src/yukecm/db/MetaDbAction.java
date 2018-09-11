package yukecm.db;

import java.sql.SQLException;
import java.util.List;

import yukcommon.dic.type.SubMetaSettingType;
import yukcommon.model.meta.MetaData;

public interface MetaDbAction extends AbsDbAction{

	void makePrepare(String query) throws SQLException;

	void excute() throws SQLException;

	void addParam(int order, Object value, SubMetaSettingType type) throws SQLException;

	List<MetaData> mapping() throws SQLException;

}