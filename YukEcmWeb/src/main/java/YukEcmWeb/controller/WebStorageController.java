package YukEcmWeb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import YukEcmWeb.SessionBean;
import yukcommon.dic.EtcDic;
import yukcommon.dic.UriDic;
import yukcommon.model.Storage;
import yukcommon.util.JsonUtil;
import yukecmapi.StorageApi;

@RestController
public class WebStorageController {
	@Autowired(required = true)
    private SessionBean session;
	
	@RequestMapping(UriDic.GETSTORAGE)
	public List<Storage> getStorage() throws Exception {
		StorageApi api = new StorageApi(session.getConn());
		return api.getStorage();
	}
	
	@RequestMapping(UriDic.ADDSTORAGE)
	public String addStorage(@RequestHeader(EtcDic.STORAGE) String json) throws Exception {
		Storage storage = JsonUtil.fromJson(json, Storage.class);
		StorageApi api = new StorageApi(session.getConn());
		return api.addStorage(storage);
	}
	
	@RequestMapping(UriDic.DELSTORAGE)
	public void delStorage(@RequestHeader(EtcDic.STORAGE) String json) throws Exception {
		Storage storage = JsonUtil.fromJson(json, Storage.class);
		StorageApi api = new StorageApi(session.getConn());
		api.delStorage(storage.getId());
	}
	
	@RequestMapping(UriDic.UPDSTORAGE)
	public void updStorage(@RequestHeader(EtcDic.STORAGE) String json) throws Exception {
		Storage storage = JsonUtil.fromJson(json, Storage.class);
		StorageApi api = new StorageApi(session.getConn());
		api.updStorage(storage);
	}
}
