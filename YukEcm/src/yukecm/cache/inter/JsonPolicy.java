package yukecm.cache.inter;

public interface JsonPolicy<K,V> {
	public String makeKey(Object obj) throws Exception;
	public String makeValue(Object obj) throws Exception;
	public K returnKey(String key) throws Exception;
	public V returnValue(String value) throws Exception;
}
