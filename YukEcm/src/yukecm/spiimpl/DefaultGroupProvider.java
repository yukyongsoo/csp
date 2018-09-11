package yukecm.spiimpl;

import java.util.List;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import yukcommon.dic.EtcDic;
import yukcommon.dic.type.OnErrorType;
import yukcommon.model.Group;
import yukcommon.spi.GroupProvider;
import yukcommon.util.JsonUtil;
import yukecm.etc.EcmUtil;
import yukecm.injecter.group.GroupInjector;

public class DefaultGroupProvider implements GroupProvider{
	
	@Override
	public String addGroup(HttpRequest request, HttpResponse response) throws Exception {
		String json = request.getFirstHeader(EtcDic.GROUP).getValue();
		Group group = JsonUtil.fromJson(json, Group.class);
		group.setId(EcmUtil.getId());
		GroupInjector.getInstance().addGroup(group);
		return group.getId();
	}

	@Override
	public Group getGroup(HttpRequest request, HttpResponse response) throws Exception {
		String json = request.getFirstHeader(EtcDic.GROUP).getValue();
		Group group = JsonUtil.fromJson(json, Group.class);
		return GroupInjector.getInstance().getGroup(group,OnErrorType.NONE);
	}

	@Override
	public void delGroup(HttpRequest request, HttpResponse response) throws Exception {
		String json = request.getFirstHeader(EtcDic.GROUP).getValue();
		Group group = JsonUtil.fromJson(json, Group.class);
		GroupInjector.getInstance().getGroup(group,OnErrorType.NOTEXIST);
		GroupInjector.getInstance().delGroup(group);
	}

	@Override
	public void updGroup(HttpRequest request, HttpResponse response) throws Exception {
		String json = request.getFirstHeader(EtcDic.GROUP).getValue();
		Group group = JsonUtil.fromJson(json, Group.class);
		GroupInjector.getInstance().getGroup(group,OnErrorType.NOTEXIST);
		GroupInjector.getInstance().updGroup(group);
	}

	@Override
	public List<Group> getGroupList(HttpRequest request, HttpResponse response) throws Exception  {
		String json = request.getFirstHeader(EtcDic.GROUP).getValue();
		Group group = JsonUtil.fromJson(json, Group.class);
		return GroupInjector.getInstance().getGroupList(group,OnErrorType.NONE);
	}

	@Override
	public List<Group> getGroupChildList(HttpRequest request, HttpResponse response) throws Exception {
		String json = request.getFirstHeader(EtcDic.GROUP).getValue();
		Group group = JsonUtil.fromJson(json, Group.class);
		return GroupInjector.getInstance().getGroupChildList(group,OnErrorType.NONE);
	}

}
