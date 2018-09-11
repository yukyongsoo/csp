package yukecm.injecter.lifecycle;

import yukcommon.dic.type.CacheWorkType;
import yukcommon.model.lifecycle.LifeCycleSetting;
import yukecm.cache.inter.ReciveHandler;
import yukecm.lifecycle.LifeCycleManager;

public class LCSettingInjectorHandler implements ReciveHandler<String, LifeCycleSetting>{
	@Override
	public void handle(String k, LifeCycleSetting v, CacheWorkType workType) throws Exception {
		if(CacheWorkType.ADD == workType){			
			LifeCycleManager.getInstance().delLifeCycleSch(v);		
			LifeCycleManager.getInstance().addLifeCycleSch(v);			
		}
		else if(CacheWorkType.REMOVE == workType)
			LifeCycleManager.getInstance().delLifeCycleSch(v);

	}
}
