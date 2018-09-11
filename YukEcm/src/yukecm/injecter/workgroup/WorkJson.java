package yukecm.injecter.workgroup;

import yukcommon.model.WorkingGroup;
import yukcommon.util.JsonUtil;
import yukecm.cache.inter.JsonPolicy;

public class WorkJson implements JsonPolicy<String, WorkingGroup>{

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
	public WorkingGroup returnValue(String value) throws Exception{
		return JsonUtil.fromJson(value, WorkingGroup.class);
	}

}
