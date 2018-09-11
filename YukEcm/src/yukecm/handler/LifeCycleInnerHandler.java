package yukecm.handler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;

import yukcommon.dic.EtcDic;
import yukcommon.dic.UriDic;
import yukcommon.exception.NotSupportException;
import yukcommon.model.User;
import yukcommon.model.fileitem.IFileItem;
import yukcommon.model.fileitem.StreamFileItem;
import yukcommon.model.lifecycle.LifeCycleSetting;
import yukcommon.model.lifecycle.Mig;
import yukcommon.net.AbsNetHandler;
import yukcommon.util.JsonUtil;
import yukcommon.util.LoggerUtil;
import yukcommon.util.NormalUtil;
import yukecm.controller.LifeCycleController;

public class LifeCycleInnerHandler extends AbsNetHandler{
	@Override
	public void handleImpl(HttpRequest request, HttpResponse response, String uri,User user) throws Exception {
		try {
			if (uri.equals(UriDic.MIGFILE)) {
				if(!(request instanceof BasicHttpEntityEnclosingRequest))
					throw new NotSupportException("request are not Post Type");
				BasicHttpEntityEnclosingRequest req = (BasicHttpEntityEnclosingRequest) request;
				String json = request.getFirstHeader(EtcDic.MIG).getValue();
				Mig mig = JsonUtil.fromJson(json, Mig.class);
				IFileItem item = null;
				if(!mig.isDirect()) {
					HttpEntity entity = req.getEntity();
					item = new StreamFileItem(entity.getContent());
				}
				LifeCycleController.getInstance().migFile(mig,item);
			}
			else if (uri.equals(UriDic.STARTLCNOTREPL)) {
				String json = request.getFirstHeader(EtcDic.LCSETTING).getValue();
				LifeCycleSetting setting = JsonUtil.fromJson(json, LifeCycleSetting.class);
				LifeCycleController.getInstance().startLifeCycleNotRepl(setting);
			} else if (uri.equals(UriDic.STOPLCNOTREPL)) {
				String json = request.getFirstHeader(EtcDic.LCSETTING).getValue();
				LifeCycleSetting setting = JsonUtil.fromJson(json, LifeCycleSetting.class);
				LifeCycleController.getInstance().stopLifeCycleNotRepl(setting);
			} 
		} catch (Exception e) {
			response.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
			NormalUtil.makeError(response, e.getMessage());
			LoggerUtil.error(getClass(), "LifeCycle Inner Hadler Error", e);
		}
	}

	@Override
	public User preCheck(HttpRequest request, HttpResponse response, String uri) throws Exception {
		return null;
	}
}
