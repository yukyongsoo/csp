package YukEcmWeb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import YukEcmWeb.SessionBean;
import yukcommon.dic.EtcDic;
import yukcommon.dic.UriDic;
import yukcommon.model.Rule;
import yukcommon.util.JsonUtil;
import yukecmapi.RuleApi;

@RestController
public class WebRuleController {
	@Autowired(required = true)
    private SessionBean session;
	
	@RequestMapping(UriDic.GETRULE)
	public List<Rule> getRule() throws Exception {
		RuleApi api = new RuleApi(session.getConn());
		return api.getRule();
	}
	
	@RequestMapping(UriDic.ADDRULE)
	public String addRule(@RequestHeader(EtcDic.RULE) String json) throws Exception {
		Rule rule = JsonUtil.fromJson(json, Rule.class);
		RuleApi api = new RuleApi(session.getConn());
		return api.addRule(rule);
	}
	
	@RequestMapping(UriDic.DELRULE)
	public void delRule(@RequestHeader(EtcDic.RULE) String json) throws Exception {
		Rule rule = JsonUtil.fromJson(json, Rule.class);
		RuleApi api = new RuleApi(session.getConn());
		api.delRule(rule.getId());
	}
	
	@RequestMapping(UriDic.UPDRULE)
	public void updRule(@RequestHeader(EtcDic.RULE) String json) throws Exception {
		Rule rule = JsonUtil.fromJson(json, Rule.class);
		RuleApi api = new RuleApi(session.getConn());
		api.updRule(rule);
	}
}
