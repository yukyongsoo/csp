package yukecmapi;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import yukcommon.dic.EtcDic;
import yukcommon.model.Folder;
import yukcommon.net.NetClient;
import yukcommon.net.NetWrapper;
import yukcommon.util.JsonUtil;

public class FolderApiImpl extends AbsApiImpl {
	protected FolderApiImpl(NetClient client, NetWrapper wrap) {
		super(client, wrap);
	}

	public String addFolder(String folderName, String parentId, String aclId, String descr) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		Folder folder = new Folder();
		folder.setName(folderName);
		folder.setParentId(parentId);
		folder.setDescr(descr);
		folder.setAclId(aclId);
		wrap.addHeader(EtcDic.FOLDER, JsonUtil.toJson(folder));
		client.excute(wrap);
		return wrap.getHeaderValue(EtcDic.RETID);
	}

	public void updFolder(String id, String newName, String parentId, String aclId) throws URISyntaxException, IOException, InterruptedException, ExecutionException  {
		Folder folder = new Folder();
		folder.setId(id);
		folder.setParentId(parentId);
		folder.setName(newName);
		folder.setAclId(aclId);
		wrap.addHeader(EtcDic.FOLDER, JsonUtil.toJson(folder));
		client.excute(wrap);
	}

	public void delFolder(String id) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		Folder folder = new Folder();
		folder.setId(id);
		wrap.addHeader(EtcDic.FOLDER, JsonUtil.toJson(folder));
		client.excute(wrap);
	}

	public List<Folder> getFolderChildList(String id) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		Folder folder = new Folder();
		folder.setId(id);
		wrap.addHeader(EtcDic.FOLDER, JsonUtil.toJson(folder));
		client.excute(wrap);
		String json = wrap.getHeaderValue(EtcDic.FOLDER);
		return JsonUtil.fromJson(json, JsonUtil.LISTFOLDER);
	}

	public List<Folder> getFolderList() throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		Folder folder = new Folder();
		wrap.addHeader(EtcDic.FOLDER, JsonUtil.toJson(folder));
		client.excute(wrap);
		String json = wrap.getHeaderValue(EtcDic.FOLDER);
		return JsonUtil.fromJson(json, JsonUtil.LISTFOLDER);
	}
}
