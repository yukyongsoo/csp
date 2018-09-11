package yukecm.injecter.user;

import yukcommon.model.User;
import yukcommon.util.JsonUtil;
import yukecm.cache.inter.JsonPolicy;

public class UserJson  implements JsonPolicy<String, User>{

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
	public User returnValue(String value) throws Exception{
		return JsonUtil.fromJson(value, User.class);
	}

}
