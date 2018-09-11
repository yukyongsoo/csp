package yukecm.cache.inter;

import yukcommon.dic.type.CacheWorkType;

public interface ReciveHandler<K, V> {
	public void handle(K k, V v, CacheWorkType workType) throws Exception;
}
