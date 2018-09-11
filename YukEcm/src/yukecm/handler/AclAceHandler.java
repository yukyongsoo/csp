package yukecm.handler;

import java.util.List;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import yukcommon.dic.EtcDic;
import yukcommon.dic.UriDic;
import yukcommon.model.Ace;
import yukcommon.model.Acl;
import yukcommon.model.User;
import yukcommon.net.AbsNetHandler;
import yukcommon.util.JsonUtil;
import yukecm.controller.AceController;
import yukecm.controller.AclController;
import yukecm.controller.SecureController;

public class AclAceHandler extends AbsNetHandler{
	@Override
	public void handleImpl(HttpRequest request, HttpResponse response, String uri,User user) throws Exception {
		if (uri.equals(UriDic.ADDACL)) {
			String id = AclController.getInstance().addAcl(request,response);
			response.addHeader(EtcDic.RETID, id);
		}
		else if (uri.equals(UriDic.GETACL)) {
			List<Acl> aclList = AclController.getInstance().getAclList(request,response);
			response.addHeader(EtcDic.ACL, JsonUtil.toJson(aclList));
		}
		else if (uri.equals(UriDic.DELACL)) {
			AclController.getInstance().delAcl(request,response);
		} 
		else if (uri.equals(UriDic.UPDACL)) {
			AclController.getInstance().updAcl(request,response);
		}
		else if( uri.equals(UriDic.ADDACE)) {
			AceController.getInstance().addAce(request,response);
		}
		else if( uri.equals(UriDic.GETACE)) {
			List<Ace> list = AceController.getInstance().getAce(request,response);
			response.addHeader(EtcDic.ACE, JsonUtil.toJson(list));
		}
		else if( uri.equals(UriDic.DELACE)) {
			AceController.getInstance().delAce(request,response);
		}
		else if( uri.equals(UriDic.UPDACE)) {
			AceController.getInstance().updAce(request,response);
		}
	}

	@Override
	public User preCheck(HttpRequest request, HttpResponse response, String uri) throws Exception {
		User user = SecureController.getInstance().checkUser(request, response, uri);
		SecureController.getInstance().checkSystemPermission(request, response,user,uri);
		return user;
	}
}
