package yukecmapi;

import java.util.List;

import yukcommon.dic.UriDic;
import yukcommon.exception.NotNullAllowedException;
import yukcommon.model.meta.MetaData;
import yukcommon.model.meta.MetaSetting;

public class MetaApi extends AbsApi{
	public MetaApi(EcmConnection conn) {
		super(conn);
	}

	public void addDocMeta(MetaData meta) throws Exception{
		if(meta == null)
			throw new NotNullAllowedException("this operation needed MetaData Object");
		MetaApiImpl impl = EcmApiFactory.getMetaApi(conn,UriDic.ADDDOCMETA);
		impl.addDocMeta(meta);
	}

	public void delDocMeta(MetaData meta) throws Exception{
		if(meta == null)
			throw new NotNullAllowedException("this operation needed MetaData Object");
		MetaApiImpl impl = EcmApiFactory.getMetaApi(conn,UriDic.DELDOCMETA);
		impl.delDocMeta(meta);
	}

	public List<MetaData> getDocMeta(MetaData meta) throws Exception{
		if(meta == null)
			throw new NotNullAllowedException("this operation needed MetaData Object");
		MetaApiImpl impl = EcmApiFactory.getMetaApi(conn,UriDic.GETDOCMETA);
		return impl.getDocMeta(meta);
	}

	public void addMetaSetting(MetaSetting setting) throws Exception{
		if(setting == null)
			throw new NotNullAllowedException("this operation needed MetaSetting Object");
		MetaApiImpl impl = EcmApiFactory.getMetaApi(conn,UriDic.ADDMETASETTING);
		impl.addMetaSetting(setting);
	}

	public void delMetaSetting(String settingName) throws Exception{
		MetaApiImpl impl = EcmApiFactory.getMetaApi(conn,UriDic.DELMETASETTING);
		impl.delMetaSetting(settingName);
	}

	public MetaSetting getMetaSetting(String settingName) throws Exception{
		MetaApiImpl impl = EcmApiFactory.getMetaApi(conn,UriDic.GETMETASETTING);
		return impl.getMetaSetting(settingName);
	}

	public void addMetaSubSetting(MetaSetting setting) throws Exception{
		if(setting == null)
			throw new NotNullAllowedException("this operation needed MetaSetting Object");
		MetaApiImpl impl = EcmApiFactory.getMetaApi(conn,UriDic.ADDMETASUBSETTING);
		impl.addMetaSubSetting(setting);
	}

	public void delMetaSubSetting(MetaSetting setting) throws Exception{
		if(setting == null)
			throw new NotNullAllowedException("this operation needed MetaSetting Object");
		MetaApiImpl impl = EcmApiFactory.getMetaApi(conn,UriDic.DELMETASUBSETTING);
		impl.delMetaSubSetting(setting);
	}

	public List<MetaSetting> getMetaSettingList() throws Exception{
		MetaApiImpl impl = EcmApiFactory.getMetaApi(conn,UriDic.GETMETASETTINGLIST);
		return impl.getMetaSettingList();
	}
}
