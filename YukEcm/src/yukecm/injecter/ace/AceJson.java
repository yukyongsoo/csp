package yukecm.injecter.ace;

import java.util.Map;

import yukcommon.model.Ace;
import yukcommon.util.JsonUtil;
import yukecm.cache.inter.JsonPolicy;

public class AceJson implements JsonPolicy<String, Map<String, Ace>>{

	@Override
	public String makeKey(Object obj) throws Exception {
		return JsonUtil.toJson(obj);
	}

	@Override
	public String makeValue(Object obj) throws Exception {
		return JsonUtil.toJson(obj);
	}

	@Override
	public String returnKey(String key) throws Exception {
		return JsonUtil.fromJson(key, String.class);
	}

	@Override
	public Map<String, Ace> returnValue(String value) throws Exception {
		return JsonUtil.fromJson(value, JsonUtil.SACEMAP);
	}

}
