package yukecm.etc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

import yukcommon.dic.EtcDic;
import yukcommon.dic.UriDic;
import yukcommon.model.lifecycle.LifeCycleSetting;
import yukcommon.model.lifecycle.Mig;
import yukcommon.net.NetClient;
import yukcommon.net.NetWrapper;
import yukcommon.net.io.NetIoClient;
import yukcommon.net.io.NetIoWrapper;
import yukcommon.net.nio.NetNioClient;
import yukcommon.net.nio.NetNioWrapper;
import yukcommon.util.JsonUtil;
import yukecm.config.BaseProperty;

public class InnerClient {
	private static class LazyHolder {
	    private static final InnerClient client = new InnerClient();
	}

	public static InnerClient getInstance() {
		return LazyHolder.client;
	}

	private InnerClient(){
		
	}
	
	private NetClient getClient() {
		NetClient client;
		if(BaseProperty.getInstance().type.equalsIgnoreCase("nio")) {
			client = NetNioClient.getInstance();
		}
		else {
			client = NetIoClient.getInstance();
		}
		return client;
	}
	
	private NetWrapper getWrapper(String url, String contextPath) {
		NetWrapper wrapper;
		if(BaseProperty.getInstance().type.equalsIgnoreCase("nio")) {
			wrapper = new NetNioWrapper(url, contextPath);
		}
		else {
			wrapper = new NetIoWrapper(url,contextPath);
		}
		return wrapper;
	}
	
	
	public void migFile(String url, Mig mig) throws URISyntaxException, IOException, InterruptedException, ExecutionException{
		NetClient client = getClient();
		NetWrapper wrap = getWrapper(url,UriDic.MIGFILE);
		if(mig.getDoc().getContent().getItem() != null)
			wrap.setFile(mig.getDoc().getContent().getItem().getInputStream());
		wrap.addHeader(EtcDic.MIG, JsonUtil.toJson(mig));
		client.excute(wrap);
	}
	
	public void startMig(String url, String id) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		NetClient client = getClient();
		NetWrapper wrap = getWrapper(url,UriDic.STARTLCNOTREPL);
		LifeCycleSetting setting = new LifeCycleSetting();
		setting.setId(id);
		wrap.addHeader(EtcDic.LCSETTING, JsonUtil.toJson(setting));
		client.excute(wrap);
	}
	
	public void stopMig(String url, String id) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		NetClient client = getClient();
		NetWrapper wrap = getWrapper(url,UriDic.STOPLCNOTREPL);
		LifeCycleSetting setting = new LifeCycleSetting();
		setting.setId(id);
		wrap.addHeader(EtcDic.LCSETTING, JsonUtil.toJson(setting));
		client.excute(wrap);
	}
}
