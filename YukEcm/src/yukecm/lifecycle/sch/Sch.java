package yukecm.lifecycle.sch;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.DirectSchedulerFactory;
import org.quartz.simpl.RAMJobStore;
import org.quartz.simpl.SimpleThreadPool;
import org.quartz.spi.JobStore;

import yukcommon.exception.NotExistException;
import yukcommon.model.lifecycle.LifeCycleSetting;

public class Sch {
	private static class LazyHolder {
	    private static final Sch sch; 
	    static {
	    	try {
				sch = new Sch();
			} catch (SchedulerException e) {
				throw new ExceptionInInitializerError(e);
			}
	    }
	}
	
	public static Sch getInstance() {
		return LazyHolder.sch;
	}
	
	Scheduler scheduler;
	private Map<String, JobPair> jobMap = new ConcurrentHashMap<String, JobPair>();
	
	private Sch() throws SchedulerException {
		SimpleThreadPool threadPool = new SimpleThreadPool(2, Thread.NORM_PRIORITY);
		JobStore jobStore = new RAMJobStore();
		threadPool.setInstanceName("LcSch");
		DirectSchedulerFactory.getInstance().createScheduler("LcSch", "LcSch", threadPool, jobStore);
		scheduler = DirectSchedulerFactory.getInstance().getScheduler("LcSch");
		scheduler.start();
	}
	
	public void addJob(LifeCycleSetting setting) throws SchedulerException {
		JobDetail job = JobBuilder.newJob(StartSchJob.class).withIdentity(setting.getName()+"_start").
				usingJobData("LCID", setting.getId()).build();
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity(setting.getName()+"_start").
				withSchedule(CronScheduleBuilder.cronSchedule(setting.getStartingCron())).build();
		scheduler.scheduleJob(job, trigger);
		
		JobDetail job2 = JobBuilder.newJob(EndSchJob.class).withIdentity(setting.getName()+"_end").
				usingJobData("LCID", setting.getId()).build();
		Trigger trigger2 = TriggerBuilder.newTrigger().withIdentity(setting.getName()+"_end").
				withSchedule(CronScheduleBuilder.cronSchedule(setting.getEndingCron())).build();
		scheduler.scheduleJob(job2, trigger2);
		
		JobPair pair = new JobPair();
		pair.start = job.getKey();
		pair.end = job2.getKey();
		
		jobMap.put(setting.getId(), pair);
	}
	public void deleteJob(LifeCycleSetting setting) throws SchedulerException {
		JobPair pair = jobMap.get(setting.getId());
		if(pair == null)
			throw new NotExistException("This LifeCycle Setting Id not Exist on Scheduler Map." + setting.getId());
		scheduler.deleteJob(pair.start);
		scheduler.deleteJob(pair.end);
		jobMap.remove(setting.getId());
	}
	
}
