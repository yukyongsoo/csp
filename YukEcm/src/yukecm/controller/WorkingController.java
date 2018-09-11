
package yukecm.controller;

import java.sql.SQLException;
import java.util.List;

import yukcommon.dic.type.OnErrorType;
import yukcommon.exception.NotSupportException;
import yukcommon.model.Rule;
import yukcommon.model.WorkingGroup;
import yukecm.etc.EcmUtil;
import yukecm.injecter.rule.RuleInjector;
import yukecm.injecter.workgroup.WorkInjector;

public class WorkingController {
	private static class LazyHolder {
	    private static final WorkingController work = new WorkingController();
	}

	public static WorkingController getInstance(){
		return LazyHolder.work;
	}

	private WorkingController(){

	}

	public WorkingGroup getWorking(String id) throws SQLException, InterruptedException {
		return WorkInjector.getInstance().getWorking(id,OnErrorType.NONE);
	}

	public List<WorkingGroup> getWorkList() throws SQLException, InterruptedException  {
		return WorkInjector.getInstance().getWorkList(OnErrorType.NONE);
	}

	public String addWork(WorkingGroup work) throws SQLException, InterruptedException {
		WorkInjector.getInstance().getWorkingByName(work.getName(), OnErrorType.EXIST);
		work.setId(EcmUtil.getId()); 
		WorkInjector.getInstance().addWork(work);
		return work.getId();
	}

	public void updWork(WorkingGroup work) throws  SQLException, InterruptedException{
		WorkInjector.getInstance().getWorking(work.getId(), OnErrorType.NOTEXIST);
		WorkInjector.getInstance().updWork(work);
	}

	public void delWork(WorkingGroup work) throws SQLException, InterruptedException  {
		WorkInjector.getInstance().getWorking(work.getId(), OnErrorType.NOTEXIST);
		WorkInjector.getInstance().delWork(work);
	}

	public void addWorkRule(WorkingGroup work) throws InterruptedException, SQLException {
		WorkInjector.getInstance().getWorking(work.getId(), OnErrorType.NOTEXIST);
		Rule rule = RuleInjector.getInstance().getRule(work.getTempId(),OnErrorType.NOTEXIST);
		if(rule.getType() != work.getTempType())
			throw new NotSupportException("this rule " + rule.getName() + " not adequate Rule.");
		WorkInjector.getInstance().addWorkRule(work.getId(),work.getTempId(),work.getTempType());
	}

	public void delWorkRule(WorkingGroup work) throws InterruptedException, SQLException   {
		WorkInjector.getInstance().getWorking(work.getId(), OnErrorType.NOTEXIST);
		WorkInjector.getInstance().delWorkRule(work.getId(), work.getTempId(), work.getTempType());
	}

	public WorkingGroup getWorkRuleList(String workingId) throws SQLException, InterruptedException   {
		WorkInjector.getInstance().getWorking(workingId, OnErrorType.NOTEXIST);
		return WorkInjector.getInstance().getWorkRuleList(workingId,OnErrorType.NONE);
	}
}
