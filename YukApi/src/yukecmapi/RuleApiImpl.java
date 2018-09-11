package yukecmapi;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import yukcommon.dic.EtcDic;
import yukcommon.model.Rule;
import yukcommon.net.NetClient;
import yukcommon.net.NetWrapper;
import yukcommon.util.JsonUtil;

public class RuleApiImpl extends AbsApiImpl {

	protected RuleApiImpl(NetClient client, NetWrapper wrap) {
		super(client, wrap);
	}

	public String addRule(Rule rule) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		rule.getName();
		rule.getType();
		wrap.addHeader(EtcDic.RULE, JsonUtil.toJson(rule));
		client.excute(wrap);
		return wrap.getHeaderValue(EtcDic.RETID);
	}

	public void updRule(Rule rule) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		rule.getName();
		rule.getType();
		wrap.addHeader(EtcDic.RULE, JsonUtil.toJson(rule));
		client.excute(wrap);
	}

	public void delRule(String id) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		Rule rule = new Rule();
		rule.setId(id);
		wrap.addHeader(EtcDic.RULE, JsonUtil.toJson(rule));
		client.excute(wrap);
	}

	public List<Rule> getRule() throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		Rule rule = new Rule();
		wrap.addHeader(EtcDic.RULE, JsonUtil.toJson(rule));
		client.excute(wrap);
		String json = wrap.getHeaderValue(EtcDic.RULE);
		return JsonUtil.fromJson(json, JsonUtil.LISTRULE);
	}
}
