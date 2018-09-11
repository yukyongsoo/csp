package yukecm.cache.inter;

import yukecm.cache.CacheModel;

public interface ReplicatedPolicy {
	public void sendReplicate(CacheModel model, String address) throws Exception;
}
