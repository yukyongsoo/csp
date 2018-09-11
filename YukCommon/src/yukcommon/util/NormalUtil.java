package yukcommon.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;

import yukcommon.dic.EtcDic;
import yukcommon.dic.type.PermissionType;
import yukcommon.model.fileitem.IFileItem;

public abstract class NormalUtil {
	private NormalUtil() {}
	
	public static String makeKey(String sep,String...strings){
		StringBuilder key = new StringBuilder();
		if(strings != null){
			for(int i =0; i < strings.length; i++){
				if(i == strings.length-1){
					key.append(strings[i]);
					break;
				}
				key.append(strings[i]);
				key.append(sep);
			}
		}
		return key.toString();
	}

	public static String makeKey(String sep,List<String> list){
		StringBuilder key = new StringBuilder();
		if(list != null){
			for(int i =0; i < list.size(); i++){
				if(i == list.size()-1){
					key.append(list.get(i));
					break;
				}
				key.append(list.get(i));
				key.append(sep);
			}
		}
		return key.toString();
	}
	
	public static String makePermissonToString(String sep, List<PermissionType> list) {
		StringBuilder key = new StringBuilder();
		if(list != null){
			for(int i =0; i < list.size(); i++){
				if(i == list.size()-1){
					key.append(list.get(i).toString());
					break;
				}
				key.append(list.get(i).toString());
				key.append(sep);
			}
		}
		return key.toString();
	}

	public static String parseError(HttpEntity entity) throws IOException{
		byte[] b = new byte[entity.getContent().available()];
		int read = entity.getContent().read(b);
		if(read > 0)
			return new String(b,EtcDic.CHARSET);
		else
			return "Error";
	}

	public static void makeError(HttpResponse response, String message){
		StringEntity entity;
		if(message == null){
			entity = new StringEntity("Null Pointed Exception. maybe client not include needed Header","UTF-8");
		}
		else
			entity = new StringEntity(message,EtcDic.CHARSET);
		response.setEntity(entity);
	}

	public static void moveFile(IFileItem item,String filePath) throws IOException  {
		if (item.isInMemory()) {
			FileUtils.copyInputStreamToFile(item.getInputStream(), new File(filePath));
		} else {
			FileUtils.moveFile(new File(item.getStoreLocation()), new File(filePath));
		}
	}


}
