package yukecm.lifecycle.des;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.quartz.SchedulerException;

import yukcommon.dic.EtcDic;
import yukcommon.dic.type.OnErrorType;
import yukcommon.model.Rule;
import yukcommon.model.WorkingGroup;
import yukcommon.model.lifecycle.LifeCycleInfo;
import yukcommon.model.lifecycle.LifeCycleSetting;
import yukcommon.model.subrule.DesRule;
import yukecm.db.DbConnFac;
import yukecm.db.LCDbAction;
import yukecm.etc.EcmUtil;
import yukecm.injecter.lifecycle.LCInfoInjector;
import yukecm.injecter.rule.RuleInjector;
import yukecm.injecter.workgroup.WorkInjector;
import yukecm.lifecycle.LifeCycleCallable;
import yukecm.lifecycle.LifeCycleThreadPool;
import yukecm.lifecycle.LifeCycleWorker;

public class DesWorker extends LifeCycleWorker{
	LifeCycleThreadPool pool;
	DesTargetMaker maker;

	public DesWorker(LifeCycleSetting setting) throws SchedulerException {
		super(setting);
	}

	@Override
	public List<LifeCycleCallable> getTargetList() throws InterruptedException {
		return maker.getList();
	}

	@Override
	public void stopCycleImpl() throws InterruptedException {
		if(maker != null)
			maker.stopMakeTarget();
		if(pool != null)
			pool.end();
	}

	@Override
	public void startCycleImpl() throws SQLException, IOException, InterruptedException {
		LCDbAction action = null;
		try {
			action = DbConnFac.getInstance().getLCDbAction();
			LifeCycleInfo cycleInfo = LCInfoInjector.getInstance().getLifeCycleInfo(setting.getId(),OnErrorType.NOTEXIST);
			cycleInfo.setState(EtcDic.LIFECYCLERUN);
			WorkingGroup working = WorkInjector.getInstance().getWorkRuleList(setting.getWorkId(),OnErrorType.NOTEXIST);
			Rule rule = RuleInjector.getInstance().getRule(working.getDesId(),OnErrorType.NOTEXIST);
			DesRule desRule = (DesRule) rule.getSubRule();
			long t = System.currentTimeMillis() - desRule.getLimitTime();
			long target = action.getDesTargetCount(setting.getWorkId(), EcmUtil.makeDate(t));
			cycleInfo.setTotalTarget(target); 
			LCInfoInjector.getInstance().updLifeCyclenfo(cycleInfo);
			maker = new DesTargetMaker(setting);
			maker.start();
			pool = new LifeCycleThreadPool(setting);
			pool.startPool();
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	@Override
	public void shutdownImpl(boolean graceful) throws InterruptedException {
		stopCycleImpl();
	}
}
