package yukecm.injecter.lifecycle;

import yukcommon.model.lifecycle.LifeCycleSetting;
import yukcommon.util.JsonUtil;
import yukecm.cache.inter.JsonPolicy;

public class LCSettingJson implements JsonPolicy<String, LifeCycleSetting>{

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
	public LifeCycleSetting returnValue(String value) throws Exception{
		return JsonUtil.fromJson(value, LifeCycleSetting.class);
	}
}
