package yukcommon.dic.type;

import com.google.gson.annotations.SerializedName;

public enum OnErrorType {
	@SerializedName("EXIST")
	EXIST,
	@SerializedName("NOTEXIST")
	NOTEXIST,
	@SerializedName("NONE")
	NONE
}
