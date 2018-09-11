package yukecmapi;

import java.util.List;

import yukcommon.dic.UriDic;
import yukcommon.model.Folder;

public class FolderApi extends AbsApi{
	public FolderApi(EcmConnection conn) {
		super(conn);
	}

	public String addFolder(String folderName, String parentId,String aclId, String descr) throws Exception {
		FolderApiImpl impl = EcmApiFactory.getFolderApi(conn,UriDic.ADDFOLDER);
		return impl.addFolder(folderName, parentId,aclId, descr);
	}

	public void updFolder(String id,String newName, String parentId,String aclId) throws Exception{
		FolderApiImpl impl = EcmApiFactory.getFolderApi(conn,UriDic.UPDFOLDER);
		impl.updFolder(id,newName, parentId,aclId);
	}

	public void delFolder(String id) throws Exception{
		FolderApiImpl impl = EcmApiFactory.getFolderApi(conn,UriDic.DELFOLDER);
		impl.delFolder(id);
	}

	public List<Folder> getFolderChildList(String id) throws Exception{
		FolderApiImpl impl = EcmApiFactory.getFolderApi(conn,UriDic.GETFOLDERCHILDLIST);
		return impl.getFolderChildList(id);
	}
	
	public List<Folder> getFolderList() throws Exception{
		FolderApiImpl impl = EcmApiFactory.getFolderApi(conn,UriDic.GETFOLDERLIST);
		return impl.getFolderList();
	}
}
