package yukecm.cache.inner;

import yukcommon.dic.type.CacheType;
import yukcommon.exception.NotSupportException;

public abstract class CacheProperty {
	public static CacheType type;
	private static SendingThread thread;
	
	private CacheProperty(){}

	public static void setProp(CacheType type) {
		CacheProperty.type = type;
		if(CacheType.MEMORY == type){
			//do not thing
		}
		else if(CacheType.CLUSTER == type){
			if(thread == null){
				thread = new SendingThread();
				thread.start();
			}
		}
		else
			throw new NotSupportException("This Type '" + type + "' not supported.");
	}
}
