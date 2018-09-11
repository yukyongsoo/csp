package yukecm.cache;

import yukcommon.dic.type.CacheWorkType;
import yukcommon.exception.HandlerException;
import yukecm.cache.inter.ReciveHandler;

public class ReciveHandlerWarrapper<K, V> {
	private ReciveHandler<K, V> handler;

	public ReciveHandlerWarrapper(ReciveHandler<K, V> handler) {
		if(handler == null)
			throw new NullPointerException("handler cannot be null. if you want using it");
		this.handler = handler;
	}

	public void handleEvent(K returnKey, V returnValue, CacheWorkType workType) {
		try {
			handler.handle(returnKey, returnValue,workType);
		} catch (Exception e) {
			throw new HandlerException("Handle Recive Event Fail", e);
		}
	}

}
