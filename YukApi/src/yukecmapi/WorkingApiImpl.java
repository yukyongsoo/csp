package yukecmapi;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import yukcommon.dic.EtcDic;
import yukcommon.dic.type.RuleType;
import yukcommon.model.WorkingGroup;
import yukcommon.net.NetClient;
import yukcommon.net.NetWrapper;
import yukcommon.util.JsonUtil;

public class WorkingApiImpl extends AbsApiImpl {
	protected WorkingApiImpl(NetClient client, NetWrapper wrap) {
		super(client, wrap);
	}

	public String addWork(String name, boolean audit) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		WorkingGroup group = new WorkingGroup();
		group.setName(name);
		group.setAudit(audit);
		wrap.addHeader(EtcDic.WORKINGGROUP, JsonUtil.toJson(group));
		client.excute(wrap);
		return wrap.getHeaderValue(EtcDic.RETID);
	}

	public void delWork(String id) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		WorkingGroup group = new WorkingGroup();
		group.setId(id);
		wrap.addHeader(EtcDic.WORKINGGROUP, JsonUtil.toJson(group));
		client.excute(wrap);
	}

	public void updWork(String id, String name, boolean audit) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		WorkingGroup group = new WorkingGroup();
		group.setId(id);
		group.setName(name);
		group.setAudit(audit);
		wrap.addHeader(EtcDic.WORKINGGROUP, JsonUtil.toJson(group));
		client.excute(wrap);
	}

	public void addWorkRule(String workid, String ruleid, RuleType type) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		sameFunction(workid,ruleid,type);
	}

	public void delWorkRule(String workid, String ruleid, RuleType type) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		sameFunction(workid,ruleid,type);
	}
	
	private void sameFunction(String workid, String ruleid, RuleType type) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		WorkingGroup group = new WorkingGroup();
		group.setId(workid);
		group.setTempId(ruleid);
		group.setTempType(type);
		wrap.addHeader(EtcDic.WORKINGGROUP, JsonUtil.toJson(group));
		client.excute(wrap);
	}

	public List<WorkingGroup> getWorkingList() throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		WorkingGroup group = new WorkingGroup();
		wrap.addHeader(EtcDic.WORKINGGROUP, JsonUtil.toJson(group));
		client.excute(wrap);
		String json = wrap.getHeaderValue(EtcDic.WORKINGGROUP);
		return JsonUtil.fromJson(json, JsonUtil.LISTWORK);
	}

	public WorkingGroup getWorkRule(String workId) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		WorkingGroup group = new WorkingGroup();
		group.setId(workId);
		wrap.addHeader(EtcDic.WORKINGGROUP, JsonUtil.toJson(group));
		client.excute(wrap);
		String json = wrap.getHeaderValue(EtcDic.WORKINGGROUP);
		return JsonUtil.fromJson(json, WorkingGroup.class);
	}
}
