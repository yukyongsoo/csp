package yukecm.controller;

import java.sql.SQLException;
import java.util.List;

import yukcommon.dic.type.MetaSettingType;
import yukcommon.dic.type.OnErrorType;
import yukcommon.exception.NotSupportException;
import yukcommon.model.meta.MetaData;
import yukcommon.model.meta.MetaSetting;
import yukecm.etc.EcmUtil;
import yukecm.injecter.meta.MetaInjecter;
import yukecm.injecter.meta.MetaSettingInjecter;

public class MetaController {
	private static class LazyHolder {
	    private static final MetaController meta = new MetaController();
	}

	public static MetaController getInstance(){
		return LazyHolder.meta;
	}

	private MetaController(){

	}

	public void addDocMeta(MetaData meta) throws SQLException, InterruptedException {
		MetaSetting setting = new MetaSetting();
		setting.setName(meta.getMetaName());
		setting = MetaSettingInjecter.getInstance().getMetaSetting(setting,OnErrorType.NOTEXIST);
		MetaInjecter.getInstance().addMeta(meta,setting);
	}

	public void delDocMeta(MetaData meta) throws SQLException, InterruptedException {
		MetaSetting setting = new MetaSetting();
		setting.setName(meta.getMetaName());
		setting = MetaSettingInjecter.getInstance().getMetaSetting(setting,OnErrorType.NOTEXIST);
		MetaInjecter.getInstance().delMeta(meta,setting);
	}

	public List<MetaData> getDocMeta(MetaData meta) throws SQLException, InterruptedException {
		MetaSetting setting = new MetaSetting();
		setting.setName(meta.getMetaName());
		setting = MetaSettingInjecter.getInstance().getMetaSetting(setting,OnErrorType.NOTEXIST);
		return MetaInjecter.getInstance().getMeta(meta,setting);
	}	

	public void addMetaSetting(MetaSetting setting) throws SQLException, InterruptedException{
		MetaSettingInjecter.getInstance().getMetaSetting(setting,OnErrorType.EXIST);
		if(MetaSettingType.ADD == setting.getType()) {
			EcmUtil.checkForbiddenWord(setting.getQuery(), new String[]{"delete","update","create","drop","truncate"});
			EcmUtil.checkContainWord(setting.getQuery(), new String[]{"?","insert"});
		}
		else if(MetaSettingType.DEL == setting.getType()) {
			EcmUtil.checkForbiddenWord(setting.getQuery(), new String[]{"insert","update","create","drop","truncate"});
			EcmUtil.checkContainWord(setting.getQuery(), new String[]{"?","where","delete"});
		}
		else if(MetaSettingType.GET == setting.getType()) {
			EcmUtil.checkForbiddenWord(setting.getQuery(), new String[]{"delete","update","insert","create","drop","truncate"});
			EcmUtil.checkContainWord(setting.getQuery(), new String[]{"?","where","select"});
		}
		else
			throw new NotSupportException("This Meta Setting Type is Not Supported.");
		MetaSettingInjecter.getInstance().addMetaSetting(setting);
	}

	public void delMetaSetting(MetaSetting setting) throws SQLException, InterruptedException {
		MetaSettingInjecter.getInstance().getMetaSetting(setting,OnErrorType.NOTEXIST);
		MetaSettingInjecter.getInstance().delMetaSetting(setting);
	}

	public MetaSetting getMetaSetting(MetaSetting setting) throws SQLException, InterruptedException{
		return  MetaSettingInjecter.getInstance().getMetaSetting(setting,OnErrorType.NONE);
	}

	public void addMetaSubSetting(MetaSetting setting) throws SQLException, InterruptedException {
		MetaSetting nSetting = MetaSettingInjecter.getInstance().getMetaSetting(setting,OnErrorType.NOTEXIST);
		for(String key : setting.getMap().keySet()){
			setting.getMap().get(key).setKeyName(key);
			nSetting.getMap().put(key, setting.getMap().get(key));
		}
		MetaSettingInjecter.getInstance().updMetaSubSetting(nSetting);
	}
	
	public List<MetaSetting> getMetaSettingList() throws SQLException, InterruptedException {
		return MetaSettingInjecter.getInstance().getMetaSettingList();
	}

	public void delMetaSubSetting(MetaSetting setting) throws SQLException, InterruptedException {
		MetaSetting nSetting = MetaSettingInjecter.getInstance().getMetaSetting(setting,OnErrorType.NOTEXIST);
		for(String key : setting.getMap().keySet()){
			nSetting.getMap().remove(key);
		}
		MetaSettingInjecter.getInstance().updMetaSubSetting(nSetting);
	}

	
}
