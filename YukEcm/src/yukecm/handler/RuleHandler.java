package yukecm.handler;

import java.util.List;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import yukcommon.dic.EtcDic;
import yukcommon.dic.UriDic;
import yukcommon.model.Rule;
import yukcommon.model.User;
import yukcommon.net.AbsNetHandler;
import yukcommon.util.JsonUtil;
import yukecm.controller.RuleController;
import yukecm.controller.SecureController;

public class RuleHandler extends AbsNetHandler{
	@Override
	public void handleImpl(HttpRequest request, HttpResponse response, String uri,User user) throws Exception {
		String json = request.getFirstHeader(EtcDic.RULE).getValue();
		Rule rule = JsonUtil.fromJson(json, Rule.class);
		if (uri.equals(UriDic.ADDRULE)) {
			String id = RuleController.getInstance().addRule(rule);
			response.addHeader(EtcDic.RETID, id);
		} else if (uri.equals(UriDic.UPDRULE)) {
			RuleController.getInstance().updRule(rule);
		} else if (uri.equals(UriDic.DELRULE)) {
			RuleController.getInstance().delRule(rule);
		} else if (uri.equals(UriDic.GETRULE)) {
			List<Rule> ruleList = RuleController.getInstance().getRuleList();
			response.addHeader(EtcDic.RULE,JsonUtil.toJson(ruleList));
		}
	}

	@Override
	public User preCheck(HttpRequest request, HttpResponse response, String uri) throws Exception {
		User user = SecureController.getInstance().checkUser(request, response, uri);
		SecureController.getInstance().checkSystemPermission(request,response,user,uri);
		return user;
	}
}
