package yukecm.injecter.pipe;

import yukcommon.dic.type.CacheWorkType;
import yukcommon.model.Pipe;
import yukecm.cache.inter.ReciveHandler;
import yukecm.etc.EcmUtil;

public class PipeInjectorHandler implements ReciveHandler<String, Pipe>{
	@Override
	public void handle(String k, Pipe v, CacheWorkType workType) throws Exception {
		if(CacheWorkType.ADD == workType)
			EcmUtil.setPipeAdaptor(v);
	}
}
