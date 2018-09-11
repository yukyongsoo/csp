package yukcommon.util;

import java.lang.reflect.Type;
import java.util.EnumMap;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import yukcommon.dic.type.PermissionType;
import yukcommon.exception.NotNullAllowedException;
import yukcommon.model.Ace;
import yukcommon.model.Acl;
import yukcommon.model.Cluster;
import yukcommon.model.Doc;
import yukcommon.model.Folder;
import yukcommon.model.Group;
import yukcommon.model.Pipe;
import yukcommon.model.Repository;
import yukcommon.model.Rule;
import yukcommon.model.Storage;
import yukcommon.model.User;
import yukcommon.model.WorkingGroup;
import yukcommon.model.lifecycle.LifeCycleSetting;
import yukcommon.model.meta.MetaData;
import yukcommon.model.meta.MetaSetting;
import yukcommon.model.meta.SubMetaSettingData;

public abstract class JsonUtil {
	private JsonUtil() {}
	
	private static Gson gson = new Gson();
	public static final Type ISMAP = new TypeToken<ConcurrentSkipListMap<Integer, String>>(){}.getType();
	public static final Type SMETAMAP = new TypeToken<ConcurrentSkipListMap<String, SubMetaSettingData>>(){}.getType();
	public static final Type SACEMAP = new TypeToken<ConcurrentSkipListMap<String, Ace>>(){}.getType();
	public static final Type PERBMAP = new TypeToken<EnumMap<PermissionType, Boolean>>(){}.getType();
	public static final Type LISTREPO = new TypeToken<List<Repository>>(){}.getType();
	public static final Type LISTPIPE = new TypeToken<List<Pipe>>(){}.getType();
	public static final Type LISTRULE = new TypeToken<List<Rule>>(){}.getType();
	public static final Type LISTDOC = new TypeToken<List<Doc>>(){}.getType();
	public static final Type LISTLCSETTING = new TypeToken<List<LifeCycleSetting>>(){}.getType();
	public static final Type LISTMETA = new TypeToken<List<MetaData>>(){}.getType();
	public static final Type LISTMETASETTING = new TypeToken<List<MetaSetting>>(){}.getType();
	public static final Type LISTUSER = new TypeToken<List<User>>(){}.getType();
	public static final Type LISTACL = new TypeToken<List<Acl>>(){}.getType();
	public static final Type LISTSTR = new TypeToken<List<Storage>>(){}.getType();
	public static final Type LISTWORK = new TypeToken<List<WorkingGroup>>(){}.getType();
	public static final Type LISTCLUSTER = new TypeToken<List<Cluster>>(){}.getType();
	public static final Type LISTACE = new TypeToken<List<Ace>>(){}.getType();
	public static final Type LISTGROUP = new TypeToken<List<Group>>(){}.getType();
	public static final Type LISTFOLDER = new TypeToken<List<Folder>>(){}.getType();
	
	public static String toJson(Object obj){
		if(obj == null)
			throw new NotNullAllowedException("serialized json object can't be null.");
		return gson.toJson(obj);
	}

	public static <T> T fromJson(String json,Class<T> c){
		if(json == null || json.isEmpty())
			throw new NotNullAllowedException("deserialized json object can't be null.");
		return gson.fromJson(json, c);
	}

	public static <T> T fromJson(String json,Type t){
		if(json == null  || json.isEmpty())
			throw new NotNullAllowedException("deserialized json object can't be null.");
		return gson.fromJson(json, t);
	}
}
