package yukecm.handler;

import java.util.List;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import yukcommon.dic.EtcDic;
import yukcommon.dic.UriDic;
import yukcommon.model.Storage;
import yukcommon.model.User;
import yukcommon.net.AbsNetHandler;
import yukcommon.util.JsonUtil;
import yukecm.controller.SecureController;
import yukecm.controller.StorageController;

public class StorageHandler extends AbsNetHandler{
	@Override
	public void handleImpl(HttpRequest request, HttpResponse response, String uri,User user) throws Exception {
		String json = request.getFirstHeader(EtcDic.STORAGE).getValue();
		Storage storage = JsonUtil.fromJson(json, Storage.class);
		if (uri.equals(UriDic.ADDSTORAGE)) {
			String id = StorageController.getInstance().addStorage(storage);
			response.addHeader(EtcDic.RETID, id);
		} else if (uri.equals(UriDic.DELSTORAGE)) {
			StorageController.getInstance().delStorage(storage);
		} else if (uri.equals(UriDic.UPDSTORAGE)) {
			StorageController.getInstance().updStorage(storage);
		} else if (uri.equals(UriDic.GETSTORAGE)) {
			List<Storage> storageList = StorageController.getInstance().getStorageList();
			response.addHeader(EtcDic.STORAGE, JsonUtil.toJson(storageList));
		}
	}

	@Override
	public User preCheck(HttpRequest request, HttpResponse response, String uri) throws Exception {
		User user = SecureController.getInstance().checkUser(request, response, uri);
		SecureController.getInstance().checkSystemPermission(request,response,user,uri);
		return user;
	}
}
