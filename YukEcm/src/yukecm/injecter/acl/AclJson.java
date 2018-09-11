package yukecm.injecter.acl;

import yukcommon.model.Acl;
import yukcommon.util.JsonUtil;
import yukecm.cache.inter.JsonPolicy;

public class AclJson implements JsonPolicy<String, Acl>{
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
	public Acl returnValue(String value) throws Exception{
		return JsonUtil.fromJson(value, Acl.class);
	}
}
