package YukEcmWeb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import YukEcmWeb.SessionBean;
import yukcommon.dic.EtcDic;
import yukcommon.dic.UriDic;
import yukcommon.model.WorkingGroup;
import yukcommon.util.JsonUtil;
import yukecmapi.WorkingApi;

@RestController
public class WebWorkingController {
	@Autowired(required = true)
    private SessionBean session;
	
	@RequestMapping(UriDic.GETWORK)
	public List<WorkingGroup> getWork() throws Exception {
		WorkingApi api = new WorkingApi(session.getConn());
		return api.getWorkingList();
	}
	
	@RequestMapping(UriDic.ADDWORK)
	public String addWork(@RequestHeader(EtcDic.WORKINGGROUP) String json) throws Exception {
		WorkingGroup work = JsonUtil.fromJson(json, WorkingGroup.class);
		WorkingApi api = new WorkingApi(session.getConn());
		return api.addWork(work.getName(), work.isAudit());
	}
	
	@RequestMapping(UriDic.DELWORK)
	public void delWork(@RequestHeader(EtcDic.WORKINGGROUP) String json) throws Exception {
		WorkingGroup work = JsonUtil.fromJson(json, WorkingGroup.class);
		WorkingApi api = new WorkingApi(session.getConn());
		api.delWork(work.getId());
	}
	
	@RequestMapping(UriDic.UPDWORK)
	public void updWork(@RequestHeader(EtcDic.WORKINGGROUP) String json) throws Exception {
		WorkingGroup work = JsonUtil.fromJson(json, WorkingGroup.class);
		WorkingApi api = new WorkingApi(session.getConn());
		api.updWork(work.getId(), work.getName(), work.isAudit());
	}
	
	@RequestMapping(UriDic.GETWORKRULE)
	public WorkingGroup getPipe(@RequestHeader(EtcDic.WORKINGGROUP) String json) throws Exception {
		WorkingGroup work = JsonUtil.fromJson(json, WorkingGroup.class);
		WorkingApi api = new WorkingApi(session.getConn());
		return api.getWorkRule(work.getId());
	}
	
	@RequestMapping(UriDic.ADDWORKRULE)
	public void addWorkRule(@RequestHeader(EtcDic.WORKINGGROUP) String json) throws Exception {
		WorkingGroup work = JsonUtil.fromJson(json, WorkingGroup.class);
		WorkingApi api = new WorkingApi(session.getConn());		
		api.addWorkRule(work.getId(), work.getTempId(), work.getTempType());
	}
	
	@RequestMapping(UriDic.DELWORKRULE)
	public void delWorkRule(@RequestHeader(EtcDic.WORKINGGROUP) String json) throws Exception {
		WorkingGroup work = JsonUtil.fromJson(json, WorkingGroup.class);
		WorkingApi api = new WorkingApi(session.getConn());
		api.delWorkRule(work.getId(), work.getTempId(),work.getTempType());
	}
}
