package yukecm.injecter;

import yukcommon.dic.UriDic;
import yukcommon.net.NetClient;
import yukcommon.net.NetWrapper;
import yukcommon.net.io.NetIoClient;
import yukcommon.net.io.NetIoWrapper;
import yukcommon.net.nio.NetNioClient;
import yukcommon.net.nio.NetNioWrapper;
import yukcommon.util.JsonUtil;
import yukecm.cache.CacheModel;
import yukecm.cache.inter.ReplicatedPolicy;
import yukecm.config.BaseProperty;

public class CacheSender implements ReplicatedPolicy{
	@Override
	public void sendReplicate(CacheModel model, String address) throws Exception {
		NetClient client;
		NetWrapper wrap;
		if(BaseProperty.getInstance().type.equalsIgnoreCase("nio")) {
			client = NetNioClient.getInstance();
			wrap = new NetNioWrapper(address,UriDic.SYNCCACHE);
		}
		else {
			client = NetIoClient.getInstance();
			wrap = new NetIoWrapper(address,UriDic.SYNCCACHE);
		}
		wrap.addHeader("cache", JsonUtil.toJson(model));
		client.excute(wrap);
	}
}
