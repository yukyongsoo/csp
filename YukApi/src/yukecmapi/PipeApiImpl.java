package yukecmapi;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import yukcommon.dic.EtcDic;
import yukcommon.model.Pipe;
import yukcommon.net.NetClient;
import yukcommon.net.NetWrapper;
import yukcommon.util.JsonUtil;

public class PipeApiImpl extends AbsApiImpl {
	protected PipeApiImpl(NetClient client, NetWrapper wrap) {
		super(client, wrap);
	}

	public String addPipe(String name, String className, boolean passOnError) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		Pipe pipe = new Pipe();
		pipe.setName(name);
		pipe.setClassName(className);
		pipe.setPassOnError(passOnError);
		wrap.addHeader(EtcDic.PIPE, JsonUtil.toJson(pipe));
		client.excute(wrap);
		return wrap.getHeaderValue(EtcDic.RETID);
	}

	public void delPipe(String id) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		Pipe pipe = new Pipe();
		pipe.setId(id);
		wrap.addHeader(EtcDic.PIPE, JsonUtil.toJson(pipe));
		client.excute(wrap);
	}

	public void updPipe(String id, String name, String className, boolean passOnError) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		Pipe pipe = new Pipe();
		pipe.setId(id);
		pipe.setName(name);
		pipe.setClassName(className);
		pipe.setPassOnError(passOnError);
		wrap.addHeader(EtcDic.PIPE, JsonUtil.toJson(pipe));
		client.excute(wrap);
	}

	public List<Pipe> getPipe() throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		Pipe pipe = new Pipe();
		wrap.addHeader(EtcDic.PIPE, JsonUtil.toJson(pipe));
		client.excute(wrap);
		String json = wrap.getHeaderValue(EtcDic.PIPE);
		return JsonUtil.fromJson(json, JsonUtil.LISTPIPE);
	}
}
