package yukecm.lifecycle.sch;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import yukcommon.dic.type.OnErrorType;
import yukcommon.util.LoggerUtil;
import yukecm.lifecycle.LifeCycleManager;
import yukecm.lifecycle.LifeCycleWorker;

public class EndSchJob implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			JobDataMap dataMap = context.getJobDetail().getJobDataMap();
			String lcId = dataMap.getString("LCID");
			LifeCycleWorker worker = LifeCycleManager.getInstance().getWorker(lcId,OnErrorType.NOTEXIST);
			worker.stopCycle();
			LoggerUtil.info(getClass(), lcId + " life Cycle is Stoped by scheduler", null);
		} catch (Exception e) {
			LoggerUtil.error(getClass(), "Stoping Schedule Error", e);
		}
	}
}
