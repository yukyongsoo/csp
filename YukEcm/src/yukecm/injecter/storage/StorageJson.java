package yukecm.injecter.storage;

import yukcommon.model.Storage;
import yukcommon.util.JsonUtil;
import yukecm.cache.inter.JsonPolicy;

public class StorageJson implements JsonPolicy<String, Storage>{

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
	public Storage returnValue(String value) throws Exception{
		return JsonUtil.fromJson(value, Storage.class);
	}

}
