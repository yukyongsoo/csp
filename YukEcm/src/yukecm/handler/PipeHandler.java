package yukecm.handler;

import java.util.List;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import yukcommon.dic.EtcDic;
import yukcommon.dic.UriDic;
import yukcommon.model.Pipe;
import yukcommon.model.User;
import yukcommon.net.AbsNetHandler;
import yukcommon.util.JsonUtil;
import yukecm.controller.PipeController;
import yukecm.controller.SecureController;

public class PipeHandler extends AbsNetHandler{
	@Override
	public void handleImpl(HttpRequest request, HttpResponse response, String uri,User user) throws Exception {
		String json = request.getFirstHeader(EtcDic.PIPE).getValue();
		Pipe pipe = JsonUtil.fromJson(json, Pipe.class);
		if (uri.equals(UriDic.ADDPIPE)) {
			String id = PipeController.getInstance().addPipe(pipe);
			response.addHeader(EtcDic.RETID, id);
		} else if (uri.equals(UriDic.DELPIPE)) {
			PipeController.getInstance().delPipe(pipe);
		} else if (uri.equals(UriDic.UPDPIPE)) {
			PipeController.getInstance().updPipe(pipe);
		} else if (uri.equals(UriDic.GETPIPE)) {
			List<Pipe> pipeList = PipeController.getInstance().getPipeList();
			response.addHeader(EtcDic.PIPE, JsonUtil.toJson(pipeList));
		}
	}

	@Override
	public User preCheck(HttpRequest request, HttpResponse response, String uri) throws Exception {
		User user = SecureController.getInstance().checkUser(request, response, uri);
		SecureController.getInstance().checkSystemPermission(request,response,user,uri);
		return user;
	}
}
