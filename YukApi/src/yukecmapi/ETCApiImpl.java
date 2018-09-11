package yukecmapi;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

import yukcommon.net.NetClient;
import yukcommon.net.NetWrapper;

public class ETCApiImpl extends AbsApiImpl{

	protected ETCApiImpl(NetClient client, NetWrapper wrap) {
		super(client, wrap);
	}

	public void reinitCache() throws URISyntaxException, IOException, InterruptedException, ExecutionException {		
		client.excute(wrap);
	}
}
