package yukecm.handler;

import java.util.List;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import yukcommon.dic.EtcDic;
import yukcommon.dic.UriDic;
import yukcommon.model.User;
import yukcommon.model.lifecycle.LifeCycleInfo;
import yukcommon.model.lifecycle.LifeCycleSetting;
import yukcommon.net.AbsNetHandler;
import yukcommon.util.JsonUtil;
import yukecm.controller.LifeCycleController;
import yukecm.controller.SecureController;

public class LifeCycleHandler extends AbsNetHandler{
	@Override
	public void handleImpl(HttpRequest request, HttpResponse response, String uri,User user) throws Exception {
		String json = request.getFirstHeader(EtcDic.LCSETTING).getValue();
		LifeCycleSetting setting = JsonUtil.fromJson(json, LifeCycleSetting.class);
		if (uri.equals(UriDic.ADDLCSCH)) {
			String id = LifeCycleController.getInstance().addLifeCycleSch(setting);
			response.addHeader(EtcDic.RETID, id);
		}
		else if (uri.equals(UriDic.DELLCSCH)) {
			LifeCycleController.getInstance().delLifeCycleSch(setting);
		}
		else if (uri.equals(UriDic.STARTLC)) {
			LifeCycleController.getInstance().startLifeCycle(setting);
		} else if (uri.equals(UriDic.STOPLC)) {
			LifeCycleController.getInstance().stopLifeCycle(setting);
		} 
		 else if (uri.equals(UriDic.GETLCSCH)) {
			List<LifeCycleSetting> list = LifeCycleController.getInstance().getLifeCycleSch();
			response.addHeader(EtcDic.LCSETTING, JsonUtil.toJson(list));
		} else if (uri.equals(UriDic.GETLCINFO)) {
			LifeCycleInfo info = LifeCycleController.getInstance().getLifeCycleInfo(setting);
			response.addHeader(EtcDic.LCINFO, JsonUtil.toJson(info));
		}
	}

	@Override
	public User preCheck(HttpRequest request, HttpResponse response, String uri) throws Exception {
		User user = SecureController.getInstance().checkUser(request, response, uri);
		SecureController.getInstance().checkSystemPermission(request,response,user,uri);
		return user;
	}

}
