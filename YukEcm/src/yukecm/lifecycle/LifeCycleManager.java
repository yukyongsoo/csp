package yukecm.lifecycle;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.quartz.SchedulerException;

import yukcommon.dic.type.OnErrorType;
import yukcommon.dic.type.RuleType;
import yukcommon.exception.NotSupportException;
import yukcommon.model.lifecycle.LifeCycleSetting;
import yukecm.injecter.InjecterUtil;
import yukecm.lifecycle.des.DesWorker;
import yukecm.lifecycle.mig.MigWorker;
import yukecm.lifecycle.sch.Sch;

public class LifeCycleManager {
	private static LifeCycleManager manager;

	public static LifeCycleManager getInstance()  {
		if(manager == null)
			manager = new LifeCycleManager();
		return manager;
	}

	Map<String, LifeCycleWorker> managerMap = new ConcurrentHashMap<String, LifeCycleWorker>();

	private LifeCycleManager() {
		Sch.getInstance();
	}

	public void addLifeCycleSch(LifeCycleSetting setting) throws SchedulerException {
		LifeCycleWorker worker = null;
		if(RuleType.MIGRULE == setting.getType())
			 worker = new MigWorker(setting);
		else if(RuleType.DESRULE == setting.getType())
			 worker = new DesWorker(setting);
		else
			throw new NotSupportException("add lifeCycle fail.cause type is not supported.");
		managerMap.put(setting.getId(), worker);
	}

	public void delLifeCycleSch(LifeCycleSetting setting) throws SchedulerException, InterruptedException {
		LifeCycleWorker worker = managerMap.get(setting.getId());
		if(worker != null)
			worker.shutdown(false);
		managerMap.remove(setting.getId());
	}

	public LifeCycleWorker getWorker(String id,OnErrorType type){
		LifeCycleWorker lifeCycleWorker = managerMap.get(id);
		InjecterUtil.onErrorException(lifeCycleWorker, type);
		return lifeCycleWorker;
	}

	public void startLifeCycle(String id) throws SQLException, IOException, InterruptedException {
		LifeCycleWorker worker = getWorker(id,OnErrorType.NOTEXIST);
		worker.startCycle();
	}

	public void stopLifeCycle(String id) throws InterruptedException, SQLException {
		LifeCycleWorker worker = getWorker(id,OnErrorType.NOTEXIST);
		worker.stopCycle();		
	}
}
