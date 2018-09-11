package YukEcmWeb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import YukEcmWeb.SessionBean;
import yukcommon.dic.EtcDic;
import yukcommon.dic.UriDic;
import yukcommon.model.Folder;
import yukcommon.util.JsonUtil;
import yukecmapi.FolderApi;

@RestController
public class WebFolderController {
	@Autowired(required = true)
    private SessionBean session;

	@RequestMapping(UriDic.GETFOLDERLIST)
	public List<Folder> getFolderList() throws Exception {
		FolderApi api = new FolderApi(session.getConn());
		return api.getFolderList();
	}
	
	@RequestMapping(UriDic.GETFOLDERCHILDLIST)
	public List<Folder> getFolderChildList(@RequestHeader(EtcDic.FOLDER) String json) throws Exception {
		Folder folder = JsonUtil.fromJson(json, Folder.class);
		FolderApi api = new FolderApi(session.getConn());
		return api.getFolderChildList(folder.getId());
	}
	
	@RequestMapping(UriDic.ADDFOLDER)
	public String addFolder(@RequestHeader(EtcDic.FOLDER) String json) throws Exception {
		Folder folder = JsonUtil.fromJson(json, Folder.class);
		FolderApi api = new FolderApi(session.getConn());
		return api.addFolder(folder.getName(), folder.getParentId(), folder.getAclId(),folder.getDescr());
	}
	
	@RequestMapping(UriDic.DELFOLDER)
	public void delFolder(@RequestHeader(EtcDic.FOLDER) String json) throws Exception {
		Folder folder = JsonUtil.fromJson(json, Folder.class);
		FolderApi api = new FolderApi(session.getConn());
		api.delFolder(folder.getId());
	}
	
	@RequestMapping(UriDic.UPDFOLDER)
	public void updAcl(@RequestHeader(EtcDic.FOLDER) String json) throws Exception {
		Folder folder = JsonUtil.fromJson(json, Folder.class);
		FolderApi api = new FolderApi(session.getConn());
		api.updFolder(folder.getId(),folder.getName(), folder.getParentId(), folder.getAclId());
	}
}
