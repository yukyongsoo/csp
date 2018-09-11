package yukecm.handler;

import java.util.List;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import yukcommon.dic.EtcDic;
import yukcommon.dic.UriDic;
import yukcommon.model.Folder;
import yukcommon.model.User;
import yukcommon.net.AbsNetHandler;
import yukcommon.util.JsonUtil;
import yukecm.controller.FolderController;
import yukecm.controller.SecureController;

public class FolderHandler extends AbsNetHandler{
	@Override
	public void handleImpl(HttpRequest request, HttpResponse response, String uri,User user) throws Exception {
		String json = request.getFirstHeader(EtcDic.FOLDER).getValue();
		Folder folder = JsonUtil.fromJson(json, Folder.class);
		if (uri.equals(UriDic.ADDFOLDER)) {
			String id = FolderController.getInstance().addFolder(folder,user);
			response.addHeader(EtcDic.RETID, id);
		} else if (uri.equals(UriDic.GETFOLDERLIST)) {
			List<Folder> list = FolderController.getInstance().getFolderList(folder,user);
			response.addHeader(EtcDic.FOLDER, JsonUtil.toJson(list));
		}  else if (uri.equals(UriDic.GETFOLDERCHILDLIST)) {
			List<Folder> list = FolderController.getInstance().getFolderChildList(folder,user);
			response.addHeader(EtcDic.FOLDER, JsonUtil.toJson(list));
		} 
		else if (uri.equals(UriDic.DELFOLDER)) {
			FolderController.getInstance().delFolder(folder,user);
		} else if(uri.equals(UriDic.UPDFOLDER)){
			FolderController.getInstance().updFolder(folder,user);
		}
	}

	@Override
	public User preCheck(HttpRequest request, HttpResponse response, String uri) throws Exception {
		return SecureController.getInstance().checkUser(request, response, uri);
	}

}
