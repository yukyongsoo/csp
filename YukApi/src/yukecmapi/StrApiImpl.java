package yukecmapi;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import yukcommon.dic.EtcDic;
import yukcommon.model.Storage;
import yukcommon.net.NetClient;
import yukcommon.net.NetWrapper;
import yukcommon.util.JsonUtil;

public class StrApiImpl extends AbsApiImpl {
	protected StrApiImpl(NetClient client, NetWrapper wrap) {
		super(client, wrap);
	}

	public String addStorage(Storage storage) throws URISyntaxException, IOException, InterruptedException, ExecutionException  {
		storage.getName();
		storage.getType();
		wrap.addHeader(EtcDic.STORAGE, JsonUtil.toJson(storage));
		client.excute(wrap);
		return wrap.getHeaderValue(EtcDic.RETID);
	}

	public void delStorage(String id) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		Storage storage = new Storage();
		storage.setId(id);
		wrap.addHeader(EtcDic.STORAGE, JsonUtil.toJson(storage));
		client.excute(wrap);
	}

	public void updStorage(Storage storage) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		storage.getId();
		storage.getName();
		storage.getType();
		wrap.addHeader(EtcDic.STORAGE, JsonUtil.toJson(storage));
		client.excute(wrap);
	}

	public List<Storage> getStorage() throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		wrap.addHeader(EtcDic.STORAGE, JsonUtil.toJson(new Storage()));
		client.excute(wrap);
		String json = wrap.getHeaderValue(EtcDic.STORAGE);
		return JsonUtil.fromJson(json, JsonUtil.LISTSTR);
	}
}
