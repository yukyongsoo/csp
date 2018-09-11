package yukecmapi;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import yukcommon.dic.EtcDic;
import yukcommon.exception.WrongOperationException;
import yukcommon.model.meta.MetaData;
import yukcommon.model.meta.MetaSetting;
import yukcommon.net.NetClient;
import yukcommon.net.NetWrapper;
import yukcommon.util.JsonUtil;

public class MetaApiImpl extends AbsApiImpl{

	protected MetaApiImpl(NetClient client, NetWrapper wrap) {
		super(client, wrap);
	}

	public void addDocMeta(MetaData meta) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		wrap.addHeader(EtcDic.META, JsonUtil.toJson(meta));
		client.excute(wrap);
	}

	public void delDocMeta(MetaData meta) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		meta.getMetaName();
		wrap.addHeader(EtcDic.META, JsonUtil.toJson(meta));
		client.excute(wrap);
	}

	public List<MetaData> getDocMeta(MetaData meta) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		meta.getMetaName();
		wrap.addHeader(EtcDic.META, JsonUtil.toJson(meta));
		client.excute(wrap);
		String json = wrap.getHeaderValue(EtcDic.META);
		return JsonUtil.fromJson(json, JsonUtil.LISTMETA);
	}

	public void addMetaSetting(MetaSetting setting) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		setting.getName();
		setting.getQuery();
		setting.getType();
		wrap.addHeader(EtcDic.METASETTING, JsonUtil.toJson(setting));
		client.excute(wrap);
	}

	public void delMetaSetting(String settingName) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		MetaSetting setting = new MetaSetting();
		setting.setName(settingName);
		wrap.addHeader(EtcDic.METASETTING, JsonUtil.toJson(setting));
		client.excute(wrap);
	}

	public MetaSetting getMetaSetting(String settingName) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		MetaSetting setting = new MetaSetting();
		setting.setName(settingName);
		wrap.addHeader(EtcDic.METASETTING, JsonUtil.toJson(setting));
		client.excute(wrap);
		String json = wrap.getHeaderValue(EtcDic.METASETTING);
		return JsonUtil.fromJson(json, MetaSetting.class);
	}

	public List<MetaSetting> getMetaSettingList() throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		MetaSetting setting = new MetaSetting();
		wrap.addHeader(EtcDic.METASETTING, JsonUtil.toJson(setting));
		client.excute(wrap);
		String json = wrap.getHeaderValue(EtcDic.METASETTING);
		return JsonUtil.fromJson(json, JsonUtil.LISTMETASETTING);
	}
	
	public void addMetaSubSetting(MetaSetting setting) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		sameFunction(setting);
	}

	public void delMetaSubSetting(MetaSetting setting) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		sameFunction(setting);
	}
	
	private void sameFunction(MetaSetting setting) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		setting.getName();
		if(setting.getMap().isEmpty())
			throw new WrongOperationException("add or delete metaSubData function needed more than zero 'MetaSubData Object'");
		wrap.addHeader(EtcDic.METASETTING, JsonUtil.toJson(setting));
		client.excute(wrap);
	}

}
