package yukecmapi;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.commons.io.FileUtils;

import yukcommon.dic.EtcDic;
import yukcommon.exception.WrongOperationException;
import yukcommon.exception.ZeroFileSizeException;
import yukcommon.model.Doc;
import yukcommon.model.fileitem.IFileItem;
import yukcommon.net.NetClient;
import yukcommon.net.NetWrapper;
import yukcommon.util.JsonUtil;
import yukcommon.util.NormalUtil;

public class DocApiImpl extends AbsApiImpl{

	protected DocApiImpl(NetClient client, NetWrapper wrap) {
		super(client, wrap);
	}

	public String addDoc(String workId,String name, String localFilePath, String aclId) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		File file = new File(localFilePath);
		if(!(file.exists() && file.isFile() && file.length() > 0))
			throw new WrongOperationException("file is not correct." + localFilePath);	
		Doc doc = new Doc();
		doc.setWorkId(workId);
		doc.setName(name);
		doc.getContent().setName(file.getName());
		doc.getContent().setSize(file.length());
		if (doc.getContent().getSize() < 1)
			throw new ZeroFileSizeException("File size 0 is Not Permmited.");
		doc.setAclid(aclId);
		wrap.setFile(localFilePath);
		wrap.addHeader(EtcDic.DOC, JsonUtil.toJson(doc));
		client.excute(wrap);
		return wrap.getHeaderValue(EtcDic.RETID);
	}

	public String addDoc(String workId,String name,InputStream stream, String fileName, String aclId) throws IOException, URISyntaxException, InterruptedException, ExecutionException {
		Doc doc = new Doc();
		doc.setName(name);
		doc.setWorkId(workId);
		doc.getContent().setName(fileName);
		doc.getContent().setSize(stream.available());
		if (doc.getContent().getSize() < 1)
			throw new ZeroFileSizeException("File size 0 is Not Permmited.");
		doc.setAclid(aclId);
		wrap.setFile(stream);
		wrap.addHeader(EtcDic.DOC, JsonUtil.toJson(doc));
		client.excute(wrap);
		return wrap.getHeaderValue(EtcDic.RETID);
	}
	
	public void addDocToFolder(String docId, String folderId) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		Doc doc = new Doc();
		doc.setId(docId);
		doc.setFolderId(folderId);
		wrap.addHeader(EtcDic.DOC, JsonUtil.toJson(doc));
		client.excute(wrap);
	}

	public void getDoc(String docId, String dirPath, String fileName) throws IOException, URISyntaxException, InterruptedException, ExecutionException {
		File file = new File(dirPath);
		if(!file.exists())
			FileUtils.forceMkdir(file);
		if(file.exists() && file.isFile())
			throw new WrongOperationException("dirPath already have file");
		Doc doc = new Doc();
		doc.setId(docId);
		wrap.addHeader(EtcDic.DOC, JsonUtil.toJson(doc));
		client.excuteNonClose(wrap);
		IFileItem item = wrap.getFiles();
		String dir = file.getAbsolutePath() + EtcDic.PATHSEP + fileName;
		NormalUtil.moveFile(item, dir);
		wrap.close();
	}
	
	public InputStream getDocAsStream(String docId) throws IOException, URISyntaxException, InterruptedException, ExecutionException {
		Doc doc = new Doc();
		doc.setId(docId);
		wrap.addHeader(EtcDic.DOC, JsonUtil.toJson(doc));
		client.excuteNonClose(wrap);
		IFileItem item = wrap.getFiles();
		return item.getInputStream();
	}

	public void delDoc(String docId) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		Doc doc = new Doc();
		doc.setId(docId);
		wrap.addHeader(EtcDic.DOC, JsonUtil.toJson(doc));
		client.excute(wrap);
	}

	public void updDoc(String docId,String name, String localFilePath, String aclId ) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		Doc doc = new Doc();
		doc.setName(name);
		doc.setId(docId);
		File file = new File(localFilePath);
		doc.setName(name);
		doc.getContent().setName(file.getName());
		if (doc.getContent().getSize() <= 0)
			throw new ZeroFileSizeException("File size 0 is Not Permmited.");
		doc.setAclid(aclId);
		wrap.setFile(localFilePath);
		wrap.addHeader(EtcDic.DOC, JsonUtil.toJson(doc));
		client.excute(wrap);
	}

	public void updDoc(String docId,String name, InputStream stream, String fileName, String aclId) throws URISyntaxException, IOException, InterruptedException, ExecutionException  {
		Doc doc = new Doc();
		doc.setId(docId);
		doc.setName(name);
		doc.getContent().setName(fileName);
		doc.getContent().setSize(stream.available());
		if (doc.getContent().getSize() <= 0)
			throw new ZeroFileSizeException("File size 0 is Not Permmited.");
		doc.setAclid(aclId);
		wrap.addHeader(EtcDic.DOC, JsonUtil.toJson(doc));
		wrap.setFile(stream);
		client.excute(wrap);
	}

	public List<Doc> getDocFolderList(String folderId) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		Doc doc = new Doc();
		doc.setFolderId(folderId);
		wrap.addHeader(EtcDic.DOC, JsonUtil.toJson(doc));
		client.excute(wrap);
		String json = wrap.getHeaderValue(EtcDic.DOC);
		return JsonUtil.fromJson(json, JsonUtil.LISTDOC);
	}
}
