package yukecm.handler;

import java.util.List;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import yukcommon.dic.EtcDic;
import yukcommon.dic.UriDic;
import yukcommon.model.User;
import yukcommon.model.WorkingGroup;
import yukcommon.net.AbsNetHandler;
import yukcommon.util.JsonUtil;
import yukecm.controller.SecureController;
import yukecm.controller.WorkingController;

public class WorkingGroupHandler extends AbsNetHandler{
	@Override
	public void handleImpl(HttpRequest request, HttpResponse response, String uri,User user) throws Exception {
		String json = request.getFirstHeader(EtcDic.WORKINGGROUP).getValue();
		WorkingGroup work = JsonUtil.fromJson(json, WorkingGroup.class);
		if (uri.equals(UriDic.ADDWORK)) {
			String id = WorkingController.getInstance().addWork(work);
			response.addHeader(EtcDic.RETID, id);
		} else if (uri.equals(UriDic.UPDWORK)) {
			WorkingController.getInstance().updWork(work);
		} else if (uri.equals(UriDic.DELWORK)) {
			WorkingController.getInstance().delWork(work);
		} else if (uri.equals(UriDic.GETWORK)) {
			List<WorkingGroup> workList = WorkingController.getInstance().getWorkList();
			response.addHeader(EtcDic.WORKINGGROUP, JsonUtil.toJson(workList));
		} else if (uri.equals(UriDic.ADDWORKRULE)) {
			WorkingController.getInstance().addWorkRule(work);
		} else if (uri.equals(UriDic.DELWORKRULE)) {
			WorkingController.getInstance().delWorkRule(work);
		} else if (uri.equals(UriDic.GETWORKRULE)) {
			WorkingGroup workRuleList = WorkingController.getInstance().getWorkRuleList(work.getId());
			response.addHeader(EtcDic.WORKINGGROUP, JsonUtil.toJson(workRuleList));
		}

	}

	@Override
	public User preCheck(HttpRequest request, HttpResponse response, String uri) throws Exception {
		User user = SecureController.getInstance().checkUser(request, response, uri);
		SecureController.getInstance().checkSystemPermission(request,response,user,uri);
		return user;
	}
}
