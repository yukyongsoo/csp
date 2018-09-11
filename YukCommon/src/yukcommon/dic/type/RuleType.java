package yukcommon.dic.type;

import com.google.gson.annotations.SerializedName;

public enum RuleType {
	@SerializedName("INITRULE")
	INITRULE,
	@SerializedName("MIGRULE")
	MIGRULE,
	@SerializedName("DESRULE")
	DESRULE;
}
