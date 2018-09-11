package yukcommon.dic.type;

import com.google.gson.annotations.SerializedName;

public enum StorageType {
	@SerializedName("DISK")
	DISK,
	@SerializedName("CENTERA")
	CENTERA,
	@SerializedName("HCP")
	HCP,
	@SerializedName("NETAPP")
	NETAPP,
	@SerializedName("CLOUD")
	CLOUD
}
