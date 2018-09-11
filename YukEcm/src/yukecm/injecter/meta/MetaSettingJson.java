package yukecm.injecter.meta;

import yukcommon.model.meta.MetaSetting;
import yukcommon.util.JsonUtil;
import yukecm.cache.inter.JsonPolicy;

public class MetaSettingJson implements JsonPolicy<String, MetaSetting>{

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
	public MetaSetting returnValue(String value) throws Exception {
		return JsonUtil.fromJson(value, MetaSetting.class);
	}

}
