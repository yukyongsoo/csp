package yukecm.handler;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import yukcommon.model.User;
import yukcommon.net.AbsNetHandler;
import yukecm.controller.SecureController;

public class SearchHandler extends AbsNetHandler{
	@Override
	public void handleImpl(HttpRequest request, HttpResponse response, String uri,User user) throws Exception {
		// TODO make Search
	}

	@Override
	public User preCheck(HttpRequest request, HttpResponse response, String uri) throws Exception {
		return SecureController.getInstance().checkUser(request, response, uri);
	}
}
