package yukecm.lifecycle;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import yukcommon.model.lifecycle.LifeCycleInfo;
import yukcommon.model.lifecycle.LifeCycleSetting;
import yukcommon.util.LoggerUtil;
import yukecm.controller.LifeCycleController;
import yukecm.etc.EcmUtil;
import yukecm.injecter.lifecycle.LCInfoInjector;

public class LifeCycleThreadPool extends Thread{
	private ExecutorService executor;
	private volatile boolean stop = false;
	private LifeCycleSetting cycleSetting;

	public LifeCycleThreadPool(LifeCycleSetting setting) {
		setName(setting.getName() + "-Invoker");
		this.cycleSetting = setting;
	}

	public void end() {
		stop = true;
		if(executor != null)
			executor.shutdownNow();
	}
	
	public void startPool() {
		start();
	}
	
	@Override
	public void run() {
		try {
			executor = Executors.newFixedThreadPool(cycleSetting.getThread(), new ThreadFactory() {
				@Override
				public Thread newThread(Runnable r) {
					return new Thread(r, cycleSetting.getName() + "-WorkThread");
				}
			});
			sleep(2000);
			List<LifeCycleCallable> list = LifeCycleController.getInstance().getLifeCycleFileList(cycleSetting);
			while(!stop){
				if (list != null && !list.isEmpty()) {
					long start= System.currentTimeMillis();
					List<Future<Boolean>> targetResult = executor.invokeAll(list);
					list = LifeCycleController.getInstance().getLifeCycleFileList(cycleSetting);
					LifeCycleInfo updInfo = new LifeCycleInfo();
					updInfo.setId(cycleSetting.getId());
					for (Future<Boolean> future : targetResult) {
						getResult(future, updInfo);
					}
					updInfo.setEnd(EcmUtil.makeDateReadable());
					LCInfoInjector.getInstance().updCycleInfoAddData(updInfo);
					LoggerUtil.debugTime(getClass(),"lifeCycle :" + cycleSetting.getId() + " spend "  
							+ targetResult.size() + " File Time is {}Ms", start);
				}
				else{
					LifeCycleManager.getInstance().stopLifeCycle(cycleSetting.getId());
				}
			}
		}
		catch(InterruptedException ie){
			LoggerUtil.warn(getClass(), "LifeCycle Thread Has Interrupted. LifeCycle is End." +  cycleSetting.getName(), null);
			interrupt();
		}
		catch (Exception e) {
			LoggerUtil.error(getClass(), "LifeCycle Thread Has Error." + cycleSetting.getName(), e);
		}
	}
	
	private void getResult(Future<Boolean> future,LifeCycleInfo updInfo) throws InterruptedException {
		Boolean result;
		try {
			result = future.get();
			if (!result)
				updInfo.addError(1);
		} catch (ExecutionException e) {
			LoggerUtil.error(getClass(),"LifeCycling File Has Internal Error", e);
			updInfo.addError(1);
		} finally {
			updInfo.addExcuted(1);
		}
	}
}
