package yukecm.cache;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import yukcommon.exception.NotNullAllowedException;

public class InMemCache<K, V>  implements Cache<K, V>{
	private Map<K, V> cache = new ConcurrentHashMap<K, V>();

	protected InMemCache(String name) {
		
	}

	@Override
	public void put(K key, V value) throws InterruptedException{
		if(key == null)
			throw new NotNullAllowedException("cache key cannot be null");
		if(value == null)
			throw new NotNullAllowedException("cache value cannot be null");
		cache.put(key, value);
	}

	@Override
	public V get(K key)  {
		return cache.get(key);
	}
	
	@Override
	public Set<Entry<K, V>> entry() {
		return cache.entrySet();
	}
	
	@Override
	public boolean isExist(K key) {
		if(cache.get(key) != null)
			return true;
		return false;
	}

	@Override
	public V remove(K key) throws InterruptedException   {
		return cache.remove(key);
	}

	@Override
	public Set<K> keySet() {
		return cache.keySet();
	}

	@Override
	public Collection<V> values() {
		return  cache.values();
	}

	@Override
	public void setSynchronized() {
		// nothing to do
	}

	@Override
	public void setDesyncronized() {
		// nothing to do
	}

	@Override
	public void clear() {
		cache.clear();
	}
}
