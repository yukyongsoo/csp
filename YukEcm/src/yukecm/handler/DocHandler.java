package yukecm.handler;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;

import yukcommon.dic.EtcDic;
import yukcommon.dic.UriDic;
import yukcommon.exception.NotSupportException;
import yukcommon.model.Content;
import yukcommon.model.Doc;
import yukcommon.model.User;
import yukcommon.model.fileitem.IFileItem;
import yukcommon.model.fileitem.StreamFileItem;
import yukcommon.net.AbsNetHandler;
import yukcommon.util.JsonUtil;
import yukcommon.util.LoggerUtil;
import yukecm.controller.DocController;
import yukecm.controller.SecureController;
import yukecm.etc.EcmUtil;

public class DocHandler extends AbsNetHandler{
	@Override
	public void handleImpl(HttpRequest request, HttpResponse response, String uri,User user) throws Exception {
		String json = request.getFirstHeader(EtcDic.DOC).getValue();
		Doc doc = JsonUtil.fromJson(json, Doc.class);
		long start = System.currentTimeMillis();
		if (uri.equals(UriDic.ADDDOC)) {
			try {
				if(!(request instanceof BasicHttpEntityEnclosingRequest))
					throw new NotSupportException("request are not Post Type");
				BasicHttpEntityEnclosingRequest req = (BasicHttpEntityEnclosingRequest) request;
				HttpEntity entity = req.getEntity();
				IFileItem item = new StreamFileItem(entity.getContent());
				String id = DocController.getInstance().addDoc(doc, item,user);
				response.addHeader(EtcDic.RETID, id);
				LoggerUtil.debugTime(getClass(), doc.getId() + " create sucess.total time is {}Ms",start);
			} catch (Exception e) {
				LoggerUtil.debugTime(getClass(), "create fail.total time is {}Ms",start);
				throw e;
			}
		}
		else if (uri.equals(UriDic.GETDOC)) {
			try {
				if(!(request instanceof BasicHttpEntityEnclosingRequest))
					throw new NotSupportException("request are not Post Type");
				Content nContent = DocController.getInstance().getDoc(doc,user);
				EcmUtil.setItem(response, nContent.getItem().getInputStream());
				LoggerUtil.debugTime(getClass(), doc.getId() + " download sucess.total time is {}Ms. but it's not contain network Time",start);
			} catch (Exception e) {
				LoggerUtil.debugTime(getClass(), doc.getId() + " download fail.total time is {}Ms. but it's not contain network Time",start);
				throw e;
			}
		}
		else if (uri.equals(UriDic.GETFOLDERDOCLIST)) {
			try {
				List<Doc> list = DocController.getInstance().getFolderDocList(doc,user);
				response.addHeader(EtcDic.DOC, JsonUtil.toJson(list));
				LoggerUtil.debugTime(getClass(),"get Foldering document sucess.total time is {}Ms. but it's not contain network Time",start);
			} catch (Exception e) {
				LoggerUtil.debugTime(getClass(),"get Foldering document fail.total time is {}Ms. but it's not contain network Time",start);
				throw e;
			}
		}
		else if (uri.equals(UriDic.DELDOC)) {
			try {
				DocController.getInstance().delDoc(doc,user);
				LoggerUtil.debugTime(getClass(), doc.getId() + " delete sucess.total time is {}Ms",start);
			} catch (Exception e) {
				LoggerUtil.debugTime(getClass(), doc.getId() + " delete fail.total time is {}Ms",start);
				throw e;
			}
		}
		else if(uri.equals(UriDic.UPDDOC)){
			if(!(request instanceof BasicHttpEntityEnclosingRequest))
				throw new NotSupportException("request are not Post Type");
			BasicHttpEntityEnclosingRequest req = (BasicHttpEntityEnclosingRequest) request;
			HttpEntity entity = req.getEntity();
			IFileItem item = new StreamFileItem(entity.getContent());
			DocController.getInstance().updDoc(doc , item,user);
		}
		else if(uri.equals(UriDic.ADDDOCTOFOLDER)) {
			DocController.getInstance().addDocToFolder(doc,user);
		}
	}

	@Override
	public User preCheck(HttpRequest request, HttpResponse response, String uri) throws Exception {
		return SecureController.getInstance().checkUser(request, response, uri);
	}
}
