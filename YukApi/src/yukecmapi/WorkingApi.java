package yukecmapi;

import java.util.List;

import yukcommon.dic.UriDic;
import yukcommon.dic.type.RuleType;
import yukcommon.model.WorkingGroup;

public class WorkingApi extends AbsApi{
	public WorkingApi(EcmConnection conn) {
		super(conn);
	}

	public List<WorkingGroup> getWorkingList() throws Exception{
		WorkingApiImpl impl = EcmApiFactory.getWoringApi(conn,UriDic.GETWORK);
		return impl.getWorkingList();
	}
	
	public String addWork(String name, boolean audit) throws Exception{
		WorkingApiImpl impl = EcmApiFactory.getWoringApi(conn,UriDic.ADDWORK);
		return impl.addWork(name, audit);
	}

	public void delWork(String id) throws Exception{
		WorkingApiImpl impl = EcmApiFactory.getWoringApi(conn,UriDic.DELWORK);
		impl.delWork(id);
	}

	public void updWork(String id, String name, boolean audit) throws Exception{
		WorkingApiImpl impl = EcmApiFactory.getWoringApi(conn,UriDic.UPDWORK);
		impl.updWork(id, name, audit);
	}
	
	public WorkingGroup getWorkRule(String workId) throws Exception{
		WorkingApiImpl impl = EcmApiFactory.getWoringApi(conn,UriDic.GETWORKRULE);
		return impl.getWorkRule(workId);
	}

	public void addWorkRule(String workid, String ruleid, RuleType type) throws Exception{
		WorkingApiImpl impl = EcmApiFactory.getWoringApi(conn,UriDic.ADDWORKRULE);
		impl.addWorkRule(workid, ruleid, type);
	}

	public void delWorkRule(String workid, String ruleid, RuleType tempType) throws Exception{
		WorkingApiImpl impl = EcmApiFactory.getWoringApi(conn,UriDic.DELWORKRULE);
		impl.delWorkRule(workid, ruleid,tempType);
	}

	
}
