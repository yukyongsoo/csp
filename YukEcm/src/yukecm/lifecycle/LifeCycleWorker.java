package yukecm.lifecycle;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.quartz.SchedulerException;

import yukcommon.dic.EtcDic;
import yukcommon.dic.type.OnErrorType;
import yukcommon.dic.type.RuleType;
import yukcommon.exception.NotExistException;
import yukcommon.exception.WrongOperationException;
import yukcommon.model.WorkingGroup;
import yukcommon.model.lifecycle.LifeCycleInfo;
import yukcommon.model.lifecycle.LifeCycleSetting;
import yukcommon.util.LoggerUtil;
import yukecm.etc.EcmUtil;
import yukecm.injecter.lifecycle.LCInfoInjector;
import yukecm.injecter.rule.RuleInjector;
import yukecm.injecter.workgroup.WorkInjector;
import yukecm.lifecycle.sch.Sch;

public abstract class LifeCycleWorker {
	protected LifeCycleSetting setting;

	public LifeCycleWorker(final LifeCycleSetting setting) throws SchedulerException {
		this.setting = setting;
		Sch.getInstance().addJob(setting);
	}

	public abstract List<LifeCycleCallable> getTargetList() throws InterruptedException;
	public abstract void stopCycleImpl() throws InterruptedException;
	public abstract void startCycleImpl() throws SQLException, IOException, InterruptedException;
	public abstract void shutdownImpl(boolean graceful) throws InterruptedException;

	public void startCycle() throws SQLException, IOException, InterruptedException{
		LifeCycleInfo info = LCInfoInjector.getInstance().getLifeCycleInfo(setting.getId(),OnErrorType.NOTEXIST);
		if(info.getState().equals(EtcDic.LCSTOPING))
			throw new WrongOperationException("This Cycle Not Ready State. Please Stop Cycle and Retry");
		if(info.getState().equals(EtcDic.LIFECYCLERUN))
			throw new WrongOperationException("This Cycle Already Running State.");
		WorkingGroup working = WorkInjector.getInstance().getWorkRuleList(setting.getWorkId(),OnErrorType.NOTEXIST);
		if(setting.getType().equals(RuleType.MIGRULE)) {
			RuleInjector.getInstance().getRule(working.getMigId(),OnErrorType.NOTEXIST);
		}
		else if(setting.getType().equals(RuleType.DESRULE)) {
			RuleInjector.getInstance().getRule(working.getDesId(),OnErrorType.NOTEXIST);
		}
		else
			throw new NotExistException("This WorkGroup has not current Rule." + setting.getWorkId() 
										+ ".Rule Type is" + setting.getType().toString());
		startCycleImpl();
		LoggerUtil.info(getClass(), setting.getId() + " life Cycle is Started", null);
	}

	public void stopCycle() throws InterruptedException, SQLException{
		LifeCycleInfo info = LCInfoInjector.getInstance().getLifeCycleInfo(setting.getId(),OnErrorType.NOTEXIST);
		info.setState(EtcDic.LCSTOPING);
		info.setNextRowNum(0);
		LCInfoInjector.getInstance().updLifeCyclenfo(info);
		stopCycleImpl();
		info.setState(EtcDic.NOTSTARTED);
		info.setEnd(EcmUtil.makeDateReadable()); 
		LCInfoInjector.getInstance().updLifeCyclenfo(info);
		LoggerUtil.info(getClass(), setting.getId() + " life Cycle is ended", null);
	}

	public void shutdown(boolean graceful) throws InterruptedException, SchedulerException{
		Sch.getInstance().deleteJob(setting);
		shutdownImpl(graceful);
	}

}
