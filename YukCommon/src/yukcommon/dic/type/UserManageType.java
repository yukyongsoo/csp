package yukcommon.dic.type;

import com.google.gson.annotations.SerializedName;

public enum UserManageType {
	@SerializedName("ADMIN")
	ADMIN,
	@SerializedName("SYSTEM")
	SYSTEM,
	@SerializedName("PERMISSION")
	PERMISSION,
	@SerializedName("DOCADMIN")
	DOCADMIN,
	@SerializedName("PUBLIC")
	PUBLIC
}
