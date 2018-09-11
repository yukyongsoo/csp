package yukcommon.net;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;

import yukcommon.exception.EcmNormalError;
import yukcommon.model.User;
import yukcommon.util.LoggerUtil;
import yukcommon.util.NormalUtil;

public abstract class AbsNetHandler {
	public void handling(HttpRequest request,HttpResponse response, String uri) {
		try {
			User user = preCheck(request, response, uri);
			handleImpl(request, response, uri, user);
		} catch (EcmNormalError e) {
			response.setStatusCode(HttpStatus.SC_BAD_REQUEST);
			NormalUtil.makeError(response, e.getMessage());
			LoggerUtil.error(e.getClass(), e.getMessage(), e);
		} catch (Exception e) {
			response.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
			NormalUtil.makeError(response, e.getMessage());
			LoggerUtil.error(e.getClass(), "Server Internal Error.Internal Message :" + e.getMessage(), e);
		} catch (NoClassDefFoundError e) {
			response.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
			NormalUtil.makeError(response, e.getMessage());
			LoggerUtil.error(e.getClass(), "Server Internal Error.Internal Message :" + e.getMessage(),new Exception(e.getCause()));
		}	
	}
	
	public abstract void handleImpl(HttpRequest request,HttpResponse response, String uri, User user) throws Exception;
	public abstract User preCheck(HttpRequest request, HttpResponse response, String uri) throws Exception;
}
