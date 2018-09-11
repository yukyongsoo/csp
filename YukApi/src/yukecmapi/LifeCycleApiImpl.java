package yukecmapi;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import yukcommon.dic.EtcDic;
import yukcommon.model.lifecycle.LifeCycleInfo;
import yukcommon.model.lifecycle.LifeCycleSetting;
import yukcommon.net.NetClient;
import yukcommon.net.NetWrapper;
import yukcommon.util.JsonUtil;

public class LifeCycleApiImpl extends AbsApiImpl{
	protected LifeCycleApiImpl(NetClient client, NetWrapper wrap) {
		super(client, wrap);
	}

	public List<LifeCycleSetting> getLifeCycleSch() throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		LifeCycleSetting setting = new LifeCycleSetting();
		wrap.addHeader(EtcDic.LCSETTING, JsonUtil.toJson(setting));
		client.excute(wrap);
		String json = wrap.getHeaderValue(EtcDic.LCSETTING);
		return JsonUtil.fromJson(json, JsonUtil.LISTLCSETTING);
	}

	public void delLifeCycleSch(String id)  throws Exception {
		sameFunction(id);
	}

	public String addLifeCycleSch(LifeCycleSetting setting) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		setting.getName();
		setting.getWorkId();
		setting.getStartingCron();
		setting.getEndingCron();
		wrap.addHeader(EtcDic.LCSETTING, JsonUtil.toJson(setting));
		client.excute(wrap);
		return wrap.getHeaderValue(EtcDic.RETID);
	}

	public void startLifeCycle(String id) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		sameFunction(id);
	}

	public void stopLifeCycle(String id) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		sameFunction(id);
	}
	
	private void sameFunction(String id) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		LifeCycleSetting setting = new LifeCycleSetting();
		setting.setId(id);
		wrap.addHeader(EtcDic.LCSETTING, JsonUtil.toJson(setting));
		client.excute(wrap);
	}

	public LifeCycleInfo getLifeCycleInfo(String id) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		LifeCycleSetting setting = new LifeCycleSetting();
		setting.setId(id);
		wrap.addHeader(EtcDic.LCSETTING, JsonUtil.toJson(setting));
		client.excute(wrap);
		String json = wrap.getHeaderValue(EtcDic.LCINFO);
		return JsonUtil.fromJson(json, LifeCycleInfo.class);
	}
}
