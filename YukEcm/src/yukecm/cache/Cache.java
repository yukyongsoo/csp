package yukecm.cache;

import java.util.Collection;
import java.util.Map.Entry;
import java.util.Set;

public interface Cache<K,V> {
	public void put(K key, V value) throws InterruptedException;
	public V remove(K key) throws InterruptedException;
	public V get(K key);
	public boolean isExist(K key);
	public Set<K> keySet();
	public Collection<V> values();
	public Set<Entry<K, V>> entry();
	public void setSynchronized();
	public void setDesyncronized();
	public void clear() throws InterruptedException;
}
