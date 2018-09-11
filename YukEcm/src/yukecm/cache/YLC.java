package yukecm.cache;

import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import yukcommon.dic.type.CacheType;
import yukcommon.exception.DuplicatedException;
import yukcommon.exception.NotInitedException;
import yukcommon.util.LoggerUtil;
import yukecm.cache.inner.CacheProperty;
import yukecm.cache.inter.JsonPolicy;
import yukecm.cache.inter.ReciveHandler;
import yukecm.cache.inter.ReplicatedPolicy;
import yukecm.config.BaseProperty;
import yukecm.controller.ClusterController;

public abstract class YLC {
	private YLC(){}

	private static Map<String, Cache> cacheMap = new ConcurrentHashMap<String, Cache>();
	private static boolean init;
	private static RepliPolicyWrapper repl;

	public static RepliPolicyWrapper getRepl() {
		return repl;
	}

	public static void init(CacheType type,ReplicatedPolicy policy) throws SQLException, InterruptedException {
		CacheProperty.setProp(type);
		repl = new RepliPolicyWrapper(policy);
		init = true;
		ClusterController.getInstance().init(BaseProperty.getInstance().apNum);
	}

	public static void setSynchronized(String cacheName) {
		if(!init)
			throw new NotInitedException("Cache not inited");
		getCache(cacheName).setSynchronized();
	}

	public static void setDesyncronized(String cacheName) {
		if(!init)
			throw new NotInitedException("Cache not inited");
		getCache(cacheName).setDesyncronized();
	}

	public static <K, V> Cache<K, V> makeCache(String name,JsonPolicy<K,V> json) {
		if(!init)
			throw new NotInitedException("Cache not inited");
		if(cacheMap.get(name) != null)
			throw new DuplicatedException("Cache Name " + name + " already exist");
		Cache<K, V> cache = null;
		if(CacheType.CLUSTER == CacheProperty.type)
			cache = new ClusterCache<K, V>(name, json);
		else if(CacheType.MEMORY == CacheProperty.type)
			cache = new InMemCache<K, V>(name);
		cacheMap.put(name, cache);
		return cache;
	}

	public static <K, V> Cache<K, V> makeCache(String name,JsonPolicy<K,V> json,ReciveHandler<K,V> handler) {
		if(!init)
			throw new NotInitedException("Cache not inited");
		Cache<K, V> cache = null;
		if(CacheType.CLUSTER == CacheProperty.type)
			cache = new ClusterCache<K, V>(name, json, handler);
		else if(CacheType.MEMORY == CacheProperty.type)
			cache = new InMemCache<K, V>(name);
		cacheMap.put(name, cache);
		return cache;
	}

	public static <K, V> Cache<K, V> getCache(String name)  {
		if(!init)
			throw new NotInitedException("Cache not inited");
		return cacheMap.get(name);
	}

	public static void removeCache(String name)  {
		if(!init)
			throw new NotInitedException("Cache not inited");
		cacheMap.remove(name);
	}

	public static void putCacheModel(CacheModel model) {
		if(model != null){
			Cache cache = YLC.getCache(model.getName());
			((ClusterCache)cache).putRecive(model);
			LoggerUtil.debug(YLC.class, "Cluter Repicataed Data Recived." + model.getName(), null);
		}
	}
}
