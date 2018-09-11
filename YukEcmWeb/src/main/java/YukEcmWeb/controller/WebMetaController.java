package YukEcmWeb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import YukEcmWeb.SessionBean;
import yukcommon.dic.EtcDic;
import yukcommon.dic.UriDic;
import yukcommon.model.meta.MetaSetting;
import yukcommon.util.JsonUtil;
import yukecmapi.MetaApi;

@RestController
public class WebMetaController {
	@Autowired(required = true)
    private SessionBean session;

	@RequestMapping(UriDic.GETMETASETTINGLIST)
	public List<MetaSetting> getMetaSetting() throws Exception {
		MetaApi api = new MetaApi(session.getConn());
		return api.getMetaSettingList();
	}
	
	@RequestMapping(UriDic.ADDMETASETTING)
	public void addMetaSetting(@RequestHeader(EtcDic.METASETTING) String json) throws Exception {
		MetaSetting setting = JsonUtil.fromJson(json, MetaSetting.class);
		MetaApi api = new MetaApi(session.getConn());
		api.addMetaSetting(setting);
	}
	
	@RequestMapping(UriDic.DELMETASETTING)
	public void delMetaSetting(@RequestHeader(EtcDic.METASETTING) String json) throws Exception {
		MetaSetting setting = JsonUtil.fromJson(json, MetaSetting.class);
		MetaApi api = new MetaApi(session.getConn());
		api.delMetaSetting(setting.getName());
	}
	
	@RequestMapping(UriDic.ADDMETASUBSETTING)
	public void addMetaSubSetting(@RequestHeader(EtcDic.METASETTING) String json) throws Exception {
		MetaSetting setting = JsonUtil.fromJson(json, MetaSetting.class);
		MetaApi api = new MetaApi(session.getConn());
		api.addMetaSubSetting(setting);
	}
	
	@RequestMapping(UriDic.DELMETASUBSETTING)
	public void delMetaSubSetting(@RequestHeader(EtcDic.METASETTING) String json) throws Exception {
		MetaSetting setting = JsonUtil.fromJson(json, MetaSetting.class);
		MetaApi api = new MetaApi(session.getConn());
		api.delMetaSubSetting(setting);
	}
}
