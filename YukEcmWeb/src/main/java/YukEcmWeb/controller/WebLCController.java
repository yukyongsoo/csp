package YukEcmWeb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import YukEcmWeb.SessionBean;
import yukcommon.dic.EtcDic;
import yukcommon.dic.UriDic;
import yukcommon.model.lifecycle.LifeCycleInfo;
import yukcommon.model.lifecycle.LifeCycleSetting;
import yukcommon.util.JsonUtil;
import yukecmapi.LifeCycleApi;

@RestController
public class WebLCController {
	@Autowired(required = true)
    private SessionBean session;
	
	@RequestMapping(UriDic.GETLCSCH)
	public List<LifeCycleSetting> getLifeSch() throws Exception {
		LifeCycleApi api = new LifeCycleApi(session.getConn());
		return api.getLifeCycleSch();
	}
	
	@RequestMapping(UriDic.GETLCINFO)
	public LifeCycleInfo getLifeCycleInfo(@RequestHeader(EtcDic.LCINFO) String json) throws Exception {
		LifeCycleInfo info = JsonUtil.fromJson(json, LifeCycleInfo.class);
		LifeCycleApi api = new LifeCycleApi(session.getConn());
		return api.getLifeCycleInfo(info.getId());
	}
	
	@RequestMapping(UriDic.ADDLCSCH)
	public String addLifeSetting(@RequestHeader(EtcDic.LCSETTING) String json) throws Exception {
		LifeCycleSetting setting = JsonUtil.fromJson(json, LifeCycleSetting.class);
		LifeCycleApi api = new LifeCycleApi(session.getConn());
		return api.addLifeCycleSch(setting);
	}
	
	@RequestMapping(UriDic.DELLCSCH)
	public void delLifeSetting(@RequestHeader(EtcDic.LCSETTING) String json) throws Exception {
		LifeCycleSetting setting = JsonUtil.fromJson(json, LifeCycleSetting.class);
		LifeCycleApi api = new LifeCycleApi(session.getConn());
		api.delLifeCycleSch(setting.getId());
	}
	
	@RequestMapping(UriDic.STARTLC)
	public void startLifeCylce(@RequestHeader(EtcDic.LCINFO) String json) throws Exception {
		LifeCycleInfo info = JsonUtil.fromJson(json, LifeCycleInfo.class);
		LifeCycleApi api = new LifeCycleApi(session.getConn());
		api.startLifeCycle(info.getId());
	}
	
	
	@RequestMapping(UriDic.STOPLC)
	public void stopLifeCycle(@RequestHeader(EtcDic.LCINFO) String json) throws Exception {
		LifeCycleInfo info = JsonUtil.fromJson(json, LifeCycleInfo.class);
		LifeCycleApi api = new LifeCycleApi(session.getConn());
		api.stopLifeCycle(info.getId());
	}
}
