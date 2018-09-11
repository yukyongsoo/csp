package yukecmapi;

import java.util.List;

import yukcommon.dic.UriDic;
import yukcommon.exception.NotNullAllowedException;
import yukcommon.model.Rule;

public class RuleApi extends AbsApi{
	public RuleApi(EcmConnection conn) {
		super(conn);
	}

	public String addRule(Rule rule) throws Exception{
		if(rule == null)
			throw new NotNullAllowedException("addRule needed Rule Object");
		RuleApiImpl impl = EcmApiFactory.getRuleApi(conn,UriDic.ADDRULE);
		return impl.addRule(rule);
	}

	public void updRule(Rule rule) throws Exception{
		if(rule == null)
			throw new NotNullAllowedException("updRule needed Rule Object");
		RuleApiImpl impl = EcmApiFactory.getRuleApi(conn,UriDic.UPDRULE);
		impl.updRule(rule);
	}

	public void delRule(String id) throws Exception{
		RuleApiImpl impl = EcmApiFactory.getRuleApi(conn,UriDic.DELRULE);
		impl.delRule(id);
	}

	public List<Rule> getRule() throws Exception{
		RuleApiImpl impl = EcmApiFactory.getRuleApi(conn,UriDic.GETRULE);
		return impl.getRule();
	}
}
