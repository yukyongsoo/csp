package yukecm.injecter.cluster;

import yukcommon.dic.type.CacheWorkType;
import yukcommon.model.Cluster;
import yukecm.cache.inter.ReciveHandler;
import yukecm.config.BaseProperty;

public class ClusterInjectorHandler implements ReciveHandler<String, Cluster>{
	@Override
	public void handle(String k, Cluster v, CacheWorkType workType) throws Exception {
		if(v.getApNum() == BaseProperty.getInstance().apNum)
			v.setMe(true);
		else 
			v.setMe(false);
	}
}
