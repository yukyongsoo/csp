package yukecm.handler;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;

import yukcommon.dic.EtcDic;
import yukcommon.dic.UriDic;
import yukcommon.model.User;
import yukcommon.net.AbsNetHandler;
import yukcommon.util.JsonUtil;
import yukcommon.util.LoggerUtil;
import yukcommon.util.NormalUtil;
import yukecm.cache.CacheModel;
import yukecm.cache.YLC;
import yukecm.injecter.InjecterUtil;

public class CacheHandler extends AbsNetHandler{
	@Override
	public void handleImpl(HttpRequest request, HttpResponse response, String uri,User user) throws Exception {
		try {
			if(uri.equals(UriDic.SYNCCACHE)) {
				String value = request.getFirstHeader(EtcDic.CACHE).getValue();
				CacheModel fromJson = JsonUtil.fromJson(value, CacheModel.class);
				YLC.putCacheModel(fromJson);
			}
			else if(uri.equals(UriDic.REINITCACHE)) {
				InjecterUtil.reinitAll();
			}
		} catch (Exception e) {
			response.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
			NormalUtil.makeError(response, e.getMessage());
			LoggerUtil.warn(getClass(), "Cache Sync Fail", e);
		}
	}

	@Override
	public User preCheck(HttpRequest request, HttpResponse response, String uri) throws Exception {
		return null;
	}

}
