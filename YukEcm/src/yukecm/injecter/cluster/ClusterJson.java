package yukecm.injecter.cluster;

import yukcommon.model.Cluster;
import yukcommon.util.JsonUtil;
import yukecm.cache.inter.JsonPolicy;

public class ClusterJson implements JsonPolicy<String, Cluster>{

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
	public Cluster returnValue(String value) throws Exception {
		return JsonUtil.fromJson(value, Cluster.class);
	}

}
