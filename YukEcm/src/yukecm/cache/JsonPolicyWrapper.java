package yukecm.cache;

import yukcommon.exception.JsonException;
import yukecm.cache.inter.JsonPolicy;

public class JsonPolicyWrapper<K, V> {
	private JsonPolicy<K, V> policy;

	protected JsonPolicyWrapper(JsonPolicy<K, V> policy){
		if(policy == null)
			throw new NullPointerException("Json policy cannot be null");
		this.policy = policy;
	}

	public String makeKey(K key) {
		try {
			return policy.makeKey(key);
		} catch (Exception e) {
			throw new JsonException("Json Policy Has Error. Check Your Policy Code",e);
		}
	}

	public String makeValue(V value)  {
		try {
			return policy.makeValue(value);
		} catch (Exception e) {
			throw new JsonException("Json Policy Has Error. Check Your Policy Code",e);
		}
	}

	public K returnKey(String key) {
		try {
			return policy.returnKey(key);
		} catch (Exception e) {
			throw new JsonException("Json Policy Has Error. Check Your Policy Code",e);
		}
	}

	public V returnValue(String value)  {
		try {
			return policy.returnValue(value);
		} catch (Exception e) {
			throw new JsonException("Json Policy Has Error. Check Your Policy Code",e);
		}
	}
}
