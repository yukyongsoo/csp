package yukcommon.dic.type;

import com.google.gson.annotations.SerializedName;

public enum CacheType {
	@SerializedName("MEMORY")
	MEMORY,
	@SerializedName("CLUSTER")
	CLUSTER
}
