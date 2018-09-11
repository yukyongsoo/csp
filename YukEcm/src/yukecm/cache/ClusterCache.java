package yukecm.cache;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import yukcommon.dic.type.CacheWorkType;
import yukcommon.exception.NotNullAllowedException;
import yukcommon.exception.WrongOperationException;
import yukecm.cache.inner.QueueManager;
import yukecm.cache.inter.JsonPolicy;
import yukecm.cache.inter.ReciveHandler;

public class ClusterCache<K, V>  implements Cache<K, V>{
	private String name = "";
	private Map<K, V> cache = new ConcurrentHashMap<K, V>();
	private JsonPolicyWrapper<K, V> json;
	private ReciveHandlerWarrapper<K,V> recive;
	private boolean sync = true;

	protected ClusterCache(String name, JsonPolicy<K,V> json, ReciveHandler<K,V> handler) {
		this.name = name;
		this.json = new JsonPolicyWrapper<K, V>(json);
		this.recive = new ReciveHandlerWarrapper<K,V>(handler);
	}

	protected ClusterCache(String name, JsonPolicy<K,V> json) {
		this.name = name;
		this.json = new JsonPolicyWrapper<K, V>(json);
	}

	@Override
	public void put(K key, V value) throws InterruptedException{
		if(key == null)
			throw new NotNullAllowedException("cache key cannot be null");
		if(value == null)
			throw new NotNullAllowedException("cache value cannot be null");
		CacheModel model = new CacheModel();
		model.setName(name);
		model.setWorkType(CacheWorkType.ADD);
		model.setKey(json.makeKey(key));
		model.setValue(json.makeValue(value));
		if(sync)
			QueueManager.getInstance().putTarget(model);
		cache.put(key, value);
	}

	@Override
	public V get(K key) {
		return cache.get(key);
	}

	@Override
	public V remove(K key) throws InterruptedException   {
		V v = cache.remove(key);
		if(v != null) {
			CacheModel model = new CacheModel();
			model.setName(name);
			model.setWorkType(CacheWorkType.REMOVE);
			model.setKey(json.makeKey(key));
			model.setValue(json.makeValue(v));
			if (sync)
				QueueManager.getInstance().putTarget(model);
			return v;
		}
		throw new WrongOperationException("key is not in cache." + key);
	}
	
	@Override
	public boolean isExist(K key) {
		if(cache.get(key) != null)
			return true;
		return false;
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
	public Set<Entry<K, V>> entry() {
		return cache.entrySet();
	}

	public void putRecive(CacheModel poll) {
		K returnKey = json.returnKey(poll.getKey());
		V returnValue = json.returnValue(poll.getValue());
		if(recive != null)
			recive.handleEvent(returnKey,returnValue,poll.getWorkType());
		if(poll.getWorkType() == CacheWorkType.ADD)
			cache.put(returnKey, returnValue);
		else if(poll.getWorkType() == CacheWorkType.REMOVE)
			cache.remove(returnKey);
	}

	@Override
	public void setSynchronized() {
		sync = true;
	}

	@Override
	public void setDesyncronized() {
		sync = false;
	}

	@Override
	public void clear() throws InterruptedException {
		for(K key : keySet())
			remove(key);
	}
}
