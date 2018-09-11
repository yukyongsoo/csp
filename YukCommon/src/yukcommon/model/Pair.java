package yukcommon.model;

import yukcommon.exception.NotNullAllowedException;
import yukcommon.exception.WrongOperationException;

public class Pair<K,V> extends AbsModel{
	private K k;
	private V v;
	
	public K getKey() {
		if(k == null)
			throw new WrongOperationException("pair key is null");
		return k;
	}
	
	public V getValue() {
		if(v == null)
			throw new WrongOperationException("pair value is null");
		return v;
	}
	
	public void set(K k,V v) {
		if(k == null || v == null)
			throw new NotNullAllowedException("key,value can't be null");
		this.k= k;
		this.v = v;
	}
	
	
}
