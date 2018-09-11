package yukecmapi;

import java.io.InputStream;
import java.util.List;

import yukcommon.dic.UriDic;
import yukcommon.model.Doc;

public class DocApi extends AbsApi{
	public DocApi(EcmConnection conn) {
		super(conn);
	}

	public String addDoc(String workId,String name, String localFilePath,String aclId) throws Exception  {
		DocApiImpl docApi = EcmApiFactory.getDocApi(conn,UriDic.ADDDOC);
		return docApi.addDoc(workId,name, localFilePath,aclId);
	}

	public String addDoc(String workId,String name, InputStream stream, String fileName,String aclId) throws Exception{
		DocApiImpl docApi = EcmApiFactory.getDocApi(conn,UriDic.ADDDOC);
		return docApi.addDoc(workId,name, stream, fileName,aclId);
	}
	
	public void addDocToFolder(String docId, String folderId) throws Exception{
		DocApiImpl impl = EcmApiFactory.getDocApi(conn,UriDic.ADDDOCTOFOLDER);
		impl.addDocToFolder(docId, folderId);		
	}

	public void getDoc(String docId, String dirPath, String fileName) throws Exception{
		DocApiImpl docApi = EcmApiFactory.getDocApi(conn,UriDic.GETDOC);
		docApi.getDoc(docId, dirPath,fileName);
	}
	
	public InputStream getDocAsStream(String docId) throws Exception{
		DocApiImpl docApi = EcmApiFactory.getDocApi(conn,UriDic.GETDOC);
		return docApi.getDocAsStream(docId);
	}
	

	public void delDoc(String docId) throws Exception{
		DocApiImpl docApi = EcmApiFactory.getDocApi(conn,UriDic.DELDOC);
		docApi.delDoc(docId);
	}
	
	public List<Doc> getDocFolderList(String folderId) throws Exception{
		DocApiImpl docApi = EcmApiFactory.getDocApi(conn,UriDic.GETFOLDERDOCLIST);
		return docApi.getDocFolderList(folderId);
	}

	public void updDoc(String docId,String name, String localFilePath,String aclId) throws Exception{
		DocApiImpl docApi = EcmApiFactory.getDocApi(conn,UriDic.UPDDOC);
		docApi.updDoc(docId,name,localFilePath,aclId);
	}

	public void updDoc(String docId, String name, InputStream stream, String fileName,String aclId) throws Exception{
		DocApiImpl docApi = EcmApiFactory.getDocApi(conn,UriDic.UPDDOC);
		docApi.updDoc(docId,name,stream,fileName,aclId);
	}
}
