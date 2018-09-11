package yukecm.injecter.rule;

import yukcommon.model.Rule;
import yukcommon.util.JsonUtil;
import yukecm.cache.inter.JsonPolicy;

public class RuleJson implements JsonPolicy<String, Rule>{

	@Override
	public String makeKey(Object obj) throws Exception{
		return JsonUtil.toJson(obj);
	}

	@Override
	public String makeValue(Object obj) throws Exception{
		return JsonUtil.toJson(obj);
	}

	@Override
	public String returnKey(String key) throws Exception{
		return JsonUtil.fromJson(key, String.class);
	}

	@Override
	public Rule returnValue(String value) throws Exception{
		return JsonUtil.fromJson(value, Rule.class);
	}
}
