package yukcommon.dic.type;

import com.google.gson.annotations.SerializedName;

public enum SubMetaSettingType {
	@SerializedName("STRING")
	STRING,
	@SerializedName("INT")
	INT,
	@SerializedName("DATE")
	DATE,
	@SerializedName("LONG")
	LONG,
	@SerializedName("FLOAT")
	FLOAT,
	@SerializedName("BOOLEAN")
	BOOLEAN,
	@SerializedName("BYTE")
	BYTE
}
