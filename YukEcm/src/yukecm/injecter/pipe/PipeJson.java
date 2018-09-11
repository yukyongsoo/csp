package yukecm.injecter.pipe;

import yukcommon.model.Pipe;
import yukcommon.util.JsonUtil;
import yukecm.cache.inter.JsonPolicy;

public class PipeJson implements JsonPolicy<String, Pipe>{

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
	public Pipe returnValue(String value) throws Exception{
		return JsonUtil.fromJson(value, Pipe.class);
	}
}
