package yukecm.lifecycle.mig;

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
import yukcommon.model.subrule.MigRule;
import yukecm.db.DbConnFac;
import yukecm.db.LCDbAction;
import yukecm.injecter.lifecycle.LCInfoInjector;
import yukecm.injecter.rule.RuleInjector;
import yukecm.injecter.workgroup.WorkInjector;
import yukecm.lifecycle.LifeCycleCallable;
import yukecm.lifecycle.LifeCycleThreadPool;
import yukecm.lifecycle.LifeCycleWorker;

public class MigWorker extends LifeCycleWorker  {
	LifeCycleThreadPool pool;
	MigTargetMaker maker;

	public MigWorker(LifeCycleSetting setting) throws SchedulerException {
		super(setting);
	}

	@Override
	public void shutdownImpl(boolean graceful) throws InterruptedException {
		stopCycleImpl();
	}

	@Override
	public void startCycleImpl() throws SQLException, IOException,InterruptedException {
		LCDbAction action = null;
		try {
			action = DbConnFac.getInstance().getLCDbAction();
			LifeCycleInfo cycleInfo = LCInfoInjector.getInstance().getLifeCycleInfo(setting.getId(),OnErrorType.NOTEXIST);
			cycleInfo.setState(EtcDic.LIFECYCLERUN);
			WorkingGroup working = WorkInjector.getInstance().getWorkRuleList(setting.getWorkId(),OnErrorType.NOTEXIST);
			Rule rule = RuleInjector.getInstance().getRule(working.getMigId(),OnErrorType.NOTEXIST);
			MigRule migRule = (MigRule) rule.getSubRule();
			long target = action.getRangeTargetCount(setting.getWorkId(), migRule.getId(), setting.getStartingRange(), setting.getEndRange());
			cycleInfo.setTotalTarget(target); 
			LCInfoInjector.getInstance().updLifeCyclenfo(cycleInfo);
			if(maker != null) {
				maker.stopMakeTarget();
			}
			maker = new MigTargetMaker(setting);
			maker.start();
			if(pool != null) {
				pool.end();
			}
			pool = new LifeCycleThreadPool(setting);
			pool.startPool();
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	@Override
	public void stopCycleImpl() throws InterruptedException {
		if(maker != null)
			maker.stopMakeTarget();
		if(pool != null)
			pool.end();
	}

	@Override
	public List<LifeCycleCallable> getTargetList() throws InterruptedException {
		return maker.getList();
	}
}
