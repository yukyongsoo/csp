package yukecm.handler;

import java.util.List;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import yukcommon.dic.EtcDic;
import yukcommon.dic.UriDic;
import yukcommon.model.User;
import yukcommon.model.meta.MetaData;
import yukcommon.model.meta.MetaSetting;
import yukcommon.net.AbsNetHandler;
import yukcommon.util.JsonUtil;
import yukecm.controller.MetaController;
import yukecm.controller.SecureController;

public class MetaHandler extends AbsNetHandler{

	@Override
	public void handleImpl(HttpRequest request, HttpResponse response, String uri,User user) throws Exception {
		if(uri.equals(UriDic.ADDDOCMETA)){
			String json = request.getFirstHeader(EtcDic.META).getValue();
			MetaData meta = JsonUtil.fromJson(json, MetaData.class);
			MetaController.getInstance().addDocMeta(meta);
		}
		else if(uri.equals(UriDic.DELDOCMETA)){
			String json = request.getFirstHeader(EtcDic.META).getValue();
			MetaData meta = JsonUtil.fromJson(json, MetaData.class);
			MetaController.getInstance().delDocMeta(meta);
		}
		else if(uri.equals(UriDic.GETDOCMETA)){
			String json = request.getFirstHeader(EtcDic.META).getValue();
			MetaData meta = JsonUtil.fromJson(json, MetaData.class);
			List<MetaData> nMeta = MetaController.getInstance().getDocMeta(meta);
			response.addHeader(EtcDic.META, JsonUtil.toJson(nMeta));
		}
		else if(uri.equals(UriDic.ADDMETASETTING)){
			String json = request.getFirstHeader(EtcDic.METASETTING).getValue();
			MetaSetting setting = JsonUtil.fromJson(json, MetaSetting.class);
			MetaController.getInstance().addMetaSetting(setting);
		}
		else if(uri.equals(UriDic.DELMETASETTING)){
			String json = request.getFirstHeader(EtcDic.METASETTING).getValue();
			MetaSetting setting = JsonUtil.fromJson(json, MetaSetting.class);
			MetaController.getInstance().delMetaSetting(setting);
		}
		else if(uri.equals(UriDic.GETMETASETTING)){
			String json = request.getFirstHeader(EtcDic.METASETTING).getValue();
			MetaSetting setting = JsonUtil.fromJson(json, MetaSetting.class);
			MetaSetting nSetting = MetaController.getInstance().getMetaSetting(setting);
			response.addHeader(EtcDic.METASETTING, JsonUtil.toJson(nSetting));
		}
		else if(uri.equals(UriDic.GETMETASETTINGLIST)){
			List<MetaSetting> list = MetaController.getInstance().getMetaSettingList();
			response.addHeader(EtcDic.METASETTING, JsonUtil.toJson(list));
		}
		else if(uri.equals(UriDic.ADDMETASUBSETTING)){
			String json = request.getFirstHeader(EtcDic.METASETTING).getValue();
			MetaSetting setting = JsonUtil.fromJson(json, MetaSetting.class);
			MetaController.getInstance().addMetaSubSetting(setting);
		}
		else if(uri.equals(UriDic.DELMETASUBSETTING)){
			String json = request.getFirstHeader(EtcDic.METASETTING).getValue();
			MetaSetting setting = JsonUtil.fromJson(json, MetaSetting.class);
			MetaController.getInstance().delMetaSubSetting(setting);
		}
	}

	@Override
	public User preCheck(HttpRequest request, HttpResponse response, String uri) throws Exception {
		return SecureController.getInstance().checkUser(request, response, uri);
	}

}
