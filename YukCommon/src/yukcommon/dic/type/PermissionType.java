package yukcommon.dic.type;

import com.google.gson.annotations.SerializedName;

public enum PermissionType {
	@SerializedName("FOLDER")
	FOLDER,
	@SerializedName("ADDFOLDER")
	ADDFOLDER,
	@SerializedName("DELFOLDER")
	DELFOLDER,
	@SerializedName("UPDFOLDER")
	UPDFOLDER,
	@SerializedName("GETFOLDER")
	GETFOLDER,
	
	@SerializedName("FILE")
	FILE,
	@SerializedName("ADDFILE")
	ADDFILE,
	@SerializedName("DELFILE")
	DELFILE,
	@SerializedName("UPDFILE")
	UPDFILE,
	@SerializedName("GETFILE")
	GETFILE,
	@SerializedName("VERSIONFILE")
	VERSIONFILE
}
