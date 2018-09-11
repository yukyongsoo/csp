package yukecm.injecter.group;

import yukcommon.model.Group;
import yukcommon.util.JsonUtil;
import yukecm.cache.inter.JsonPolicy;

public class GroupJson implements JsonPolicy<String, Group>{

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
	public Group returnValue(String value) throws Exception {
		return JsonUtil.fromJson(value, Group.class);
	}
}
