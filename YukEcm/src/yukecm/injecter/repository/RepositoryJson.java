package yukecm.injecter.repository;

import yukcommon.model.Repository;
import yukcommon.util.JsonUtil;
import yukecm.cache.inter.JsonPolicy;

public class RepositoryJson implements JsonPolicy<String, Repository>{

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
	public Repository returnValue(String value) throws Exception{
		return JsonUtil.fromJson(value, Repository.class);
	}
}
