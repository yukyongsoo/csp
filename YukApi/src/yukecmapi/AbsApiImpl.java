package yukecmapi;

import yukcommon.net.NetClient;
import yukcommon.net.NetWrapper;

public abstract class AbsApiImpl {
	NetClient client;
	NetWrapper wrap;
	
	protected AbsApiImpl(NetClient client, NetWrapper wrap) {
		this.client = client;
		this.wrap = wrap;
	}
}
