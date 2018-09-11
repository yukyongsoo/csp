package yukecm.handler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;

import yukcommon.dic.EtcDic;
import yukcommon.dic.UriDic;
import yukcommon.exception.NotSupportException;
import yukcommon.model.User;
import yukcommon.model.Version;
import yukcommon.model.fileitem.IFileItem;
import yukcommon.model.fileitem.StreamFileItem;
import yukcommon.net.AbsNetHandler;
import yukcommon.util.JsonUtil;
import yukecm.controller.SecureController;
import yukecm.controller.VersionController;

public class VersionHandler extends AbsNetHandler{

	@Override
	public void handleImpl(HttpRequest request, HttpResponse response, String uri, User user) throws Exception {
		String json = request.getFirstHeader(EtcDic.VERSION).getValue();
		Version version = JsonUtil.fromJson(json, Version.class);
		if (uri.equals(UriDic.CHECKOUT)) {
			VersionController.getInstance().checkOut(version);
		}
		else if (uri.equals(UriDic.CHECKIN)) {
			if(!(request instanceof BasicHttpEntityEnclosingRequest))
				throw new NotSupportException("request are not Post Type");
			BasicHttpEntityEnclosingRequest req = (BasicHttpEntityEnclosingRequest) request;
			HttpEntity entity = req.getEntity();
			IFileItem item = new StreamFileItem(entity.getContent());
			VersionController.getInstance().checkIn(version,item);
		} 
		else if (uri.equals(UriDic.CANCLECHECKOUT)) {
			VersionController.getInstance().cancleCheckOut(version);
		} 
		else if (uri.equals(UriDic.REVERSEVERSION)) {
			VersionController.getInstance().reverseVersion(version);
		}
	}

	@Override
	public User preCheck(HttpRequest request, HttpResponse response, String uri) throws Exception {
		return SecureController.getInstance().checkUser(request, response, uri);
	}

}
