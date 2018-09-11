package yukecm.cache;

import yukcommon.exception.ReplicateException;
import yukecm.cache.inter.ReplicatedPolicy;

public class RepliPolicyWrapper {
	ReplicatedPolicy policy;

	protected RepliPolicyWrapper(ReplicatedPolicy policy) {
		if(policy == null)
			throw new NullPointerException("Sending policy cannot be null");
		this.policy = policy;
	}

	public void sendReplicate(CacheModel mayFail, String address) {
		try {
			policy.sendReplicate(mayFail, address);
		} catch (Exception e) {
			throw new ReplicateException("Replicate Sending Fail", e);
		}
	}
}
