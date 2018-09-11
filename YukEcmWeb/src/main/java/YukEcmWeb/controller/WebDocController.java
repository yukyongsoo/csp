package YukEcmWeb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import YukEcmWeb.SessionBean;
import yukcommon.dic.EtcDic;
import yukcommon.dic.UriDic;
import yukcommon.model.Doc;
import yukcommon.util.JsonUtil;
import yukecmapi.DocApi;

@RestController
public class WebDocController {
	@Autowired(required = true)
    private SessionBean session;

	@RequestMapping(UriDic.GETFOLDERDOCLIST)
	public List<Doc> getFolderDocList(@RequestHeader(EtcDic.DOC) String json) throws Exception {
		Doc doc = JsonUtil.fromJson(json, Doc.class);
		DocApi api = new DocApi(session.getConn());
		return api.getDocFolderList(doc.getId());
	}
	
	@RequestMapping(UriDic.ADDDOC)
	public String addDoc(@RequestHeader(EtcDic.DOC) String json, @RequestParam("file") MultipartFile file) throws Exception {
		Doc doc = JsonUtil.fromJson(json, Doc.class);
		DocApi api = new DocApi(session.getConn());
		String docId = api.addDoc(doc.getWorkId(), doc.getName(), 
				file.getInputStream(), file.getName(), doc.getAclid());
		api.addDocToFolder(docId, doc.getFolderId());
		return docId;
	}
	
	@RequestMapping(UriDic.ADDDOCTOFOLDER)
	public void addDocToFolder(@RequestHeader(EtcDic.DOC) String json) throws Exception {
		Doc doc = JsonUtil.fromJson(json, Doc.class);
		DocApi api = new DocApi(session.getConn());
		api.addDocToFolder(doc.getId(), doc.getFolderId());
	}
	
	@RequestMapping(UriDic.DELDOC)
	public void delDoc(@RequestHeader(EtcDic.DOC) String json) throws Exception {
		Doc doc = JsonUtil.fromJson(json, Doc.class);
		DocApi api = new DocApi(session.getConn());
		api.delDoc(doc.getId());
	}
	
	@RequestMapping(UriDic.UPDDOC)
	public void updDoc(@RequestHeader(EtcDic.DOC) String json) throws Exception {
		Doc doc = JsonUtil.fromJson(json, Doc.class);
		DocApi api = new DocApi(session.getConn());
		//TODO : make Upd api.updDoc(docId, localFilePath, aclId);
	}
}
